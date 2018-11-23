package com.afis.cloud.auth.controller.open;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.aspect.AfisAuthExtInterface;
import com.afis.cloud.auth.business.store.OpenUserManagements;
import com.afis.cloud.auth.business.store.impl.OpenUserManagementsImpl;
import com.afis.cloud.auth.config.AuthCloudConfig;
import com.afis.cloud.auth.model.protocol.open.request.AbstractInterfaceRequest;
import com.afis.cloud.auth.model.protocol.open.request.UserRequest;
import com.afis.cloud.auth.model.protocol.open.response.UserResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth/v1/users")
@ApiOperation("用户接口-用户维护")
public class UserController {
	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@Autowired
	private AuthCloudConfig authCloudConfig;

	@PostMapping
	@ApiOperation("用户添加")
	@AfisAuthExtInterface
	public WebResponse postUser(HttpServletRequest request, @RequestBody UserRequest userRequest)
			throws AuthenticationException, CommonException, SQLException {
		OpenUserManagements userManagements = new OpenUserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userManagements.addUser(SessionUtil.getFunctionUrl(request), userRequest);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, "用户添加成功", user);
	}

	@PatchMapping("/{id}")
	@ApiOperation("用户修改")
	@AfisAuthExtInterface
	public WebResponse patchUser(HttpServletRequest request, @RequestBody UserRequest userRequest,
			@PathVariable Long id) throws SQLException, CommonException, AuthenticationException {
		OpenUserManagements userManagements = new OpenUserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userManagements.patchUser(SessionUtil.getFunctionUrl(request), userRequest, id);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, "用户修改成功", user);
	}

	@GetMapping("/{id}")
	@AfisAuthExtInterface
	@ApiOperation("用户查询")
	public WebResponse getUser(HttpServletRequest request, @PathVariable Long id, @RequestParam String token,
			@RequestParam String appCode) throws SQLException, CommonException, AuthenticationException {
		OpenUserManagements userManagements = new OpenUserManagementsImpl(kafkaTemplate, authCacheUtil);
		UserResponse user = userManagements.getUser(token, appCode, id);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, "用户查询成功", user);
	}

	@PatchMapping("/cancel/{id}")
	@AfisAuthExtInterface
	@ApiOperation("用户注销")
	public WebResponse cancelUser(HttpServletRequest request, @RequestBody AbstractInterfaceRequest userRequest,
			@PathVariable Long id) throws SQLException, CommonException, AuthenticationException {
		OpenUserManagements userManagements = new OpenUserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userManagements.cancelUser(SessionUtil.getFunctionUrl(request), id, userRequest);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, "用户注销成功", user);
	}

	@PatchMapping("/frozen/{id}")
	@AfisAuthExtInterface
	@ApiOperation("用户冻结")
	public WebResponse frozenUser(HttpServletRequest request, @RequestBody AbstractInterfaceRequest userRequest,
			@PathVariable Long id) throws SQLException, CommonException, AuthenticationException {
		OpenUserManagements userManagements = new OpenUserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userManagements.frozenUser(SessionUtil.getFunctionUrl(request), id, userRequest);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, "用户冻结成功", user);
	}

	@PatchMapping("/thaw/{id}")
	@AfisAuthExtInterface
	@ApiOperation("用户解冻")
	public WebResponse thawUser(HttpServletRequest request, @RequestBody AbstractInterfaceRequest userRequest,
			@PathVariable Long id) throws SQLException, CommonException, AuthenticationException {
		OpenUserManagements userManagements = new OpenUserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userManagements.thawUser(SessionUtil.getFunctionUrl(request), id, userRequest);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, "用户解冻成功", user);
	}

	@PatchMapping("/unlock/{id}")
	@AfisAuthExtInterface
	@ApiOperation("用户解锁")
	public WebResponse unlockUser(HttpServletRequest request, @RequestBody AbstractInterfaceRequest userRequest,
			@PathVariable Long id) throws SQLException, CommonException, AuthenticationException {
		OpenUserManagements userManagements = new OpenUserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userManagements.unlockUser(SessionUtil.getFunctionUrl(request), id, authCloudConfig.getFailNum(),
				userRequest);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, "用户解锁成功", user);
	}
}
