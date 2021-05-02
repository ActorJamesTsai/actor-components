package com.avenger.actor.statemachine.engine.builder;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.avenger.actor.statemachine.definition.BpmDefinition;
import com.avenger.actor.statemachine.definition.Definition;
import com.avenger.actor.statemachine.engine.ActionLabelStateMachine;
import com.avenger.actor.statemachine.engine.BpmStateMachine;
import com.avenger.actor.statemachine.engine.BpmStateMachineListener;
import com.avenger.actor.statemachine.engine.MachineData;
import com.avenger.actor.statemachine.engine.context.SpringExecuteContext;
import com.avenger.actor.statemachine.engine.graph.AdjacencyListGraph;
import com.avenger.actor.statemachine.instance.BpmInstance;
import com.avenger.actor.statemachine.instance.Instance;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;

/**
 * Description:
 *
 * Date: 2021/4/22
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class StateMachineBuilderFacade {

    private static final Logger log = LoggerFactory.getLogger(StateMachineBuilderFacade.class);

    private static final Map<String, StateMachineBuilderData> stateMachineBuilderDataCache = new ConcurrentHashMap<>();

    private static final Map<String, BpmStateMachine> bpmStateMachineInstanceCache = new ConcurrentHashMap<>();


    private final BpmDefinition bpmDefinition;

    private final BpmInstance bpmInstance;

    public StateMachineBuilderFacade(BpmDefinition bpmDefinition, BpmInstance bpmInstance) {
        this.bpmDefinition = bpmDefinition;
        this.bpmInstance = bpmInstance;
    }

    /**
     * @param machineId 流程版本主键 --> 对应状态机主键
     * @param machineVersion 流程版本号 --> 对应状态机版本
     */
    public void createStateMachine(String machineId, int machineVersion, MachineData machineData) {
        String machineUnionId = machineId + "-" + machineVersion;
        StateMachineBuilder<ActionLabelStateMachine, String, String, SpringExecuteContext> builder = StateMachineBuilderFactory
            .create(ActionLabelStateMachine.class, String.class, String.class, SpringExecuteContext.class);
        try {
            buildTransition(builder, machineData);
            for (String finishState : machineData.getFinishState()) {
                builder.defineFinalState(finishState);
            }
            StateMachineBuilderData builderData = new StateMachineBuilderData();
            builderData.setActionLabelStateMachineBuilder(builder);
            builderData.setMachineData(machineData);
            stateMachineBuilderDataCache.put(machineUnionId, builderData);
        } catch (Exception e) {
            log.error("Create state machine is failed. Machine ID: " + machineUnionId, e);
        }
    }

    public String startStateMachineInstance(String machineId, int machineVersion) {
        return startStateMachineInstance(machineId, machineVersion, null);
    }

    public String startStateMachineInstance(String machineId, int machineVersion, String initialState) {
        return startStateMachineInstance(machineId, machineVersion, null, null);
    }

    /**
     * after bpmDataInitService.init()
     *
     * @param instanceId 流程实例id
     */
    public String startStateMachineInstance(String machineId, int machineVersion, String initialState,
        String instanceId) {
        String machineUnionId = machineId + "-" + machineVersion;
        StateMachineBuilderData builderData = stateMachineBuilderDataCache.get(machineUnionId);
        if (builderData == null) {
            throw new RuntimeException("Can not find state machine: " + machineUnionId);
        }
        StateMachineBuilder<ActionLabelStateMachine, String, String, SpringExecuteContext> builder = builderData
            .getActionLabelStateMachineBuilder();
        ActionLabelStateMachine actionLabelStateMachine;
        if (StrUtil.isBlank(initialState)) {
            actionLabelStateMachine = builder.newStateMachine(builderData.getMachineData().getStartState());
        } else {
            actionLabelStateMachine = builder.newStateMachine(initialState);
        }
        if (StrUtil.isBlank(instanceId)) {
            instanceId = IdUtil.objectId();
        }
        if (bpmStateMachineInstanceCache.get(instanceId) != null) {
            log.warn("The state instance {} started already", instanceId);
            return instanceId;
        }
        stateMachineInstance(instanceId, actionLabelStateMachine);
        return instanceId;
    }

    /**
     * bpmDataInitService.init();
     *
     * @param instanceId 流程实例id
     * @param actionName 执行参数
     */
    public void executeAction(String instanceId, String actionName) {
        BpmStateMachine bpmStateMachine;
        if (bpmStateMachineInstanceCache.get(instanceId) == null) {
            bpmStateMachine = recoverStateMachineInstance(instanceId);
        } else {
            bpmStateMachine = bpmStateMachineInstanceCache.get(instanceId);
        }
        bpmStateMachine.executeAction(actionName, new SpringExecuteContext(instanceId, this.bpmInstance));
    }

    /**
     * bpmDataInitService.init();
     *
     * @param instanceId 流程实例id
     */
    public String getCurrentState(String instanceId) {
        BpmStateMachine bpmStateMachine;
        if (bpmStateMachineInstanceCache.get(instanceId) == null) {
            bpmStateMachine = recoverStateMachineInstance(instanceId);
        } else {
            bpmStateMachine = bpmStateMachineInstanceCache.get(instanceId);
        }
        return bpmStateMachine.getCurrentState();
    }


    private void buildTransition(
        StateMachineBuilder<ActionLabelStateMachine, String, String, SpringExecuteContext> builder,
        MachineData machineData) {
        AdjacencyListGraph<MachineData.TransitTableItem> adjacencyListGraph = new AdjacencyListGraph<>();
        adjacencyListGraph.initGraph(machineData.getTransitTableItemList());
        adjacencyListGraph.breadthFirstTravelEdge(
            (fromNode, toNode) -> builder.externalTransition().from(fromNode.getValue()).to(toNode.getValue())
                .on(toNode.getData().getActionName())
                .callMethod("stateTransitAction"));
    }


    /**
     * 恢复流程状态
     *
     * @param instanceId 流程实例
     */
    private BpmStateMachine recoverStateMachineInstance(String instanceId) {
        Instance currentInstance = this.bpmInstance.selectOneById(instanceId);
        if (currentInstance == null) {
            throw new RuntimeException("The state instance {} is miss");
        }
        Definition currentDefine = this.bpmDefinition.selectByInstId(instanceId);
        if (currentDefine == null) {
            throw new RuntimeException("The state machine {} is not defined");
        }
        String machineUnionId = currentDefine.getId() + "-" + currentDefine.getVersion();
        StateMachineBuilderData builderData = stateMachineBuilderDataCache.get(machineUnionId);
        if (builderData == null) {
            throw new RuntimeException("Can not find state machine: " + machineUnionId);
        }
        if (bpmStateMachineInstanceCache.get(instanceId) != null) {
            log.warn("The state machine {} started already", instanceId);
        }
        StateMachineBuilder<ActionLabelStateMachine, String, String, SpringExecuteContext> builder = builderData
            .getActionLabelStateMachineBuilder();
        ActionLabelStateMachine actionLabelStateMachine = builder.newStateMachine(currentInstance.getCurrentNodeId());
        return stateMachineInstance(instanceId, actionLabelStateMachine);
    }

    private BpmStateMachine stateMachineInstance(String instanceId, ActionLabelStateMachine actionLabelStateMachine) {
        actionLabelStateMachine
            .addStateMachineListener(new BpmStateMachineListener(instanceId, this.bpmInstance));
        actionLabelStateMachine.start(new SpringExecuteContext(instanceId, this.bpmInstance));
        BpmStateMachine bpmStateMachine = new BpmStateMachine(instanceId);
        bpmStateMachine.setMasterStateMachine(actionLabelStateMachine);
        bpmStateMachineInstanceCache.put(instanceId, bpmStateMachine);
        return bpmStateMachine;
    }


}
