package com.avenger.actor.statemachine.engine.builder;

import com.avenger.actor.statemachine.definition.BpmDefinition;
import com.avenger.actor.statemachine.instance.BpmInstance;
import com.avenger.actor.statemachine.node.BpmNode;
import com.avenger.actor.statemachine.router.BpmRouter;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Description:
 *
 * Date: 2021/5/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class StateMachineBuilderFacadeTest {


    @Resource
    private BpmDefinition bpmDefinition;

    @Resource
    private BpmInstance bpmInstance;

    @Resource
    private BpmRouter bpmRouter;

    @Resource
    private BpmNode bpmNode;


    private final StateMachineBuilderFacade stateMachineBuilderFacade = new StateMachineBuilderFacade(bpmDefinition,
        bpmInstance);


    @Test
    private void init() {
        /*try {
            List<Definition> bpmDefinitionList = this.bpmDefinition.findAll();
            if (CollectionUtils.isEmpty(bpmDefinitionList)) {
                return;
            }
            List<Router> bpmRouterList = this.bpmRouter.findAll();
            List<Node> bpmNodeList = this.bpmNode.findAll();
            for (Definition bpmDefinition : bpmDefinitionList) {
                MachineData machineData = new MachineData();
                machineData.setTransitTableItemList(new ArrayList<>());
                machineData.setFinishState(new ArrayList<>());
                for (Router bpmRouter : bpmRouterList) {
                    if (bpmDefinition.getId() != null && bpmDefinition.getId().equals(bpmRouter.getDefinitionId())
                        && bpmRouter.getDefinitionVersion() != null && bpmDefinition.getVersion() != null
                        && bpmDefinition.getVersion().intValue() == bpmRouter.getDefinitionVersion().intValue()) {
                        TransitTableItem tableItem = new TransitTableItem();
                        BeanUtil.copyProperties(tableItem, bpmRouter);
                        machineData.getTransitTableItemList().add(tableItem);
                    }
                }
                for (Node bpmNode : bpmNodeList) {
                    if (StrUtil.equals(bpmDefinition.getId(), bpmNode.getDefinitionId()) &&
                        ObjectUtil.equals(bpmDefinition.getVersion(), bpmNode.getDefinitionVersion())) {
                        if (bpmNode.getNodeType() == 0) {
                            machineData.setStartState(bpmNode.getNodeCode());
                        }
                        if (bpmNode.getNodeType() == 2) {
                            machineData.getFinishState().add(bpmNode.getNodeCode());
                        }
                    }
                }
                if (StrUtil.isBlank(machineData.getStartState()) || machineData.getFinishState().size() == 0
                    || machineData.getTransitTableItemList().isEmpty()) {
                    log.warn("流程 {}，版本 {}：节点或者路由的数据有问题，1 必须有开始和结束节点；2 必须有路由记录!", bpmDefinition.getKey(),
                        bpmDefinition.getVersion());
                    continue;
                }
                stateMachineBuilderFacade
                    .createStateMachine(bpmDefinition.getId(), bpmDefinition.getVersion(), machineData);
            }
        } catch (Exception e) {
            log.error("State engine init error", e);
        }*/
    }
}
