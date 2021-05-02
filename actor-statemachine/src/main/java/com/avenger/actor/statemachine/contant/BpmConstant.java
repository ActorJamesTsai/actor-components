package com.avenger.actor.statemachine.contant;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * Date: 2021/3/2
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class BpmConstant {


    public enum InstanceStatus {
        IN_SERVICE("inService", "运行中"),
        DISABLE("disabled", "禁用"),
        PUBLISHED("published", "发布");


        private final String code;

        private final String desc;

        InstanceStatus(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static Map<String, String> codeMap() {
            InstanceStatus[] values = values();
            return Arrays.stream(values)
                .collect(Collectors.toMap(InstanceStatus::getCode, InstanceStatus::getDesc));
        }

        public static Map<String, String> descMap() {
            InstanceStatus[] values = values();
            return Arrays.stream(values)
                .collect(Collectors.toMap(InstanceStatus::getDesc, InstanceStatus::getCode));
        }
    }


    public enum SupportMobile {
        SUPPORT(1, "支持手机审批"),
        UN_SUPPORT(0, "不支持手机审批");

        private final Integer code;

        private final String desc;

        SupportMobile(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public Integer getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
