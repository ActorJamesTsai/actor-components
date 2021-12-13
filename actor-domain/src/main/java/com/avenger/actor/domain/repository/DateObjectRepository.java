/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.domain.repository;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.avenger.actor.domain.DateObject;
import com.avenger.actor.domain.Identifier;
import java.util.List;
import java.util.function.ToIntFunction;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2021/8/12
 */
public interface DateObjectRepository<T extends DateObject<ID>, ID extends Identifier> {

    /**
     * 新增或更新
     *
     * @param t dataObject
     * @param insert 新增
     * @param update 更新
     * @return 结果
     */
    default boolean insertOrUpdate(T t, ToIntFunction<T> insert, ToIntFunction<T> update) {
        if (ObjectUtil.isNull(t.getUniqueId())) {
            return insert.applyAsInt(t) == 1;
        } else {
            return update.applyAsInt(t) == 1;
        }
    }

    /**
     * 批量新增或更新
     *
     * @param list 集合
     * @param insert 新增
     * @param update 更新
     * @return 结果
     */
    default boolean insertOrUpdate(List<T> list, ToIntFunction<T> insert, ToIntFunction<T> update) {
        if (CollUtil.isEmpty(list)) {
            return Boolean.FALSE;
        }
        return list.stream().filter(t -> insertOrUpdate(t, insert, update)).count() == list.size();
    }
}
