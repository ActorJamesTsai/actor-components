package com.avenger.actor.statemachine.service;

import cn.hutool.core.util.StrUtil;
import com.avenger.actor.statemachine.aggregate.InstanceAggregate;
import com.avenger.actor.statemachine.aggregate.TaskAggregate;
import com.avenger.actor.statemachine.command.HandleTaskCmd;
import com.avenger.actor.statemachine.engine.builder.StateMachineBuilderFacade;
import com.avenger.actor.statemachine.instance.BpmInstance;
import com.avenger.actor.statemachine.task.BpmTask;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
@Slf4j
public class TaskService {

    @Resource
    private InstanceService instanceService;
    @Resource
    private StateMachineBuilderFacade stateMachineBuilderFacade;


    @Resource
    private BpmInstance bpmInstance;
    @Resource
    private BpmTask bpmTask;


    @Resource
    private RedissonClient redissonClient;

    public TaskAggregate handleTask(HandleTaskCmd handleTaskCmd) {
        RLock lock = redissonClient.getLock(
            StrUtil.format("FLOW:{}:{}", handleTaskCmd.getInstanceId(), handleTaskCmd.getTaskId()));
        try {
            boolean lockResult = lock.tryLock(200, 5000, TimeUnit.MILLISECONDS);
            if (lockResult) {
                InstanceAggregate instanceAggregate = instanceService.findInstance(handleTaskCmd.getInstanceId());
                boolean canHandle = instanceAggregate.checkCurrentTask(handleTaskCmd);

                stateMachineBuilderFacade.executeAction(handleTaskCmd.getInstanceId(), handleTaskCmd.getActionName());

                instanceService.updateInstance(instanceAggregate);
            }

        } catch (Exception e) {
            log.error("handleTask error:{}", e.getMessage());
        } finally {
            lock.unlock();
        }
        return null;
    }

    public boolean isJointlyTask() {

    }
}
