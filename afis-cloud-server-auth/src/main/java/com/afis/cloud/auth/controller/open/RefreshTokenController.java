package com.afis.cloud.auth.controller.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.model.TokenModel;
import com.afis.cloud.auth.model.protocol.open.request.CustomerRefreshTokenRequest;
import com.afis.cloud.auth.model.protocol.open.response.CustomerTokenResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/auth/v1/refresh_token")
@Api("刷新token的时间")
public class RefreshTokenController {

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@PostMapping
	public WebResponse refreshToken(@RequestBody CustomerRefreshTokenRequest request) throws AuthenticationException {

		// 获取token
		TokenModel token = authCacheUtil.getToken(request.getToken(), request.getAppCode());

		// 更新token的
		token.setLastUpdateTime(System.currentTimeMillis());

		// 将token再次缓存起来
		authCacheUtil.addTokenToRedis(token);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, new CustomerTokenResponse(token));
	}
}
