package com.avenger.actor.statemachine.node;

import java.util.List;

/**
 * Description:
 *
 * Date: 2021/3/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface BpmNode {

    List<Node> selectByDefKey(String defKey);
}
