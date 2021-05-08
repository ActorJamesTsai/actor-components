package com.avenger.actor.exception;

import com.alibaba.fastjson.JSON;
import com.avenger.actor.response.HttpStatusEnum;
import com.avenger.actor.response.Response;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class RestException extends BaseException {

    private static final long serialVersionUID = 2305201073664000499L;

    private final int status;

    private Object data;

    public RestException(HttpStatusEnum httpStatus) {
        super(httpStatus.message());
        this.status = httpStatus.status();
    }

    public <T> RestException(HttpStatusEnum httpStatus, T data) {
        super(httpStatus.message());
        this.status = httpStatus.status();
        this.data = data;
    }

    public RestException(int status, String message) {
        super(message);
        this.status = status;
    }

    public <T> RestException(int status, String message, T data) {
        super(message);
        this.status = status;
        this.data = data;
    }

    public RestException(String message) {
        super(message);
        this.status = HttpStatusEnum.INTERNAL_SERVER_ERROR.status();
    }

    public Response<Object> toResponse() {
        return new Response<>(this.status, super.getMessage(), this.data);
    }

    public String toString() {
        return JSON.toJSONString(toResponse());
    }
}
