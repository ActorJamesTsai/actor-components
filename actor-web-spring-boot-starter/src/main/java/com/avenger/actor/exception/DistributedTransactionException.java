package com.avenger.actor.exception;

import com.avenger.actor.distributed.transaction.constant.DistributedTransactionExceptionEnum;

/**
 * Description:
 *
 * Date: 2021/5/1
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class DistributedTransactionException extends RuntimeException {

    private static final long serialVersionUID = 1567402028369219391L;

    private final int status;

    public DistributedTransactionException(String message, int status) {
        super(message);
        this.status = status;
    }

    public DistributedTransactionException(DistributedTransactionExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.status = exceptionEnum.getCode();
    }

    public int getStatus() {
        return status;
    }
}
