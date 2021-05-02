package com.avenger.actor.response;


import javax.xml.ws.Response;

public class Result {

    public static <T> Response<T> success(T data) {
        return new Response<>(data);
    }

    public static <T> Response<T> badRequest(T data) {
        return new Response<>(HttpStatusEnum.BAD_REQUEST, data);
    }

    public static <T> Response<T> badRequest(String message) {
        return new Response<>(HttpStatusEnum.BAD_REQUEST.status(), message);
    }

    public static <T> Response<T> conflict(T data) {
        return new Response<>(HttpStatusEnum.CONFLICT, data);
    }

    public static <T> Response<T> conflict(String message) {
        return new Response<>(HttpStatusEnum.CONFLICT.status(), message);
    }

    public static <T> Response<T> error(T data) {
        return new Response<>(HttpStatusEnum.INTERNAL_SERVER_ERROR, data);
    }

    public static <T> Response<T> error(String message) {
        return new Response<>(HttpStatusEnum.INTERNAL_SERVER_ERROR.status(), message);
    }

    public static <T> Response<T> unauthorized(T data) {
        return new Response<>(HttpStatusEnum.UNAUTHORIZED, data);
    }

    public static <T> Response<T> unauthorized(String message) {
        return new Response<>(HttpStatusEnum.UNAUTHORIZED.status(), message);
    }
}
