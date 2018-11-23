package com.afis.web.auth.security;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.afis.web.exception.AuthenticationException;
import com.afis.web.modal.UserDetails;
import com.afis.web.security.inf.AuthenticationManager;
import com.afis.web.security.inf.TokenService;
import com.afis.web.utils.HttpOrignalUtil;

/**
 * 认证使用的安全过滤器
 * 
 * @author Chengen
 *
 */
public class AuthAuthenticationManager implements AuthenticationManager {

	private Logger logger = LoggerFactory.getLogger(AuthAuthenticationManager.class);

	private TokenService tokenService;

	private String logoutSuccessPage;

	@Override
	public UserDetails authenticated(HttpServletRequest request, String cookieName) throws AuthenticationException {
		Cookie[] cookies = request.getCookies();
		String loginKey = parseToken(cookieName, cookies);
		if (loginKey == null) {
			throw new AuthenticationException(AuthenticationException.NOT_LOGIN);
		}

		UserDetails userDetails = new UserDetails();
		userDetails.setLoginKey(loginKey);
		userDetails.setSourceIp(HttpOrignalUtil.getRemoteIpReal(request));
		userDetails.setBrowserInfo(HttpOrignalUtil.getBrowserInfo(request));
		userDetails.setSystemInfo(HttpOrignalUtil.getSystemInfo(request));
		UserDetails validateToken = tokenService.validateToken(userDetails);
		return validateToken;
	}

	@Override
	public UserDetails onlineUser(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		String loginKey = parseToken(cookieName, cookies);
		if (loginKey == null || loginKey.trim().length() == 0) {
			return null;
		}
		return tokenService.getToken(loginKey);
	}

	@Override
	public void authenticateSuccess(HttpServletRequest request, HttpServletResponse response, String cookieName,
			UserDetails userDetails) {
		String loginKey = userDetails.getLoginKey();
		Cookie cookie = generateCookie(cookieName, loginKey);
		response.addCookie(cookie);
		if (logger.isDebugEnabled()) {
			logger.debug("login success for : [{}] from:[{}] ", userDetails.getUserName(), userDetails.getSourceIp());
		}
	}

	@Override
	public void logoutSuccess(HttpServletRequest request, HttpServletResponse response, String cookieName,
			UserDetails userDetails) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		String redirect = request.getParameter("redirect");

		if (redirect != null) {
			try {
				response.sendRedirect(redirect);
				return;
			} catch (IOException e) {
				logger.debug("redirect to login out success page fail.");
			}
		}

		if (logoutSuccessPage != null) {
			try {
				response.sendRedirect(logoutSuccessPage);
			} catch (IOException e) {
				logger.debug("redirect to login out success page fail.");
			}
		}

	}

	private String parseToken(String cookieName, Cookie[] cookies) {
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(cookieName)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	private Cookie generateCookie(String cookieName, String loginKey) {
		Cookie cookie = new Cookie(cookieName, loginKey);
		cookie.setPath("/");
		return cookie;
	}

	public TokenService getTokenService() {
		return tokenService;
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public String getLogoutSuccessPage() {
		return logoutSuccessPage;
	}

	public void setLogoutSuccessPage(String logoutSuccessPage) {
		this.logoutSuccessPage = logoutSuccessPage;
	}
}
