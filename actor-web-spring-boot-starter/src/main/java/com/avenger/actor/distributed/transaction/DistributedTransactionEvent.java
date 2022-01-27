/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction;

import com.avenger.actor.distributed.transaction.constant.DistributedTransactionTypeEnum;
import lombok.Data;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/25
 */
@Data
public class DistributedTransactionEvent {

    /**
     * 分布式事务主键
     */
    private String transactionId;

    /**
     * 事务类型
     */
    private DistributedTransactionTypeEnum transactionTypeEnum;

    /**
     * 事务超时时间
     */
    private Long transactionTimeOut;

    /**
     * 事务总数
     */
    private Integer transactionCount;

    /**
     * 当前事务下标
     */
    private Integer currentTransactionIndex;

    /**
     * 当前事务超时时间
     */
    private Long currentTransactionTimeOut;

}
