package com.avenger.actor.exception;

import com.avenger.actor.response.Response;
import com.avenger.actor.response.Result;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({RestException.class})
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Object> serviceErrorHandler(RestException ex) {
        return ex.toResponse();
    }

    @ExceptionHandler({SecurityException.class})
    @ResponseStatus(code = HttpStatus.OK)
    public Response<String> securityErrorHandler(SecurityException ex) {
        logger.warn("SecurityException: ", ex);
        return Result.unauthorized(ex.getMessage());
    }

    /*@ExceptionHandler({ValidationException.class})
    public Response<String> validationErrorHandler(ValidationException ex) {
        return Result.badRequest("validation error.");
    }*/

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response<String> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.warn("MethodArgumentNotValidException:", (Throwable) ex);
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        StringBuilder sb = new StringBuilder("数据校验错误：数量:[" + ex.getBindingResult().getAllErrors().size() + "]:错误信息:(");
        for (ObjectError objectError : errors) {
            sb.append("[").append(objectError.getDefaultMessage()).append("]");
        }
        String error = sb.append(")").toString();
        logger.warn(error);
        return Result.badRequest(error);
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    public Response<String> defaultErrorHandler(Exception ex) {
        logger.error("Internal Server Error: ", ex);
        return Result.error("Internal Server Error.");
    }
}
