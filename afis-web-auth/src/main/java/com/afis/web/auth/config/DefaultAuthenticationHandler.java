package com.afis.web.auth.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.ModelAndView;

import com.afis.web.config.WebProperties;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.security.inf.AuthenticationHandler;

/**
 * Created by hsw on 2017/1/13.
 */
public class DefaultAuthenticationHandler implements AuthenticationHandler {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private WebProperties webProperties;

	@Override
	public ModelAndView authenticationFailureHandler(HttpServletRequest request, HttpServletResponse response,
			Exception ex) {
		return null;
	}

	/**
	 * 登录失败处理。处理方式为：一般网页GET请求跳转只登录页面，其他求其页面返回401/403错误
	 * 
	 * @param request
	 * @param response
	 * @param exception
	 * @throws IOException
	 */
	@Override
	public void unAuthenticationHandler(HttpServletRequest request, HttpServletResponse response, Exception exception)
			throws IOException {
		HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
		String accept = request.getHeader("Accept");

		// 一般网页请求，协议为GET,接收内容为text/html / text/* / */*
		if (httpMethod == HttpMethod.GET && accept != null
				&& (accept.contains("text/html") || accept.contains("text/*") || accept.contains("*/*"))) {

			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {// ajax请求
				if (logger.isDebugEnabled()) {
					logger.debug("path [{}] not authorized, is ajax request ", request.getRequestURI());
				}
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("path [{}] not authorized, redirect to login page: [{}]", request.getRequestURI(),
							webProperties.getSecurity().getLoginPage());
				}
				response.sendRedirect(webProperties.getSecurity().getLoginPage());
			}
		} else {
			// 其他请求，根据错误原因返回401或403错误
			if (logger.isDebugEnabled()) {
				logger.debug("path [{}] not authorized , send 401/403 error", request.getRequestURI());
			}
			if (exception != null && exception instanceof AuthenticationException) {
				AuthenticationException ex = (AuthenticationException) exception;
				if (ex.getErrorCode() == AuthenticationException.NO_PERMISSION) {// 无权限，返回403错误
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				} else { // 返回401未登录错误
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
			} else {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
	}

	public WebProperties getWebProperties() {
		return webProperties;
	}

	public void setWebProperties(WebProperties webProperties) {
		this.webProperties = webProperties;
	}
}
