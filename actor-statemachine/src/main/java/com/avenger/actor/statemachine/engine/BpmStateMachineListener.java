package com.avenger.actor.statemachine.engine;

import com.avenger.actor.statemachine.engine.context.SpringExecuteContext;
import com.avenger.actor.statemachine.instance.BpmInstance;
import com.avenger.actor.statemachine.instance.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.StateMachine;

/**
 * Description:
 *
 * Date: 2021/4/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class BpmStateMachineListener implements
    StateMachine.StateMachineListener<ActionLabelStateMachine, String, String, SpringExecuteContext> {

    private static final Logger log = LoggerFactory.getLogger(BpmStateMachineListener.class);

    private final String instanceId;

    private final BpmInstance bpmInstance;

    public BpmStateMachineListener(String instanceId, BpmInstance bpmInstance) {
        this.instanceId = instanceId;
        this.bpmInstance = bpmInstance;
    }

    public void stateMachineEvent(
        StateMachine.StateMachineEvent<ActionLabelStateMachine, String, String, SpringExecuteContext> event) {
        if (event instanceof StateMachine.StartEvent) {
            Instance currentInstance = this.bpmInstance.selectOneById(this.instanceId);
            if (currentInstance != null) {
                currentInstance.setCurrentNodeId(event.getStateMachine().getCurrentState());
                this.bpmInstance.updateByPrimaryKey(currentInstance);
            } else {
                log.error("State instance update is failed");
            }
        }
    }
}
