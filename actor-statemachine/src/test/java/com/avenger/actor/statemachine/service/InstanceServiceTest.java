package com.avenger.actor.statemachine.service;

import com.avenger.actor.statemachine.command.StartInstanceCmd;
import javax.annotation.Resource;
import org.junit.Test;

/**
 * Description:
 *
 * Date: 2021/5/9
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class InstanceServiceTest {

    @Resource
    private InstanceService instanceService;


    class MyStartInstanceCmd extends StartInstanceCmd {

        private static final long serialVersionUID = -6127894643831601457L;
    }

    @Test
    public void startInstance() {
        MyStartInstanceCmd myStartInstanceCmd = new MyStartInstanceCmd();
        instanceService.startInstance(myStartInstanceCmd);
    }
}
