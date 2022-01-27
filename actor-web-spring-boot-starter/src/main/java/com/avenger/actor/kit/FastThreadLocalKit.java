/*
 * Copyright © 2021, WakeData Technology Co.,Ltd. All rights reserved. WakeData PROPRIETARY/CONFIDENTIAL. Use is subject
 * to license terms.
 */
package com.avenger.actor.kit;

import io.netty.util.concurrent.FastThreadLocal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JiaDu
 * @version 1.0.0
 * @since 2022/1/25
 */
public class FastThreadLocalKit {

    private static final FastThreadLocal<Map<String, Object>> FAST_THREAD_LOCAL = new FastThreadLocal<Map<String, Object>>() {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>(16);
        }
    };


    public static void put(String key, Object value) {
        FAST_THREAD_LOCAL.get().put(key, value);
    }


    public static Object get(String key) {
        return FAST_THREAD_LOCAL.get().get(key);
    }


    public static Object remove(String key) {
        return FAST_THREAD_LOCAL.get().remove(key);
    }


    public static Map<String, Object> entries() {
        return FAST_THREAD_LOCAL.get();
    }
}