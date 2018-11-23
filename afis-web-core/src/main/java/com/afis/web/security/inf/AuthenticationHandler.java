package com.afis.web.security.inf;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录处理
 * Created by hsw on 2017/1/12.
 */
public interface AuthenticationHandler {

    /**
     * 验证失败处理
     * @param request
     * @param response
     * @param ex
     * @return
     */
    ModelAndView authenticationFailureHandler(HttpServletRequest request, HttpServletResponse response, Exception ex);

    /**
     * 未登录处理
     * @param request
     * @param exception
     * @throws IOException
     */
    void unAuthenticationHandler(HttpServletRequest request, HttpServletResponse response, Exception exception)throws IOException;
}
