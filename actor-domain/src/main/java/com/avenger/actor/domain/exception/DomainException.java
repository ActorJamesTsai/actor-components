/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.domain.exception;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/8/11
 */
public class DomainException extends RuntimeException {

    private static final long serialVersionUID = -2469030172853468994L;

    private final Integer code;

    public DomainException(String message) {
        super(message);
        this.code = 666;
    }

    public DomainException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
