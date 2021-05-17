package com.avenger.actor.statemachine.command;

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
public class TransferTaskCmd extends HandleTaskCmd{

    private static final long serialVersionUID = -8528693170024899336L;
}
