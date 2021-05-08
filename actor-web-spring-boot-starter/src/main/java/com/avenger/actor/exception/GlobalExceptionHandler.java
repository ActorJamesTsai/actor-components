package com.avenger.actor.exception;

import com.avenger.actor.response.Response;
import com.avenger.actor.response.Result;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public Response<Object> serviceErrorHandler(HttpServletResponse resp, RestException ex) {
        return ex.toResponse();
    }

    @ExceptionHandler({SecurityException.class})
    public Response<String> securityErrorHandler(HttpServletResponse resp, SecurityException ex) {
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
        StringBuffer sb = new StringBuffer("数据校验错误：数量:[" + ex.getBindingResult().getAllErrors().size() + "]:错误信息:(");
        for (ObjectError objectError : errors) {
            sb.append("[").append(objectError.getDefaultMessage()).append("]");
        }
        String error = sb.append(")").toString();
        logger.warn(error);
        return Result.badRequest(error);
    }

    @ExceptionHandler({Exception.class})
    public Response<String> defaultErrorHandler(HttpServletResponse resp, Exception ex) throws Exception {
        logger.error("Internal Server Error: ", ex);
        return Result.error("Internal Server Error.");
    }
}
