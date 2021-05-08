package com.avenger.actor.response;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
public enum HttpStatusEnum {
    SUCCESS(0, "success"),
    BAD_REQUEST(20400, "请求错误"),
    UNAUTHORIZED(20401, "鉴权失败"),
    CONFLICT(20409, "校验数据失败"),
    INTERNAL_SERVER_ERROR(20500, "服务器错误");

    private final int status;

    private final String message;

    HttpStatusEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int status() {
        return this.status;
    }

    public String message() {
        return this.message;
    }

    public String toString() {
        return this.status + ": " + this.message;
    }
}
