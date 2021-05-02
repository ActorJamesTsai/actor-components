package com.avenger.actor.exception;

/**
 * Description:
 *
 * Date: 2021/5/1
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class BaseException extends RuntimeException {


    private static final long serialVersionUID = 1567402028369219391L;


    private final String message;

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(int status, String message) {
        super(message);
        this.message = message;
    }
}
