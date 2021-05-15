/**
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.token;

import org.springframework.stereotype.Component;

/**
 * @author JiaDu
 * @version 1.0.0
 * @Project: actor-components
 * @Description: date: 2021/5/15
 */
@Component
public class TokenRedisDao implements TokenDao {


    @Override
    public String get(String token) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }
}
