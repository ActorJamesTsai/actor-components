/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/25
 *
 * 管理本地事务
 */
public interface DistributedTransactionManagerService {

    /**
     * 是否执行
     *
     * @return 结果
     */
    boolean turnToMe() throws InterruptedException;

    /**
     * 创建自己的节点
     */
    Object execute(ProceedingJoinPoint pjp) throws Exception;
}
