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
public class HandleTaskCmd extends BaseCommand {

    private static final long serialVersionUID = 8455961062151393443L;

    private String instanceId;

    private String taskId;

    private String actionName;

    private Integer version;
}
