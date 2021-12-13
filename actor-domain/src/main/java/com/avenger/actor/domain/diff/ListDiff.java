package com.avenger.actor.domain.diff;

import java.util.List;

/**
 * Description:
 *
 * Date: 2021/12/13
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class ListDiff extends Diff {

    private List<Diff> diffs;

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }
}