package com.avenger.actor.domain.diff;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * Date: 2021/12/13
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class EntityDiff {

    public static final EntityDiff EMPTY = new EntityDiff(false, true);
    private final Map<String, Diff> diffs = new ConcurrentHashMap<>();

    private boolean isSelfModified;
    private boolean isEmpty;

    public Diff getDiff(String key) {
        return this.diffs.get(key);
    }

    public void setDiff(String key, Diff diff) {
        this.diffs.put(key, diff);
    }

    public EntityDiff(boolean isSelfModified, boolean isEmpty) {
        this.isSelfModified = isSelfModified;
        this.isEmpty = isEmpty;
    }

    public EntityDiff() {
    }

    public Map<String, Diff> getDiffs() {
        return diffs;
    }

    public boolean isSelfModified() {
        return isSelfModified;
    }

    public void setSelfModified(boolean selfModified) {
        this.isSelfModified = selfModified;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        this.isEmpty = empty;
    }
}