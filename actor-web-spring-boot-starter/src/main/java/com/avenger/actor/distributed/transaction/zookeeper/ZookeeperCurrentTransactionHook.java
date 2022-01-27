/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction.zookeeper;

import com.avenger.actor.distributed.transaction.CurrentTransactionHook;
import com.avenger.actor.distributed.transaction.constant.LocalTransactionCallbackEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/25
 */
@Slf4j
public class ZookeeperCurrentTransactionHook implements CurrentTransactionHook {

    private final ZookeeperNode node;

    private Throwable throwable;

    private Object object;


    public ZookeeperCurrentTransactionHook(CuratorFramework curatorFramework, String path) throws Exception {
        this.node = new ZookeeperNode(curatorFramework, path);
    }

    @Override
    public <T> CurrentTransactionHook execute(CatchThrowable<T> t) {
        try {
            object = t.run();
        } catch (Throwable e) {
            throwable = e;
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 执行成功
     */
    @Override
    public CurrentTransactionHook success() throws Exception {
        if (null == throwable) {
            node.updateNode(LocalTransactionCallbackEnum.success);
        }
        return this;
    }

    /**
     * 执行失败
     */
    @Override
    public CurrentTransactionHook fail() throws Exception {
        if (null != throwable) {
            node.updateNode(LocalTransactionCallbackEnum.fail);
        }
        return this;
    }

    @Override
    public Object getObject() {
        return object;
    }
}
