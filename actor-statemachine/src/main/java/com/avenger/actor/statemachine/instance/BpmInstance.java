package com.avenger.actor.statemachine.instance;

/**
 * Description:
 *
 * Date: 2021/3/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface BpmInstance {

    Instance selectOneById(String instanceId);

    void updateByPrimaryKey(Instance currentInstance);
}
