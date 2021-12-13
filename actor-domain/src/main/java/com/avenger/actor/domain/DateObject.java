/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.domain;

import java.util.Date;

/**
 * like BaseDO
 *
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/12/13
 */
public interface DateObject<ID extends Identifier> extends Identifiable<ID> {

    /**
     * 创建人
     *
     * @return 创建人
     */
    Object getCreatedBy();

    /**
     * 创建日期
     *
     * @return 字符串格式
     */
    Date getCreationDate();

    /**
     * 更新人
     *
     * @return 更新人
     */
    Object getLastUpdatedBy();

    /**
     * 更新时间
     *
     * @return 更新时间
     */
    Date getLastUpdateDate();
}
