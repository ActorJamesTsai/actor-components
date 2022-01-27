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
public enum ZookeeperTransactionPathEnum {

    /**
     * 固定目录
     */
    FIXED_PATH("/distributedTransaction"),
    /**
     * 顺序目录前缀
     */
    SERIAL_PATH_PREFIX("seq");

    private final String value;

}
