package com.afis.cloud.auth.controller.open;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.aspect.AfisAuthExtInterface;
import com.afis.cloud.auth.business.store.UserAuthorizeManagements;
import com.afis.cloud.auth.business.store.impl.UserAuthorizeManagementsImpl;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.protocol.open.request.AbstractInterfaceRequest;
import com.afis.cloud.auth.model.protocol.open.request.UserAuthorizeRequest;
import com.afis.cloud.auth.model.protocol.open.response.AppUserResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth/v1/appUsers")
@ApiOperation("用户授权")
public class UserAuthorizeController {

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@ApiOperation("用户授权查询")
	@GetMapping("/{userId}")
	@AfisAuthExtInterface
	public WebResponse getAppUser(@PathVariable Long userId, @RequestParam String appCode)
			throws CommonException, SQLException, AuthenticationException {
		UserAuthorizeManagements userAuthorizeManagements = new UserAuthorizeManagementsImpl(kafkaTemplate,authCacheUtil);
		AppUserResponse appUser = userAuthorizeManagements.getAppUserByUserIdAndAppCode(userId, appCode);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, "用户授权查询成功", appUser);
	}

	@PostMapping("/{userId}")
	@ApiOperation("用户授权添加")
	@AfisAuthExtInterface
	public WebResponse postAppUser(@PathVariable Long userId, HttpServletRequest request,
			@RequestBody UserAuthorizeRequest userParam) throws SQLException, CommonException, AuthenticationException {
		UserAuthorizeManagements service = new UserAuthorizeManagementsImpl(kafkaTemplate,authCacheUtil);
		service.addUserWarrant(SessionUtil.getFunctionUrl(request), userId, userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, null);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("用户授权删除")
	@AfisAuthExtInterface
	public WebResponse deleteAppUser(@PathVariable Long userId, HttpServletRequest request,
			@RequestBody AbstractInterfaceRequest userParam)
			throws SQLException, CommonException, AuthenticationException {
		UserAuthorizeManagements service = new UserAuthorizeManagementsImpl(kafkaTemplate,authCacheUtil);
		service.deleteAppUserWarrant(SessionUtil.getFunctionUrl(request), userId, userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, null);
	}
}
