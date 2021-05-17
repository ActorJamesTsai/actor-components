package com.avenger.actor.statemachine.command;

import com.avenger.actor.domain.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StopInstanceCmd extends BaseCommand {

    private static final long serialVersionUID = 3015826055342107255L;

    private String instanceId;
}
