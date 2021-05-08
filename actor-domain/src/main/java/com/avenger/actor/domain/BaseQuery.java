package com.avenger.actor.domain;

import java.util.Date;

/**
 * Description:
 *
 * Date: 2020/2/23
 *
 * @author JiaDu
 * @version 1.0.0
 */
public abstract class BaseQuery implements Query {

    private static final long serialVersionUID = -1415700355189814249L;

    private final String createDate;

    private final Class<?> clazz;

    public BaseQuery() {
        this.createDate = new Date().toString();
        this.clazz = this.getClass();
    }

    @Override
    public String getCreateDate() {
        return createDate;
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }
}
