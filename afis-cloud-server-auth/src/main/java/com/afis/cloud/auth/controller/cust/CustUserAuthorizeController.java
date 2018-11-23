package com.afis.cloud.auth.controller.cust;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.AppUserManagements;
import com.afis.cloud.auth.business.store.impl.AppUserManagementsImpl;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.protocol.cust.request.UserAuthorizeRequest;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;
import com.afis.web.modal.UserDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth/authorize/user")
@Api("应用界面提供的用户授权")
public class CustUserAuthorizeController extends AbstractOnlineUserController {

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@ApiOperation("用户授权")
	@PatchMapping
	public WebResponse patchAuthorize(HttpServletRequest request, @RequestBody UserAuthorizeRequest loginRequest)
			throws AuthenticationException, CommonException, SQLException {

		SessionUtil.addDebugLog(logger, "用户授权开始:{}", loginRequest);

		// 校验参数
		checkOnlineInput(loginRequest);

		ApplicationModel application = checkApplication(loginRequest.getAppCode().trim());

		UserDetails userDetails = getUserDetails(loginRequest.getLoginKey());

		AppUserManagements mangements = new AppUserManagementsImpl(kafkaTemplate, authCacheUtil);

		mangements.appUserWarrant(userDetails, application.getId(), loginRequest.getPassword(),
				SessionUtil.getFunctionUrl(request));

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, "用户授权成功，请重新登录", null);
	}
}
