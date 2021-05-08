package com.avenger.actor.intercept;

import com.avenger.actor.response.Response;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StopWatch;

/**
 * Description:
 *
 * Date: 2021/5/8
 *
 * @author JiaDu
 * @version 1.0.0
 */
public class SsoFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(SsoFilter.class);

    private final String[] exclusions;

    private final String origin;

    private Boolean corsFlag;

    private String maxAge;

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        HttpServletRequest req = (HttpServletRequest) request;
        CORSEnable(req, (HttpServletResponse) response);
        if (HttpMethod.OPTIONS.matches(((HttpServletRequest) request).getMethod())) {
            logger.info("OPTIONS request");
            ((HttpServletResponse) response).setStatus(HttpStatus.OK.value());
            return;
        }
        if (isExclusion(req)) {
            chain.doFilter(request, response);
            return;
        }
        /*CurrentUser user = UserContext.getUser(req);
        if (null == user) {
            this.log.warn(" - the CurrentUser is null .");
            interceptReturn(401,
                "当前会话信息无效,账号信息为空",
                (HttpServletResponse) response);
            return;
        }
        this.log
            .info("用户调用基本信息->[name:{},ID:{}]", user.getName(), user.getId());*/
        chain.doFilter(request, response);
        stopWatch.stop();
        logger.info(":{}", stopWatch.prettyPrint());
    }

    public SsoFilter(String[] exclusions, String origin) {
        this.exclusions = exclusions;
        this.origin = origin;
    }

    private boolean isExclusion(HttpServletRequest request) {
        String url = request.getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();
        for (String exclusion : this.exclusions) {
            if (matcher.match(exclusion, url)) {
                logger.info(" The request URI exclusion ->>" + url);
                return true;
            }
        }
        logger.info(" The request URI inclusion ->>{},exclusions={}", url, this.exclusions);
        return false;
    }

    private void CORSEnable(HttpServletRequest req, HttpServletResponse resp) {
        if (null == this.corsFlag || !this.corsFlag) {
            return;
        }
        String method = req.getMethod();
        String origin = req.getHeader("Origin");
        String contentType = req.getContentType();
        logger.info("method={},origin={},contentType={}", method, origin, contentType);
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type,X-Auth-Token,AccessToken,token,sso-token");
        resp.addHeader("Access-Control-Expose-Headers", "*");
        resp.addHeader("Access-Control-Max-Age", this.maxAge);
        resp.addHeader("Access-Control-Allow-Origin", origin);
        if (method != null) {
            if (HttpMethod.OPTIONS.matches(method)) {
                method = req.getHeader("Access-Control-Request-Method");
                logger.info("method={}", method);
            }
            resp.addHeader("Access-Control-Allow-Methods", method);
        }
        resp.setContentType(contentType);
    }

    private void interceptReturn(int status, String message, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");
        Response<String> response = new Response<>(status, message);
        PrintWriter resp_write = resp.getWriter();
        resp_write.println(response);
        resp_write.close();
    }

    public Boolean getCorsFlag() {
        return this.corsFlag;
    }

    public void setCorsFlag(Boolean corsFlag) {
        this.corsFlag = corsFlag;
    }

    public String getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }
}
