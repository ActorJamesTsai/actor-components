/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction.zookeeper;

import com.avenger.actor.distributed.transaction.WholeTransactionHook;
import com.avenger.actor.distributed.transaction.constant.DistributedTransactionEnum;
import com.avenger.actor.distributed.transaction.constant.DistributedTransactionExceptionEnum;
import com.avenger.actor.exception.DistributedTransactionException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/26
 */
@Slf4j
public class ZookeeperWholeTransactionHook implements WholeTransactionHook {

    private final CuratorFramework curatorFramework;
    private final CountDownLatch countDownLatch;

    public ZookeeperWholeTransactionHook(CuratorFramework curatorFramework, CountDownLatch countDownLatch) {
        this.curatorFramework = curatorFramework;
        this.countDownLatch = countDownLatch;
    }


    /**
     * 回滚
     */
    @Override
    public void rollback(String path) throws Exception {
        curatorFramework.setData()
            .forPath(path, DistributedTransactionEnum.ROLLBACK.getValue().getBytes(StandardCharsets.UTF_8));
        throw new DistributedTransactionException(DistributedTransactionExceptionEnum.GLOBAL_ROLLBACK);
    }

    /**
     * 提交
     */
    @Override
    public void commit(String path, Integer count) throws Exception {
        List<String> childPathList = curatorFramework.getChildren().forPath(path);
        if (childPathList.size() > count) {
            this.rollback(path);
        }

        if (childPathList.size() == count) {
            curatorFramework.setData()
                .forPath(path, DistributedTransactionEnum.COMMIT.getValue().getBytes(StandardCharsets.UTF_8));
            countDownLatch.countDown();

        }
    }

    @Override
    public void await() throws Exception {
        countDownLatch.await();
    }
}
