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
 * @since 2022/1/25
 */
@AllArgsConstructor
@Getter
public enum DistributedTransactionEnum {

    /**
     * 开启事务
     */
    BEGIN("0"),

    /**
     * 回滚事务
     */
    ROLLBACK("-1"),

    /**
     * 提交事务
     */
    COMMIT("1");


    private final String value;

}
