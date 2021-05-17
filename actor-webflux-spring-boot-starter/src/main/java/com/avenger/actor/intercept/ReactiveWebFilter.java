package com.avenger.actor.intercept;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class ReactiveWebFilter implements WebFilter {


    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
        WebFilterChain webFilterChain) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ServerHttpRequest request = serverWebExchange.getRequest();

        String token = request.getHeaders().getFirst("token");

        if (StrUtil.isBlank(token)) {

        }

        return webFilterChain.filter(serverWebExchange).sub()
    }
}
