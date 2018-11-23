package com.afis.cloud.auth.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.afis.cloud.auth.business.store.UserManagements;
import com.afis.cloud.auth.business.store.impl.UserManagementsImpl;
import com.afis.cloud.auth.config.AuthCloudConfig;
import com.afis.cloud.auth.model.protocol.AdminUserFrontRequest;
import com.afis.cloud.auth.model.protocol.AdminUserResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.exception.WebBusinessException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/1 16:22
 */
@RestController
@RequestMapping("/auth/admin/users")
public class AdminUserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Autowired
	private AuthCloudConfig authCloudConfig;

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@GetMapping
	@ApiOperation("用户列表")
	public WebResponse getUsers(HttpServletRequest request, @RequestParam(required = false) String userAccount,
			@RequestParam(required = false) String userName, @RequestParam(required = false) String clientType,
			@RequestParam(required = false) String status, @RequestParam(required = false) String registerAPP,
			@RequestParam(required = false) String registerTimeStart,
			@RequestParam(required = false) String registerTimeEnd, @RequestParam Integer start,
			@RequestParam Integer limit) throws WebBusinessException, SQLException {
		// 非空校验分页信息
		checkPageParam(start, limit);
		// 封装页面传递的参数
		Map<String, Object> map = packParm(userAccount, userName, clientType, status, registerAPP, registerTimeStart,
				registerTimeEnd);

		UserManagements userService = new UserManagementsImpl(kafkaTemplate, authCacheUtil);

		PageResult<AdminUserResponse> usersList = userService.getUsers(SessionUtil.getFunctionUrl(request), map, start,
				limit);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, usersList);
	}

	@GetMapping("/vague")
	@ApiOperation("用户列表")
	public WebResponse getUsersByUserName(@RequestParam(required = false) String userName)
			throws WebBusinessException, SQLException {

		UserManagements userService = new UserManagementsImpl(kafkaTemplate, authCacheUtil);

		List<User> usersByUserName = userService.getUsersByUserName(userName);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, usersByUserName);
	}

	@ApiOperation(value = "管理端的用户注册提交", notes = "管理端的用户注册提交")
	@PostMapping
	public WebResponse postAddUser(HttpServletRequest request, @RequestBody AdminUserFrontRequest userParam)
			throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【postAddUser】 postAddUser:{}", userParam);
		}
		// 非空校验
		checkInput(userParam);

		UserManagements userService = new UserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userService.addUser(SessionUtil.getFunctionUrl(request), userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, user);
	}

	@ApiOperation(value = "管理端的用户编辑", notes = "管理端的用户编辑")
	@PatchMapping("/{id}")
	public WebResponse editUser(@PathVariable Long id, @RequestBody AdminUserFrontRequest userParam,
			HttpServletRequest request) throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【editUser】 editUser:{}", userParam);
		}
		userParam.setId(id);
		UserManagements userService = new UserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userService.editUser(SessionUtil.getFunctionUrl(request), userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, user);
	}

	@ApiOperation(value = "用户查看", notes = "用户查看")
	@GetMapping("/{id}")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "ID", dataType = "int", required = true) })
	public WebResponse getUserById(@PathVariable Long id, HttpServletRequest request)
			throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【getUserById】 id:{}", id);
		}
		// check id parameter
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不存在");
			exception.setDesc("id");
			throw exception;
		}

		UserManagements userService = new UserManagementsImpl(kafkaTemplate, authCacheUtil);
		AdminUserResponse user = userService.getUserById(SessionUtil.getFunctionUrl(request), id);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, user);
	}

	@ApiOperation(value = "用户冻结", notes = "用户冻结")
	@PatchMapping("/frozen/{id}")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", dataType = "String") })
	public WebResponse frozenUser(@PathVariable Long id, HttpServletRequest request,
			@RequestBody AdminUserFrontRequest userParam)
			throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【frozenUser】 id:{}", id);
		}
		// check id parameter
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不存在");
			exception.setDesc("id");
			throw exception;
		}

		UserManagements userService = new UserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userService.frozenUser(SessionUtil.getFunctionUrl(request), id, userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, user);
	}

	@ApiOperation(value = "用户解冻", notes = "用户解冻")
	@PatchMapping("/thaw/{id}")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", dataType = "String") })
	public WebResponse thawUser(@PathVariable Long id, HttpServletRequest request,
			@RequestBody AdminUserFrontRequest userParam)
			throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【thawUser】 id:{}", id);
		}
		// check id parameter
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不存在");
			exception.setDesc("id");
			throw exception;
		}

		UserManagements userService = new UserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userService.thawUser(SessionUtil.getFunctionUrl(request), id, userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, user);
	}

	@ApiOperation(value = "用户注销", notes = "用户注销")
	@PatchMapping("/cancel/{id}")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", dataType = "String") })
	public WebResponse cancelUser(@PathVariable Long id, HttpServletRequest request,
			@RequestBody AdminUserFrontRequest userParam)
			throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【cancelUser】 id:{}", id);
		}
		// check id parameter
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不存在");
			exception.setDesc("id");
			throw exception;
		}

		UserManagements userService = new UserManagementsImpl(kafkaTemplate, authCacheUtil);
		User user = userService.cancelUser(SessionUtil.getFunctionUrl(request), id, userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, user);
	}

	@ApiOperation(value = "用户解锁", notes = "用户解锁")
	@PatchMapping("/unlock/{id}")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", dataType = "String") })
	public WebResponse unlockUser(@PathVariable Long id, HttpServletRequest request,
			@RequestBody AdminUserFrontRequest userParam)
			throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【unlockUser】 id:{}", id);
		}
		// check id parameter
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不存在");
			exception.setDesc("id");
			throw exception;
		}

		UserManagements userService = new UserManagementsImpl(kafkaTemplate, authCacheUtil);
		int failNum = authCloudConfig.getFailNum();
		User user = userService.unlockUser(SessionUtil.getFunctionUrl(request), id, failNum, userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, user);
	}

	private void checkInput(AdminUserFrontRequest userParam) throws CommonException {

		SessionUtil.validateString(userParam.getUserAccount(), "用户账号", true);
		SessionUtil.validateString(userParam.getUserName(), "用户名称", true);
		SessionUtil.validateString(userParam.getClientType(), "用户类型", true);
		SessionUtil.validateString(userParam.getPassword(), "密码", true);
		SessionUtil.validateString(userParam.getMobile(), "联系手机", false);
		SessionUtil.getIntegerFromWebRequest(String.valueOf(userParam.getRegisterAppId()), "注册应用", null, Boolean.TRUE,
				Boolean.FALSE, Boolean.FALSE, null);
		SessionUtil.validateString(userParam.getRemark(), "备注", false);

	}

	private void checkPageParam(Integer start, Integer limit) throws CommonException {

		SessionUtil.getIntegerFromWebRequest(String.valueOf(start), "起始条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);
		SessionUtil.getIntegerFromWebRequest(String.valueOf(limit), "每页条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);

	}

	private Map<String, Object> packParm(String userAccount, String userName, String clientType, String status,
			String appId, String startDay, String endDay) throws CommonException {
		Map<String, Object> map = new HashMap<String, Object>();

		userAccount = SessionUtil.validateString(userAccount, "用户账号", false);
		if (!"".equals(userAccount)) {
			map.put("userAccount", userAccount);
		}
		userName = SessionUtil.validateString(userName, "用户名称", false);
		if (!"".equals(userName)) {
			map.put("userName", userName);
		}
		clientType = SessionUtil.validateString(clientType, "类型", false);
		if (!"".equals(clientType)) {
			map.put("clientType", clientType);
		}
		status = SessionUtil.validateString(status, "状态", false);
		if (!"".equals(status)) {
			map.put("status", status);
		}
		if (appId != null) {
			map.put("appId", appId);
		}
		startDay = SessionUtil.validateString(startDay, "操作开始时间", false);
		if (!"".equals(startDay)) {
			map.put("startDay", startDay);
		}
		endDay = SessionUtil.validateString(endDay, "操作结束时间", false);
		if (!"".equals(endDay)) {
			map.put("endDay", endDay);
		}
		return map;
	}

}
