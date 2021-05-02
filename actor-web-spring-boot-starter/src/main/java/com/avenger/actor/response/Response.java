package com.avenger.actor.response;

import com.alibaba.fastjson.JSONObject;

public class Response<T> {
  private int status;

  private String message;

  private T data;

  public Response() {}

  public Response(HttpStatusEnum httpStatus) {
    this.status = httpStatus.status();
    this.message = httpStatus.message();
  }

  public Response(HttpStatusEnum httpStatus, T data) {
    this.status = httpStatus.status();
    this.message = httpStatus.message();
    this.data = data;
  }

  public Response(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public Response(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public Response(T data) {
    this.status = HttpStatusEnum.SUCCESS.status();
    this.message = HttpStatusEnum.SUCCESS.message();
    this.data = data;
  }
  
  public int getStatus() {
    return this.status;
  }
  
  public void setStatus(int status) {
    this.status = status;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public T getData() {
    return this.data;
  }
  
  public void setData(T data) {
    this.data = data;
  }
  
  public String toString() {
    return JSONObject.toJSONString(this);
  }
}
