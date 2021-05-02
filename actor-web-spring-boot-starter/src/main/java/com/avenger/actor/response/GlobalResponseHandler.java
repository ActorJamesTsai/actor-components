package com.avenger.actor.response;

import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Description:
 *
 * Date: 2019/2/16
 *
 * @author JiaDu
 * @version 1.0.0
 */
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {


    @Value("${server.controller.path:^com..*controller..*Controller$}")
    private String regControllerPath;

    @Value("${server.response.type:^com.avenger.actor.response.Response.*}")
    private String regResponseType;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        String returnClass = methodParameter.getGenericParameterType().getTypeName();
        Class<?> executeClass = methodParameter.getExecutable().getDeclaringClass();
        String controller = executeClass.getCanonicalName();
        Pattern controllerPath = Pattern.compile(regControllerPath);
        Pattern responseType = Pattern.compile(regResponseType);
        return controllerPath.matcher(controller).matches() && !responseType.matcher(returnClass).matches();
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
        Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
        ServerHttpResponse serverHttpResponse) {

        if (null != body && Pattern.compile(regResponseType).matcher(body.getClass().getSimpleName()).matches()) {
            return body;
        }

        return Result.success(body);
    }
}
