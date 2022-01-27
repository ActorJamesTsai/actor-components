/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed.transaction;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/26
 */
public interface WholeTransactionHook {

    /**
     * 回滚
     */
    void rollback(String path) throws Exception;

    /**
     * 提交
     */
    void commit(String path, Integer count) throws Exception;


    void await() throws Exception;
}
