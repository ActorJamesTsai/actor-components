/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction;

import com.avenger.actor.distributed.transaction.constant.DistributedTransactionExceptionEnum;
import com.avenger.actor.distributed.transaction.constant.LocalTransactionCallbackEnum;
import com.avenger.actor.exception.DistributedTransactionException;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/26
 */
public interface PreviousTransactionHook {


    /**
     * 获取上一个事务的执行情况
     *
     * @param path 路径
     */
    PreviousTransactionHook check(String path, Consumer<Boolean> consumer);

    /**
     * 监听上一个事务的执行情况
     *
     * @param path 路径
     */
    PreviousTransactionHook watch(String path, Consumer<Boolean> consumer);


    default void watchThenCheck(String path, Consumer<Boolean> consumer) {
        watch(path, consumer).check(path, consumer);
    }

    /**
     * 信号量对比
     */
    default boolean checkSemaphore(byte[] bytes) {
        String semaphore = new String(bytes, StandardCharsets.UTF_8);
        if (LocalTransactionCallbackEnum.fail.getValue().equals(semaphore)) {
            throw new DistributedTransactionException(
                DistributedTransactionExceptionEnum.PREVIOUS_FAIL);
        }
        return LocalTransactionCallbackEnum.success.getValue().equals(semaphore);
    }
}
