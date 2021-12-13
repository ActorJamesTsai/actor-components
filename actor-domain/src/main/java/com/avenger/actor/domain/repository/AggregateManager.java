package com.avenger.actor.domain.repository;

import com.avenger.actor.domain.Aggregate;
import com.avenger.actor.domain.Identifier;
import com.avenger.actor.domain.diff.EntityDiff;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/12/13
 */
public interface AggregateManager<T extends Aggregate<ID>, ID extends Identifier> {

    /**
     * 将一个Aggregate附属到一个Repository，让它变为可追踪
     */
    void attach(T aggregate);

    /**
     * 解除一个Aggregate的追踪
     */
    void detach(T aggregate);

    EntityDiff detectChanges(T aggregate);

    void merge(T aggregate);
}
