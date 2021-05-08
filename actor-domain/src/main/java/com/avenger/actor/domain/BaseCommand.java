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

    private static final long serialVersionUID = 6896482516487223906L;

    private final String createDate;

    private final Class<?> clazz;

    private final Boolean aSync;

    public BaseCommand() {
        this.createDate = new Date().toString();
        this.clazz = this.getClass();
        this.aSync = Boolean.FALSE;
    }

    @Override
    public String getCreateDate() {
        return createDate;
    }

    @Override
    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public Boolean getASync() {
        return aSync;
    }
}
