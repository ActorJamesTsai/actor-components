package com.avenger.actor.domain.diff;

/**
 * Description:
 *
 * Date: 2021/12/13
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class Diff {

    private DiffType type;
    private Object oldValue;
    private Object newValue;


    public DiffType getType() {
        return type;
    }

    public void setType(DiffType type) {
        this.type = type;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }
}