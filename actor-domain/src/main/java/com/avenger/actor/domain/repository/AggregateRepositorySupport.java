package com.avenger.actor.domain.repository;

import com.avenger.actor.domain.Aggregate;
import com.avenger.actor.domain.Identifier;
import com.avenger.actor.domain.diff.EntityDiff;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/12/13
 */
public abstract class AggregateRepositorySupport<T extends Aggregate<ID>, ID extends Identifier> implements
    AggregateRepository<T, ID> {

    private final Class<T> targetClass;

    /**
     * 让AggregateManager去维护Snapshot
     */
    private final AggregateManager<T, ID> aggregateManager;

    protected AggregateRepositorySupport(Class<T> targetClass, AggregateManager<T, ID> aggregateManager) {
        this.targetClass = targetClass;
        this.aggregateManager = aggregateManager;
    }

    public Class<T> getTargetClass() {
        return targetClass;
    }

    protected AggregateManager<T, ID> getAggregateManager() {
        return aggregateManager;
    }

    /**
     * Attach的操作就是让Aggregate可以被追踪
     */
    public void attach(T aggregate) {
        this.aggregateManager.attach(aggregate);
    }

    /**
     * Detach的操作就是让Aggregate停止追踪
     */
    public void detach(T aggregate) {
        this.aggregateManager.detach(aggregate);
    }


    /**
     * merge带来的变化更新回AggregateManager
     */
    public void merge(T aggregate) {
        this.aggregateManager.merge(aggregate);
    }


    /**
     * merge带来的变化更新回AggregateManager
     */
    public EntityDiff detectChanges(T aggregate) {
        return this.aggregateManager.detectChanges(aggregate);
    }

}
