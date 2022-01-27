/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction.zookeeper;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.avenger.actor.distributed.transaction.DistributedTransactionEvent;
import com.avenger.actor.distributed.transaction.DistributedTransactionManagerService;
import com.avenger.actor.distributed.transaction.constant.DistributedTransactionTypeEnum;
import com.avenger.actor.distributed.transaction.constant.ZookeeperTransactionPathEnum;
import com.avenger.actor.kit.FastThreadLocalKit;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/25
 */
@Slf4j
public class ZookeeperManagerServiceImpl implements DistributedTransactionManagerService {


    private final CuratorFramework curatorFramework;


    public ZookeeperManagerServiceImpl(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    /**
     * 是否执行
     *
     * @return 结果
     */
    @Override
    public boolean turnToMe() throws InterruptedException {
        DistributedTransactionEvent transactionEvent = (DistributedTransactionEvent) FastThreadLocalKit.get("event");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Long timeout = transactionEvent.getCurrentTransactionTimeOut();
        Integer index = transactionEvent.getCurrentTransactionIndex();
        if (DistributedTransactionTypeEnum.PARALLEL.equals(transactionEvent.getTransactionTypeEnum())) {
            return Boolean.TRUE;
        }

        if (index == 0) {
            return Boolean.TRUE;
        }

        //监听上一个节点的信息，避免羊群效应
        int previous = index - 1;
        String serialNumber = StrUtil.fillBefore(Integer.toString(previous), '0', 10);
        String path =
            ZookeeperTransactionPathEnum.FIXED_PATH.getValue() + StrPool.SLASH
                + transactionEvent.getTransactionId() + StrPool.SLASH
                + ZookeeperTransactionPathEnum.SERIAL_PATH_PREFIX.getValue() + serialNumber;
        new ZookeeperPreviousTransactionHook(curatorFramework).watchThenCheck(path, aBoolean -> {
            if (Boolean.TRUE.equals(aBoolean)) {
                countDownLatch.countDown();
            }
        });

        return countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
    }

    /**
     * 创建自己的节点
     */
    @Override
    public Object execute(ProceedingJoinPoint pjp) throws Exception {
        DistributedTransactionEvent transactionEvent = (DistributedTransactionEvent) FastThreadLocalKit.get("event");
        String path =
            ZookeeperTransactionPathEnum.FIXED_PATH.getValue() + StrPool.SLASH
                + transactionEvent.getTransactionId() + StrPool.SLASH
                + ZookeeperTransactionPathEnum.SERIAL_PATH_PREFIX.getValue();

        return new ZookeeperCurrentTransactionHook(curatorFramework, path).execute(pjp::proceed).success().fail()
            .getObject();
    }

}
