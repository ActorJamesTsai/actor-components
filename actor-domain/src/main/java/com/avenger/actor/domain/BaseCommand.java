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
public abstract class BaseCommand implements Command {

    private final String createDate;

    private final Class<?> clazz;

    public BaseCommand(String createDate, Class<?> clazz) {
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
