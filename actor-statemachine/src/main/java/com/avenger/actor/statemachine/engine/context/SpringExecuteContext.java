package com.avenger.actor.statemachine.engine.context;

import com.avenger.actor.statemachine.instance.BpmInstance;

/**
 * Description:
 *
 * Date: 2021/3/3
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class SpringExecuteContext implements ExecuteContext {

    private String instanceId;

    private BpmInstance bpmInstance;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public BpmInstance getBpmInstance() {
        return bpmInstance;
    }

    public void setBpmInstance(BpmInstance bpmInstance) {
        this.bpmInstance = bpmInstance;
    }

    public SpringExecuteContext(String instanceId, BpmInstance bpmInstanceMapper) {
        this.instanceId = instanceId;
        this.bpmInstance = bpmInstanceMapper;
    }

}
