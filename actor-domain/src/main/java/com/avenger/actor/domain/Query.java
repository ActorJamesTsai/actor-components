package com.avenger.actor.domain;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;

/**
 * Description:
 *
 * Date: 2020/2/23
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface Query extends Serializable {

    /**
     * 创建日期
     *
     * @return 字符串格式
     */
    String getCreateDate();

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
