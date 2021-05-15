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
public interface TokenInfo {

    /**
     * 获取token
     *
     * @return token
     */
    String getToken();

    /**
     * 获取登录时间
     *
     * @return 登录时间
     */
    long getSignInTime();

    /**
     * 获取过期时间
     *
     * @return 过期时间
     */
    long getExpirationTime();

    /**
     * 获取状态
     */
    TokenState getState();


    enum TokenState {
        /**
         * 正常
         */
        normal,
        /**
         * 过期
         */
        expired;
    }
}
