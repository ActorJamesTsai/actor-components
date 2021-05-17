package com.avenger.actor.statemachine.router;

import java.util.List;

/**
 * Description:
 *
 * Date: 2021/3/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public interface BpmRouter {

    List<Router> selectByDefKey(String defKey);
}
