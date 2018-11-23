package com.afis.cloud.auth.controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.LoginManagements;
import com.afis.cloud.auth.business.store.impl.LoginManagementsImpl;
import com.afis.cloud.auth.model.protocol.AdminLoginRequest;
import com.afis.cloud.auth.model.protocol.DefaultRequest;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;
import com.afis.web.modal.UserDetails;

import io.swagger.annotations.ApiOperation;

/**
 * 管理端的登录及登出
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/auth/admin/authorize")
public class AdminAuthorizeController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@Value("${login.fail.num}")
	private int failNum;

	@ApiOperation(value = "管理端的登录提交", notes = "管理端的登录提交")
	@PostMapping
	public WebResponse postAuthorize(@RequestBody AdminLoginRequest request)
			throws AuthenticationException, SQLException, CommonException {
		if (logger.isDebugEnabled()) {
			logger.debug("【AdminAuthorizeController】 postAuthorize:{}", request);
		}

		LoginManagements loginService = new LoginManagementsImpl(kafkaTemplate, authCacheUtil);
		UserDetails userDetails = loginService.adminLogin(request, failNum);
		userDetails.setLoginKey(SessionUtil.getRandomUUID());

		if (logger.isDebugEnabled()) {
			logger.debug("userDetails:{}", userDetails);
		}

		// 添加到缓存(因为管理端登录是排他的)
		authCacheUtil.addOnlineUserToRedis(userDetails, Boolean.TRUE);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, userDetails);
	}

	@ApiOperation(value = "管理端的登出", notes = "管理端的登出")
	@DeleteMapping
	public WebResponse deleteAuthorize(@RequestBody DefaultRequest request) throws SQLException {
		authCacheUtil.removeOnlineUserFromRedis(request.getUserDetails(), Boolean.TRUE);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, null);
	}
}
