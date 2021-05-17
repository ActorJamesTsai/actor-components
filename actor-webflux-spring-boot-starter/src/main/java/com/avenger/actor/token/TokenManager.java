/**
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.token;

/**
 * @author JiaDu
 * @version 1.0.0
 * @Project: actor-components
 * @Description: date: 2021/5/15
 */
public interface TokenManager {


    String get(String token);

    void set(String key, String value);

    void delete(String key);
}
