package com.avenger.actor.statemachine.aggregate;

import com.avenger.actor.domain.Aggregate;
import com.avenger.actor.statemachine.command.HandleTaskCmd;
import com.avenger.actor.statemachine.instance.Instance;
import com.avenger.actor.statemachine.task.Task;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
@Getter
@Setter
public class InstanceAggregate implements Aggregate {

    private static final long serialVersionUID = -695001090666793221L;

    private Instance instance;

    private List<Task> taskList;

    @Override
    public Object getUniqueId() {
        return instance.getId();
    }

    public boolean checkCurrentTask(HandleTaskCmd handleTaskCmd) {

    }
}
