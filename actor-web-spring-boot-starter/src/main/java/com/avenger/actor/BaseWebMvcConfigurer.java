package com.avenger.actor;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.avenger.actor.intercept.SsoFilter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Description:
 *
 * Date: 2021/5/1
 *
 * @author JiaDu
 * @version 1.0.0
 */
@Configuration
public class BaseWebMvcConfigurer implements WebMvcConfigurer {

    @Value("${security.sso.exclusions}")
    private String exclude;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${security.cors.origin}")
    private String origin;

    @Value("${security.cors.corsFlag:true}")
    private Boolean corsFlag;

    @Value("${security.cors.maxAge:18000}")
    private String maxAge;

    private String[] exclusions;

    public String[] getExclusions() {
        return this.exclusions;
    }


    @PostConstruct
    public void init() {
        this.exclude =
            StringUtils.isEmpty(this.exclude) ? "/sso/**,/actuator/**" : ("/sso/**,/actuator/**," + this.exclude);
        this.exclusions = this.exclude.trim().split("[ ,;]+");
        for (int i = 0; i < this.exclusions.length; i++) {
            if (this.exclusions[i].startsWith("/") && this.contextPath.length() > 0) {
                this.exclusions[i] = this.contextPath + this.exclusions[i].trim();
            }
        }
    }

    @Bean
    public FilterRegistrationBean<SsoFilter> ssoFilterRegistration() {
        FilterRegistrationBean<SsoFilter> filter = new FilterRegistrationBean<>();
        filter.setName("ssoFilter");
        SsoFilter ssoFilter = new SsoFilter(this.exclusions, this.origin);
        ssoFilter.setCorsFlag(this.corsFlag);
        ssoFilter.setMaxAge(this.maxAge);
        filter.setFilter(ssoFilter);
        filter.setOrder(-1);
        filter.addUrlPatterns("/*");
        return filter;
    }


    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat,
            SerializerFeature.DisableCircularReferenceDetect);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConverter);
    }
}
