/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction.zookeeper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import com.avenger.actor.distributed.transaction.DistributedTransactionCoordinatorService;
import com.avenger.actor.distributed.transaction.DistributedTransactionEvent;
import com.avenger.actor.distributed.transaction.WholeTransactionHook;
import com.avenger.actor.distributed.transaction.constant.DistributedTransactionEnum;
import com.avenger.actor.distributed.transaction.constant.DistributedTransactionExceptionEnum;
import com.avenger.actor.distributed.transaction.constant.LocalTransactionCallbackEnum;
import com.avenger.actor.distributed.transaction.constant.ZookeeperTransactionPathEnum;
import com.avenger.actor.exception.DistributedTransactionException;
import com.avenger.actor.kit.FastThreadLocalKit;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * @author JiaDu
 * @version 1.0.0
 * @see ZookeeperTransactionPathEnum
 * @see com.avenger.actor.distributed.transaction.DistributedTransactionEvent
 * @since 2022/1/25
 */
@Slf4j
public class ZookeeperCoordinatorServiceImpl implements DistributedTransactionCoordinatorService {


    private final CuratorFramework curatorFramework;
    private WholeTransactionHook wholeTransactionHook;

    public ZookeeperCoordinatorServiceImpl(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
        this.wholeTransactionHook = new ZookeeperWholeTransactionHook(curatorFramework, new CountDownLatch(1));
    }

    /**
     * 开启事务
     */
    @Override
    public DistributedTransactionCoordinatorService begin() throws Exception {
        DistributedTransactionEvent transactionEvent = (DistributedTransactionEvent) FastThreadLocalKit.get("event");
        String transactionId = transactionEvent.getTransactionId();
        String path = ZookeeperTransactionPathEnum.FIXED_PATH.getValue() + StrPool.SLASH + transactionId;
        if (transactionEvent.getCurrentTransactionIndex() == 0) {
            initParentPath(path);
        }
        this.watchParentPath(path, transactionEvent.getTransactionCount());
        this.checkParentPath(path);
        return this;
    }

    /**
     * 开启事务
     */
    @Override
    public DistributedTransactionCoordinatorService end() throws Exception {
        DistributedTransactionEvent transactionEvent = (DistributedTransactionEvent) FastThreadLocalKit.get("event");
        String transactionId = transactionEvent.getTransactionId();
        String path = ZookeeperTransactionPathEnum.FIXED_PATH.getValue() + StrPool.SLASH + transactionId;
        this.checkParentPath(path);
        wholeTransactionHook.await();
        return this;
    }

    private void initParentPath(String path) throws Exception {
        curatorFramework.create()
            //.withTtl(transactionEvent.getTransactionTimeOut())
            .creatingParentsIfNeeded()
            .withMode(CreateMode.PERSISTENT)
            .forPath(path, DistributedTransactionEnum.BEGIN.getValue().getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 获取全部事务的执行情况
     */
    @Override
    public void checkParentPath(String path) throws Exception {
        Stat stat = curatorFramework.checkExists().forPath(path);
        if (null == stat) {
            return;
        }
        byte[] byteArr = curatorFramework.getData().forPath(path);
        String semaphore = new String(byteArr, StandardCharsets.UTF_8);
        if (LocalTransactionCallbackEnum.fail.getValue().equals(semaphore)) {
            throw new DistributedTransactionException(DistributedTransactionExceptionEnum.GLOBAL_ROLLBACK);
        }
        List<String> childrenPath = curatorFramework.getChildren().forPath(path);
        if (CollUtil.isEmpty(childrenPath)) {
            return;
        }

        boolean anyFail = childrenPath.stream()
            .map(s -> {
                try {
                    return curatorFramework.getData().forPath(path + StrPool.SLASH + s);
                } catch (Exception e) {
                    log.error("checkParentPath:{}", e);
                    throw new DistributedTransactionException(e.getMessage(),
                        DistributedTransactionExceptionEnum.ZK_EXCEPTION.getCode());
                }
            })
            .map(bytes -> new String(bytes, StandardCharsets.UTF_8))
            .anyMatch(s -> LocalTransactionCallbackEnum.fail.getValue().equals(s));
        if (anyFail) {
            wholeTransactionHook.rollback(path);
        }
    }

    /**
     * 监听全部事务
     */
    @Override
    public void watchParentPath(String path, Integer count) {
        CuratorCache curatorCache = CuratorCache.builder(curatorFramework, path).build();
        CuratorCacheListener listener = CuratorCacheListener
            .builder()
            .forPathChildrenCache(path, curatorFramework, (client, event) -> {
                switch (event.getType()) {
                    case CHILD_REMOVED:
                    case CONNECTION_LOST:
                        wholeTransactionHook.rollback(path);
                        break;
                    case CHILD_UPDATED:
                        String semaphore = new String(event.getData().getData(), StandardCharsets.UTF_8);
                        if (LocalTransactionCallbackEnum.success.getValue().equals(semaphore)) {
                            wholeTransactionHook.commit(path, count);
                        }
                        if (LocalTransactionCallbackEnum.fail.getValue().equals(semaphore)) {
                            wholeTransactionHook.rollback(path);
                        }
                        break;
                    default:
                        break;
                }
            }).build();
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
    }

}
