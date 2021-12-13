package com.avenger.actor.domain.repository;

import com.avenger.actor.domain.Aggregate;
import com.avenger.actor.domain.Identifier;
import com.avenger.actor.domain.diff.DiffKit;
import com.avenger.actor.domain.diff.EntityDiff;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * for example
 *
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/12/13
 */
public class AggregateSnapshotContext<T extends Aggregate<ID>, ID extends Identifier> {

    private final Class<? extends T> aggregateClass;

    private final Map<ID, T> aggregateMap = new ConcurrentHashMap<>();

    public AggregateSnapshotContext(Class<? extends T> aggregateClass) {
        this.aggregateClass = aggregateClass;
    }

    public Class<? extends T> getAggregateClass() {
        return aggregateClass;
    }

    public void attach(T aggregate) {
        if (aggregate.getUniqueId() != null) {
            if (!aggregateMap.containsKey(aggregate.getUniqueId())) {
                this.merge(aggregate);
            }
        }
    }

    public void detach(T aggregate) {
        if (aggregate.getUniqueId() != null) {
            aggregateMap.remove(aggregate.getUniqueId());
        }
    }

    public EntityDiff detectChanges(T aggregate) {
        if (aggregate.getUniqueId() == null) {
            return EntityDiff.EMPTY;
        }
        T snapshot = aggregateMap.get(aggregate.getUniqueId());
        if (snapshot == null) {
            return EntityDiff.EMPTY;
        }
        return DiffKit.diff(snapshot, aggregate);
    }

    public void merge(T aggregate) {
        if (aggregate.getUniqueId() != null) {
            /*T snapshot = SnapshotKit.snapshot(aggregate);*/
            aggregateMap.put(aggregate.getUniqueId(), aggregate);
        }
    }

}
