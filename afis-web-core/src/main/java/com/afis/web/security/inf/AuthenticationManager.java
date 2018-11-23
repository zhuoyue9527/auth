package com.afis.web.security.inf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.afis.web.exception.AuthenticationException;
import com.afis.web.modal.UserDetails;

/**
 * 安全验证管理器接口 Created by hsw on 2017/1/12.
 */
public interface AuthenticationManager {

	/**
	 * 安全认证。
	 * 
	 * @param request
	 * @param roles
	 * @return 返回认证成功的用户信息
	 * @throws AuthenticationException
	 */
	UserDetails authenticated(HttpServletRequest request, String cookieName) throws AuthenticationException;

	/**
	 * 获取当前在线会员的信息
	 * 
	 * @param request
	 * @return
	 */
	UserDetails onlineUser(HttpServletRequest request,String cookieName);

	/**
	 * 认证成功
	 * 
	 * @param response
	 * @param userDetails
	 */
	void authenticateSuccess(HttpServletRequest request, HttpServletResponse response, String cookieName,
			UserDetails userDetails);

	/**
	 * 登出成功
	 * 
	 * @param request
	 * @param response
	 * @param userDetails
	 */
	void logoutSuccess(HttpServletRequest request, HttpServletResponse response, String cookieName,
			UserDetails userDetails);

}
