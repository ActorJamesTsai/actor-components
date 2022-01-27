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
public enum DistributedTransactionTypeEnum {

    /**
     * 串行事务
     */
    SERIAL(1),
    /**
     * 并行事务
     */
    PARALLEL(2);

    private final Integer value;

}
