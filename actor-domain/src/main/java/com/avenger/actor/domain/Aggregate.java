package com.avenger.actor.domain;

import com.alibaba.fastjson.JSON;

/**
 * Description:
 *
 * Date: 2020/2/23
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface Aggregate<ID extends Identifier> extends Entity<ID> {

    /**
     * 版本
     *
     * @return 版本号
     */
    int getVersion();

    default String toJsonString() {
        return JSON.toJSONString(this);
    }
}
