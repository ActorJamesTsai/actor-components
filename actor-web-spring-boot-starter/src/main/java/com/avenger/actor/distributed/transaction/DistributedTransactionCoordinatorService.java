/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/25
 *
 * 管理整个分布式事务
 */
public interface DistributedTransactionCoordinatorService {

    /**
     * 开启事务
     */
    DistributedTransactionCoordinatorService begin() throws Exception;

    /**
     * 开启事务
     */
    DistributedTransactionCoordinatorService end() throws Exception;


    /**
     * 获取全部事务的执行情况
     */
    void checkParentPath(String path) throws Exception;

    /**
     * 监听全部事务
     */
    void watchParentPath(String path, Integer count);


}
