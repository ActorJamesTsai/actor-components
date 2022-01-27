/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.distributed;

import com.alibaba.fastjson.JSON;
import com.avenger.actor.distributed.annotation.DistributedTransaction;
import com.avenger.actor.distributed.transaction.DistributedTransactionCoordinatorService;
import com.avenger.actor.distributed.transaction.DistributedTransactionEvent;
import com.avenger.actor.distributed.transaction.DistributedTransactionManagerService;
import com.avenger.actor.distributed.transaction.constant.DistributedTransactionExceptionEnum;
import com.avenger.actor.exception.DistributedTransactionException;
import com.avenger.actor.kit.FastThreadLocalKit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/26
 */
@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
public class DistributedTransactionAop {

    @Resource
    private DistributedTransactionCoordinatorService transactionCoordinatorService;
    @Resource
    private DistributedTransactionManagerService transactionManagerService;


    @Around("@annotation(transaction) && args(message)")
    public Object around(ProceedingJoinPoint pjp, DistributedTransaction transaction, String message) throws Exception {
        DistributedTransactionEvent distributedTransactionEvent = JSON.parseObject(message,
            DistributedTransactionEvent.class);
        distributedTransactionEvent.setCurrentTransactionTimeOut(transaction.timeoutMills());
        FastThreadLocalKit.put("event", distributedTransactionEvent);

        Object o;
        transactionCoordinatorService.begin();
        if (transactionManagerService.turnToMe()) {
            o = transactionManagerService.execute(pjp);
        } else {
            throw new DistributedTransactionException(DistributedTransactionExceptionEnum.LOCAL_TIMEOUT);
        }
        transactionCoordinatorService.end();
        return o;
    }


}
