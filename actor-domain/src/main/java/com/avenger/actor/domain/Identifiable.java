package com.avenger.actor.domain;

import java.io.Serializable;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/12/13
 */
public interface Identifiable<ID extends Identifier> extends Serializable {


    /**
     * 获取业务主键
     *
     * @return 业务主键
     */
    ID getUniqueId();
}