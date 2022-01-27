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
public enum LocalTransactionCallbackEnum {

    /**
     * 本地事务执行成功
     */
    success("1"),


    executing("0"),

    /**
     * 本地事务执行失败
     */
    fail("-1");

    private final String value;
}
