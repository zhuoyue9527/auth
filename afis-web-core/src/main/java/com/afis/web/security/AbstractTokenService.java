package com.afis.web.security;

import com.afis.web.exception.AuthenticationException;
import com.afis.web.modal.UserDetails;
import com.afis.web.security.inf.TokenService;

import java.util.List;

/**
 * Token服务 Created by hsw on 2017/1/12.
 */
public class AbstractTokenService implements TokenService {

	@Override
	public void storeToken(UserDetails userDetails) {

	}

	@Override
	public UserDetails getToken(String userName) {
		return null;
	}

	@Override
	public void freshTokenTime(String userName) {

	}

	@Override
	public void removeToken(String userName) {

	}

	@Override
	public void removeTokens(List<Object> tokens) {

	}

	@Override
	public UserDetails validateToken(UserDetails userDetails) throws AuthenticationException {
		UserDetails token = getToken(userDetails.getLoginKey());
		if (token == null) {
			throw new AuthenticationException(AuthenticationException.TOKEN_EXPIRED, "token expired.");
		}

		// 刷新时间
		freshTokenTime(token.getLoginKey());
		return token;
	}
}
