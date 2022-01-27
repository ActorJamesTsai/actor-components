/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction.zookeeper;

import cn.hutool.core.util.ObjectUtil;
import com.avenger.actor.distributed.transaction.PreviousTransactionHook;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/26
 */
@Slf4j
public class ZookeeperPreviousTransactionHook implements PreviousTransactionHook {

    private final CuratorFramework curatorFramework;

    public ZookeeperPreviousTransactionHook(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }


    /**
     * 获取上一个事务的执行情况
     *
     * @param path 路径
     */
    @Override
    public PreviousTransactionHook check(String path, Consumer<Boolean> consumer) {
        try {
            consumer.accept(checkSemaphore(curatorFramework.getData().forPath(path)));
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return this;
    }

    /**
     * 监听上一个事务的执行情况
     *
     * @param path 路径
     */
    @Override
    public PreviousTransactionHook watch(String path, Consumer<Boolean> consumer) {
        CuratorCache curatorCache = CuratorCache.builder(curatorFramework, path).build();
        CuratorCacheListener listener = CuratorCacheListener
            .builder()
            .forNodeCache(() ->
                curatorCache.get(path).filter(ObjectUtil::isNotNull)
                    .ifPresent(childData -> consumer.accept(checkSemaphore(childData.getData())))
            ).build();
        curatorCache.listenable().addListener(listener);
        curatorCache.start();
        return this;
    }
}
