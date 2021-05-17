/**
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.token;

/**
 * @author JiaDu
 * @version 1.0.0
 * @date: 2021/5/15
 */
public interface TokenFactory {


    String createToken(String... str);

    boolean verifyToken(String token);

}
