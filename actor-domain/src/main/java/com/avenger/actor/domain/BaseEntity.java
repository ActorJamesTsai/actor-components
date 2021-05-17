package com.avenger.actor.domain;

import java.util.Date;

/**
 * Description:
 *
 * Date: 2021/5/16
 *
 * @author JiaDu
 * @version 1.0.0
 */
public abstract class BaseEntity implements Entity {

    private static final long serialVersionUID = -3047188624480603119L;

    private Object createdBy;
    private Date creationDate;
    private Object lastUpdatedBy;
    private Date lastUpdateDate;

    @Override
    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public Object getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Object lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

}
