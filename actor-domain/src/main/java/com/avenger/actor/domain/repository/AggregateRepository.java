/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.domain.repository;

import com.avenger.actor.domain.Aggregate;
import com.avenger.actor.domain.Identifier;
import java.util.Optional;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/8/12
 */
public interface AggregateRepository<T extends Aggregate<ID>, ID extends Identifier> {


    /**
     * 转载聚合
     *
     * @param aggregateRoot 聚合根
     * @return 聚合
     */
    Optional<T> load(ID aggregateRoot);

    /**
     * 卸载聚合
     *
     * @param aggregate 聚合
     * @return 聚合根主键
     */
    ID unload(T aggregate);
}
