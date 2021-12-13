package com.avenger.actor.domain.repository;

import com.avenger.actor.domain.Aggregate;
import com.avenger.actor.domain.Identifier;
import com.avenger.actor.domain.diff.EntityDiff;

/**
 * for example
 *
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/12/13
 */
public class ThreadLocalAggregateManager<T extends Aggregate<ID>, ID extends Identifier> implements
    AggregateManager<T, ID> {

    private final ThreadLocal<AggregateSnapshotContext<T, ID>> context;

    public ThreadLocalAggregateManager(Class<? extends T> targetClass) {
        this.context = ThreadLocal.withInitial(() -> new AggregateSnapshotContext<>(targetClass));
    }

    @Override
    public void attach(T aggregate) {
        context.get().attach(aggregate);
    }

    @Override
    public void detach(T aggregate) {
        context.get().detach(aggregate);
    }

    @Override
    public EntityDiff detectChanges(T aggregate) {
        return context.get().detectChanges(aggregate);
    }

    @Override
    public void merge(T aggregate) {
        context.get().merge(aggregate);
    }

}
