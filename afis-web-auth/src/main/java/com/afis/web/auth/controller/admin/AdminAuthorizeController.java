package com.afis.web.auth.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.web.auth.protocol.admin.request.AdminLoginCust2WebRequest;
import com.afis.web.auth.protocol.admin.request.AdminLoginWeb2CloudRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.controller.AbstractController;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.UserDetails;
import com.afis.web.modal.WebResponse;
import com.afis.web.security.inf.AuthenticationManager;
import com.afis.web.utils.HttpOrignalUtil;
import com.afis.web.utils.OkHttpUtil;
import com.afis.web.utils.SessionUtil;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 管理端登录和登出使用
 * 
 * @author Chengen
 *
 */
@RestController
@Api("认证中心后台登录相关")
@RequestMapping("/auth/admin/authorize")
public class AdminAuthorizeController extends AbstractController {

	@Autowired
	private WebProperties webProperties;

	@Autowired
	private AuthenticationManager authenticationManager;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@PostMapping
	@ApiOperation("提交登录")
	public WebResponse postLogin(HttpServletRequest request, HttpServletResponse response,
			@RequestBody AdminLoginCust2WebRequest loginRequest) throws WebBusinessException {
		// 校验登录名、密码、验证码
		checkInput(request, loginRequest);
		// 提交给cloud中的认证
		UserDetails userDetails = this.sendCloudLogin(request, loginRequest);

		if (userDetails == null) {
			throw new AuthenticationException(AuthenticationException.USER_NOT_EXIST);
		}

		authenticationManager.authenticateSuccess(request, response, webProperties.getSecurity().getCookieName(),
				userDetails);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, userDetails);
	}

	private UserDetails sendCloudLogin(HttpServletRequest request, AdminLoginCust2WebRequest loginRequest)
			throws WebBusinessException {
		// 构建提交给cloud的对象
		AdminLoginWeb2CloudRequest cloudRequest = createCloudRequest(request, loginRequest);
		// 请求cloud的认证服务
		WebResponse response = OkHttpUtil.webPostToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), cloudRequest);
		logger.debug("response:{}", response);
		if (ExceptionResultCode.SUCCESS == response.getResponseCode()) {// 登录成功
			return JSONObject.parseObject(response.getData().toString(), UserDetails.class);
		} else {
			throw new AuthenticationException(response.getResponseCode(), response.getResponseDesc());
		}
	}

	private AdminLoginWeb2CloudRequest createCloudRequest(HttpServletRequest request,
			AdminLoginCust2WebRequest loginRequest) {
		AdminLoginWeb2CloudRequest cloudRequest = new AdminLoginWeb2CloudRequest(loginRequest);
		cloudRequest.setLoginIp(HttpOrignalUtil.getRemoteIpReal(request));
		cloudRequest.setBrowserInfo(HttpOrignalUtil.getBrowserInfo(request));
		cloudRequest.setSystemInfo(HttpOrignalUtil.getSystemInfo(request));
		cloudRequest.setAppId(webProperties.getSystem().getAppId());
		return cloudRequest;
	}

	private void checkInput(HttpServletRequest request, AdminLoginCust2WebRequest loginRequest)
			throws AuthenticationException {
		// check username parameter
		if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().length() == 0) {
			throw new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST, "请输入用户名");
		}
		// check parameter password.
		if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().length() == 0) {
			throw new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST, "请输入密码");
		}
		// check captcha.
		if (webProperties.getSecurity().isUseCaptcha()) {
			if (loginRequest.getCaptcha() == null || loginRequest.getCaptcha().trim().length() == 0) {
				throw new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST, "请输入验证码");
			}
			Object loginCaptcha = request.getSession().getAttribute("LOGIN_CAPTCHA");
			if (loginCaptcha == null || !loginRequest.getCaptcha().trim().equalsIgnoreCase(loginCaptcha.toString())) {
				throw new AuthenticationException(AuthenticationException.CAPTCHA_NOT_RIGHT);
			}
		}
	}

}
