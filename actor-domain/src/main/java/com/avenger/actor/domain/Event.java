package com.avenger.actor.domain;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface Event extends Serializable {

    /**
     * 事件主键
     *
     * @return 事件主键
     */
    String getId();

    /**
     * 队列名称
     *
     * @return 队列名称
     */
    String getQueue();

    /**
     * 创建日期
     *
     * @return 字符串格式
     */
    String getCreateDate();

    /**
     * 描述
     *
     * @return 描述
     */
    String getDescription();

    /**
     * 类名称
     *
     * @return 类
     */
    Class<?> getClazz();


    default String toJsonString() {
        return JSON.toJSONString(this);
    }
}
