package com.afis.web.auth.controller.cust;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afis.web.auth.protocol.cust.request.UserAuthorizeCust2WebRequest;
import com.afis.web.auth.protocol.cust.request.UserAuthorizeWeb2CloudRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.OkHttpUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth/authorize/user")
public class UserAuthorize {
	@Autowired
	private WebProperties webProperties;

	@PatchMapping
	@ApiOperation("用户授权")
	public WebResponse patchAuthorize(HttpServletRequest request,@RequestBody UserAuthorizeCust2WebRequest webRequest)
			throws WebBusinessException {
		// 参数校验
		checkInput(webRequest);
		// 请求cloud
		return OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new UserAuthorizeWeb2CloudRequest(webRequest));
	}

	private void checkInput(UserAuthorizeCust2WebRequest request) throws AuthenticationException {
		if (request.getAppCode() == null || request.getAppCode().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"appCode not exits");
			exception.setDesc("appCode");
			throw exception;
		}
		if (request.getLoginKey() == null || request.getLoginKey().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"loginKey not exits");
			exception.setDesc("loginKey");
			throw exception;
		}
	}
}
