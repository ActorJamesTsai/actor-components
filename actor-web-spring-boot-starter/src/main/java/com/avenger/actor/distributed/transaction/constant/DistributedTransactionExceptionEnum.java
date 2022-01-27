/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/26
 */
@AllArgsConstructor
@Getter
public enum DistributedTransactionExceptionEnum {

    /**
     * 本地事务超时
     */
    LOCAL_TIMEOUT(33, "本地事务超时"),
    /**
     * 本地事务异常
     */
    LOCAL_EXCEPTION(44, "本地事务异常"),

    PREVIOUS_FAIL(55, "上一个事务失败"),

    GLOBAL_ROLLBACK(66, "全局事务回滚"),

    ZK_EXCEPTION(66, "zookeeper异常"),
    ;


    private final int code;

    private final String msg;
}
