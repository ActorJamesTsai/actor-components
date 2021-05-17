package com.avenger.actor.statemachine.aggregate;

import com.avenger.actor.domain.Aggregate;
import com.avenger.actor.statemachine.node.Node;
import com.avenger.actor.statemachine.router.Router;
import com.avenger.actor.statemachine.task.Task;
import java.util.List;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class TaskAggregate implements Aggregate {

    private static final long serialVersionUID = 5566954907459990734L;

    private Task task;

    private Node node;

    private List<Router> routerList;

    @Override
    public Object getUniqueId() {
        return task.getId();
    }
}
