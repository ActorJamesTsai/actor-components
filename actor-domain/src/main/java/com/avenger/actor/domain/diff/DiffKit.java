package com.avenger.actor.domain.diff;

import cn.hutool.core.util.ReflectUtil;
import com.avenger.actor.domain.Aggregate;
import com.avenger.actor.domain.BaseEntity;
import com.avenger.actor.domain.Identifier;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * Date: 2021/12/13
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class DiffKit {

    private DiffKit() {
    }

    public static <T extends Aggregate<ID>, ID extends Identifier> EntityDiff diff(T snapshot, T aggregate) {
        EntityDiff entityDiff = new EntityDiff();
        entityDiff.setSelfModified(false);
        entityDiff.setEmpty(true);

        List<String> keys = Arrays.stream(ReflectUtil.getFields(snapshot.getClass())).map(Field::getName).collect(
            Collectors.toList());
        for (String key : keys) {
            try {
                Object snapshotVal = ReflectUtil.getFieldValue(snapshot, key);
                Object aggregateVal = ReflectUtil.getFieldValue(aggregate, key);

                if (snapshotVal instanceof Collection) {
                    ListDiff listDiff =
                        dealListDiff((Collection<BaseEntity<ID>>) snapshotVal,
                            (Collection<BaseEntity<ID>>) aggregateVal);
                    if (listDiff.getDiffs().isEmpty()) {
                        entityDiff.setEmpty(false);
                    }
                    entityDiff.setDiff(key, listDiff);
                    continue;
                }

                if (!snapshotVal.equals(aggregateVal)) {
                    entityDiff.setSelfModified(true);
                    entityDiff.setEmpty(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return entityDiff;
    }

    private static <ID extends Identifier> ListDiff dealListDiff(Collection<BaseEntity<ID>> snapshotListVal,
        Collection<BaseEntity<ID>> aggregateListVal) {
        ListDiff listDiff = new ListDiff();
        List<Diff> diffs = new ArrayList<>();

        if (aggregateListVal == null || aggregateListVal.isEmpty()) {
            listDiff.setDiffs(diffs);
            return listDiff;
        }

        for (BaseEntity<ID> obj : aggregateListVal) {
            Diff diff = new Diff();
            DiffType type = snapshotListVal.stream()
                .filter(baseEntity -> baseEntity.getUniqueId().equals(obj.getUniqueId()))
                .findFirst()
                .map(snapshot -> {
                    if (compareValues(snapshot, obj)) {
                        diff.setOldValue(snapshot);
                        diff.setNewValue(obj);
                        return DiffType.modify;
                    }
                    return DiffType.unchanged;
                })
                .orElseGet(() -> {
                    diff.setOldValue(null);
                    diff.setNewValue(obj);
                    return DiffType.add;
                });
            diff.setType(type);
            diffs.add(diff);
        }
        listDiff.setDiffs(diffs);

        return listDiff;
    }

    private static boolean compareValues(Object oldValue, Object newValue) {
        List<String> keys = Arrays.stream(ReflectUtil.getFields(oldValue.getClass())).map(Field::getName).collect(
            Collectors.toList());
        boolean flag = false;
        for (String key : keys) {
            Object oldV = ReflectUtil.getFieldValue(oldValue, key);
            Object newV = ReflectUtil.getFieldValue(newValue, key);
            if (!oldV.equals(newV)) {
                return true;
            }
        }

        return flag;
    }
}