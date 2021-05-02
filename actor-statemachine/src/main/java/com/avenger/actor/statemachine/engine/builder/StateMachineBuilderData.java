package com.avenger.actor.statemachine.engine.builder;

import com.avenger.actor.statemachine.engine.ActionLabelStateMachine;
import com.avenger.actor.statemachine.engine.MachineData;
import com.avenger.actor.statemachine.engine.context.SpringExecuteContext;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;

/**
 * Description:
 *
 * Date: 2021/4/22
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class StateMachineBuilderData {

    private StateMachineBuilder<ActionLabelStateMachine, String, String, SpringExecuteContext> actionLabelStateMachineBuilder;

    private MachineData machineData;

    public StateMachineBuilder<ActionLabelStateMachine, String, String, SpringExecuteContext> getActionLabelStateMachineBuilder() {
        return this.actionLabelStateMachineBuilder;
    }

    public void setActionLabelStateMachineBuilder(
        StateMachineBuilder<ActionLabelStateMachine, String, String, SpringExecuteContext> actionLabelStateMachineBuilder) {
        this.actionLabelStateMachineBuilder = actionLabelStateMachineBuilder;
    }

    public MachineData getMachineData() {
        return this.machineData;
    }

    public void setMachineData(MachineData machineData) {
        this.machineData = machineData;
    }
}
