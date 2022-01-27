/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction.zookeeper;

import com.avenger.actor.distributed.transaction.constant.LocalTransactionCallbackEnum;
import java.nio.charset.StandardCharsets;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/25
 */
public class ZookeeperNode {

    private final String localTransactionId;

    private final CuratorFramework curatorFramework;

    public ZookeeperNode(CuratorFramework curatorFramework, String path)
        throws Exception {
        this.curatorFramework = curatorFramework;
        this.localTransactionId = curatorFramework.create()
            .creatingParentsIfNeeded()
            .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
            .forPath(path, LocalTransactionCallbackEnum.executing.getValue().getBytes(StandardCharsets.UTF_8));
    }

    public void updateNode(LocalTransactionCallbackEnum callbackEnum) throws Exception {
        curatorFramework.setData()
            .forPath(this.localTransactionId, callbackEnum.getValue().getBytes(StandardCharsets.UTF_8));
    }
}
