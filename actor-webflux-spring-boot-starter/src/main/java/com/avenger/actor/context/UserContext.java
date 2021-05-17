/**
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.context;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author JiaDu
 * @version 1.0.0
 * @Project: actor-components
 * @Description:
 * @date: 2021/5/15
 */
public class UserContext {


    public static CurrentUser getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
            .getRequest();
        return (CurrentUser) request.getAttribute("currentUser");
    }


    interface CurrentUser extends Serializable {

        String getUserId();

        String getUserName();

        UserState getState();
    }


    enum UserState {

        normal,

        invalid,

        lock;
    }
}
