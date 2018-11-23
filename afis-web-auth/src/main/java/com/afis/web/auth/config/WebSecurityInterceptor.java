package com.afis.web.auth.config;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.afis.web.annotation.AfisAuthentication;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.modal.UserDetails;
import com.afis.web.security.inf.AuthenticationHandler;
import com.afis.web.security.inf.AuthenticationManager;

/**
 * Web安全控制器 Created by hsw on 2017/1/12.
 */
public class WebSecurityInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(WebSecurityInterceptor.class);

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	private WebProperties webProperties;

	private AuthenticationManager authenticationManager;
	private AuthenticationHandler authenticationHandler;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("security interceptor for : [{}]", request.getRequestURI());
		}

		if (antPathMatcher.match("/error", request.getRequestURI())) {
			return true;
		}

		if (antPathMatcher.match(webProperties.getSecurity().getLoginPage(), request.getRequestURI())) {
			if (logger.isDebugEnabled()) {
				logger.debug("login page: {}, passed.", request.getRequestURI());
			}
			return true;
		} else {
			if (antPathMatcher.match(webProperties.getSecurity().getLogoutPage(), request.getRequestURI())) {
				UserDetails userDetails = authenticationManager.onlineUser(request,
						webProperties.getSecurity().getCookieName());
				request.setAttribute(UserDetails.class.getName(), userDetails);
			} else {
				try {
					UserDetails userDetails = authenticationManager.onlineUser(request,
							webProperties.getSecurity().getCookieName());

					request.setAttribute(UserDetails.class.getName(), userDetails);

					HandlerMethod handlerMethod = (HandlerMethod) handler;
					Method method = handlerMethod.getMethod();
					AfisAuthentication afisAuthentication = method.getAnnotation(AfisAuthentication.class);
					if (afisAuthentication != null && userDetails == null) {
						throw new AuthenticationException(AuthenticationException.NOT_LOGIN);
					}
				} catch (Exception e) {
					if (logger.isDebugEnabled()) {
						logger.debug("authorized failure for request: {}", request.getRequestURI(), e);
					}
					authenticationHandler.unAuthenticationHandler(request, response, e);
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public WebProperties getWebProperties() {
		return webProperties;
	}

	public void setWebProperties(WebProperties webProperties) {
		this.webProperties = webProperties;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationHandler getAuthenticationHandler() {
		return authenticationHandler;
	}

	public void setAuthenticationHandler(AuthenticationHandler authenticationHandler) {
		this.authenticationHandler = authenticationHandler;
	}
}
