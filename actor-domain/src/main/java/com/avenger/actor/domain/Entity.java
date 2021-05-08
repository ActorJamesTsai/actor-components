package com.avenger.actor.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface Entity extends Serializable {

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
