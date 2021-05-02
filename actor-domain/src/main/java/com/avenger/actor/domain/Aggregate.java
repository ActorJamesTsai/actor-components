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
public interface Aggregate extends Serializable {

    /**
     * 获取业务主键
     *
     * @return 业务主键
     */
    Object getUniqueId();


    default String toJsonString() {
        return JSON.toJSONString(this);
    }
}
