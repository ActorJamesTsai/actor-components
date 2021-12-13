/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.domain;

import cn.hutool.extra.validation.BeanValidationResult;
import cn.hutool.extra.validation.ValidationUtil;
import com.avenger.actor.domain.exception.DomainException;
import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/8/11
 */
public interface ValueObject extends Serializable {


    /**
     * 当前值对象的校验
     *
     * @param groups 组别
     */
    default void doValidation(Class<?>... groups) {
        BeanValidationResult validationResult = ValidationUtil.warpValidate(this, groups);
        if (validationResult.isSuccess()) {
            return;
        }
        throw new DomainException(validationResult.getErrorMessages().stream()
            .map(errorMessage -> errorMessage.getPropertyName() + errorMessage.getMessage())
            .collect(Collectors.joining(";")));
    }

}
