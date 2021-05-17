package com.avenger.actor.statemachine.engine;

import com.avenger.actor.statemachine.engine.context.SpringExecuteContext;
import java.util.List;

/**
 * Description:
 *
 * Date: 2021/4/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class BpmStateMachine {

    private String instanceId;

    private ActionLabelStateMachine masterStateMachine;

    private List<ActionLabelStateMachine> branchStateMachineList;

    public BpmStateMachine() {
    }

    public BpmStateMachine(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public ActionLabelStateMachine getMasterStateMachine() {
        return masterStateMachine;
    }

    public void setMasterStateMachine(ActionLabelStateMachine masterStateMachine) {
        this.masterStateMachine = masterStateMachine;
    }

    public List<ActionLabelStateMachine> getBranchStateMachineList() {
        return branchStateMachineList;
    }

    public void setBranchStateMachineList(
        List<ActionLabelStateMachine> branchStateMachineList) {
        this.branchStateMachineList = branchStateMachineList;
    }

    public void executeAction(String actionName, SpringExecuteContext context) {
        boolean isAccept = this.masterStateMachine.canAccept(actionName);
        if (isAccept) {
            this.masterStateMachine.fire(actionName, context);
        } else {
            throw new RuntimeException("The actionName: '" + actionName + "' is unknown");
        }
    }

    public String getCurrentState() {
        return this.masterStateMachine.getCurrentState();
    }
}
