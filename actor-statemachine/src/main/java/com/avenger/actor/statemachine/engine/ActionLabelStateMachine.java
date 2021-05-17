package com.avenger.actor.statemachine.engine;

import com.avenger.actor.statemachine.engine.builder.StateMachineBuilderFacade;
import com.avenger.actor.statemachine.engine.context.SpringExecuteContext;
import com.avenger.actor.statemachine.instance.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * Description:
 *
 * Date: 2021/4/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class ActionLabelStateMachine extends
    AbstractStateMachine<ActionLabelStateMachine, String, String, SpringExecuteContext> {

    private static final Logger log = LoggerFactory.getLogger(StateMachineBuilderFacade.class);

    protected void stateTransitAction(String stateFrom, String stateTo, String action, SpringExecuteContext context) {
        System.out.println("stateTransitAction:" + stateFrom + "-" + stateTo + ":" + action + ":instanceid=" + context
            .getInstanceId());
        Instance currentInstance = context.getBpmInstance().selectOneById(context.getInstanceId());
        if (currentInstance != null) {
            currentInstance.setCurrentNodeId(stateTo);
            context.getBpmInstance().updateByPrimaryKey(currentInstance);
            System.out.println("updated currentInstance ");
        } else {
            log.error("State instance update is failed");
        }
    }
}
