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
 * 本地事务执行结果
 */
public interface CurrentTransactionHook {


    <T> CurrentTransactionHook execute(CatchThrowable<T> t);

    /**
     * 执行成功
     */
    CurrentTransactionHook success() throws Exception;

    /**
     * 执行失败
     */
    CurrentTransactionHook fail() throws Exception;


    Object getObject();

    @FunctionalInterface
    interface CatchThrowable<T> {

        T run() throws Throwable;
    }
}
