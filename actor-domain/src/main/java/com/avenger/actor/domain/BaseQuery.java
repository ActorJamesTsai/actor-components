package com.avenger.actor.domain;

import java.util.Date;
import javax.management.Query;

/**
 * Description:
 *
 * Date: #{DATE}
 *
 * @author JiaDu
 * @version 1.0.0
 */
public abstract class BaseQuery implements Query {

    private final String createDate;

    private final Class<?> clazz;

    public BaseQuery(String createDate, Class<?> clazz) {
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
