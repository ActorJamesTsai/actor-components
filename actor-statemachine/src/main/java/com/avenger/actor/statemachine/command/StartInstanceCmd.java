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
public class StartInstanceCmd extends BaseCommand {

    private static final long serialVersionUID = 2914938045535244465L;


    private String defKey;

    private Integer defVersion;

    private String appointNode;

}
