package com.avenger.actor.statemachine.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.avenger.actor.statemachine.aggregate.DefinitionAggregate;
import com.avenger.actor.statemachine.aggregate.InstanceAggregate;
import com.avenger.actor.statemachine.command.StartInstanceCmd;
import com.avenger.actor.statemachine.command.StopInstanceCmd;
import com.avenger.actor.statemachine.definition.BpmDefinition;
import com.avenger.actor.statemachine.definition.Definition;
import com.avenger.actor.statemachine.engine.builder.StateMachineBuilderFacade;
import com.avenger.actor.statemachine.instance.BpmInstance;
import com.avenger.actor.statemachine.node.BpmNode;
import com.avenger.actor.statemachine.node.Node;
import com.avenger.actor.statemachine.router.BpmRouter;
import com.avenger.actor.statemachine.router.Router;
import com.avenger.actor.statemachine.task.BpmTask;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class InstanceService {

    @Resource
    private StateMachineBuilderFacade stateMachineBuilderFacade;

    @Resource
    private BpmDefinition bpmDefinition;
    @Resource
    private BpmNode bpmNode;
    @Resource
    private BpmRouter bpmRouter;
    @Resource
    private BpmInstance bpmInstance;
    @Resource
    private BpmTask bpmTask;


    @Resource
    private RedisTemplate<Object, Object> redisTemplate;


    public InstanceAggregate startInstance(StartInstanceCmd startInstanceCmd) {

        if (StrUtil.isBlank(startInstanceCmd.getDefKey())) {
            throw new RuntimeException("");
        }

        DefinitionAggregate definitionAggregate = findDefinition(startInstanceCmd.getDefKey(),
            startInstanceCmd.getDefVersion());

        if (ObjectUtil.isNull(definitionAggregate)) {
            throw new RuntimeException("");
        }

        InstanceAggregate instanceAggregate = new InstanceAggregate();

        saveInstance(instanceAggregate);

        stateMachineBuilderFacade.startStateMachineInstance(instanceAggregate.getInstance().getDefId(),
            instanceAggregate.getInstance().getDefVersion(), instanceAggregate.getUniqueId().toString());

        return instanceAggregate;
    }


    private void saveInstance(InstanceAggregate instanceAggregate) {
        bpmInstance.save(instanceAggregate.getInstance());
        instanceAggregate.getTaskList().forEach(task -> bpmTask.save(task));
    }

    private DefinitionAggregate findDefinition(String defKey, Integer defVersion) {
        redisTemplate.opsForValue().get()
        Definition definition = bpmDefinition.selectByDefKey(defKey, defVersion);
        List<Node> nodeList = bpmNode.selectByDefKey(defKey);
        List<Router> routerList = bpmRouter.selectByDefKey(defKey);

        DefinitionAggregate definitionAggregate = new DefinitionAggregate();
        definitionAggregate.setDefinition(definition);
        definitionAggregate.setNodeList(nodeList);
        definitionAggregate.setRouterList(routerList);
        return definitionAggregate;
    }


    public Boolean stopInstance(StopInstanceCmd stopInstanceCmd) {

        return Boolean.TRUE;
    }

    public InstanceAggregate findInstance(String instanceId) {
        redisTemplate.opsForValue().get()
    }

    public void updateInstance(InstanceAggregate instanceAggregate) {
    }
}
