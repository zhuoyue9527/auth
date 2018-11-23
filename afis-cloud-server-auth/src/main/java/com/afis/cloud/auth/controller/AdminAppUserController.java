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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.AppUserManagements;
import com.afis.cloud.auth.business.store.impl.AppUserManagementsImpl;
import com.afis.cloud.auth.model.protocol.AdminAppUserResponse;
import com.afis.cloud.auth.model.protocol.AdminUserAppFrontRequest;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.AppUser;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.exception.WebBusinessException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.ApiOperation;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/1 16:22
 */
@RestController
@RequestMapping("/auth/admin/appUsers")
public class AdminAppUserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@GetMapping
	@ApiOperation("用户授权列表")
	public WebResponse getAppUsers(@RequestParam(required = false) String userAccount,
			@RequestParam(required = false) String userName, @RequestParam(required = false) String userAppId,
			@RequestParam(required = false) String warrant, @RequestParam Integer start, @RequestParam Integer limit)
			throws WebBusinessException, SQLException {
		// 非空校验分页信息
		checkPageParam(start, limit);
		// 封装页面传递的参数
		Map<String, Object> map = packParm(userAccount, userName, userAppId, warrant);

		AppUserManagements service = new AppUserManagementsImpl(kafkaTemplate, authCacheUtil);

		PageResult<AdminAppUserResponse> list = service.getAppUserWarrant(map, start, limit);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, list);
	}

	@GetMapping("/userApps/{userId}")
	@ApiOperation("用户应用列表")
	public WebResponse getUserApps(@PathVariable Long userId) throws WebBusinessException, SQLException {
		// 校验userId
		SessionUtil.getIntegerFromWebRequest(String.valueOf(userId), "用户Id", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);

		AppUserManagements service = new AppUserManagementsImpl(kafkaTemplate, authCacheUtil);
		List<Application> list = service.getUserApps(userId);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, list);
	}

	@ApiOperation(value = "管理端的用户授权添加", notes = "管理端的用户授权添加")
	@PatchMapping("/add/{userId}")
	public WebResponse patchAddAppUser(@PathVariable Long userId, HttpServletRequest request,
			@RequestBody AdminUserAppFrontRequest userParam)
			throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【patchAddAppUser】 patchAddAppUser:{}", userParam);
		}
		AppUserManagements service = new AppUserManagementsImpl(kafkaTemplate, authCacheUtil);
		service.appUserWarrant(userId, SessionUtil.getFunctionUrl(request), userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, null);
	}

	@ApiOperation(value = "用户授权编辑", notes = "用户授权编辑")
	@PatchMapping("/edit/{id}")
	public WebResponse editAppWarrantUserById(@PathVariable Long id, @RequestBody AdminUserAppFrontRequest userParam,
			HttpServletRequest request) throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【editAppWarrantUserById】 id:{}", id);
		}

		AppUserManagements service = new AppUserManagementsImpl(kafkaTemplate, authCacheUtil);
		AppUser appUser = service.editAppWarrantUserById(SessionUtil.getFunctionUrl(request), id, userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, appUser);
	}

	private void checkPageParam(Integer start, Integer limit) throws CommonException {

		SessionUtil.getIntegerFromWebRequest(String.valueOf(start), "起始条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);
		SessionUtil.getIntegerFromWebRequest(String.valueOf(limit), "每页条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);

	}

	private Map<String, Object> packParm(String userAccount, String userName, String appId, String warrant)
			throws CommonException {
		Map<String, Object> map = new HashMap<String, Object>();

		userAccount = SessionUtil.validateString(userAccount, "用户账号", false);
		if (!"".equals(userAccount)) {
			map.put("userAccount", userAccount);
		}
		userName = SessionUtil.validateString(userName, "用户名称", false);
		if (!"".equals(userName)) {
			map.put("userName", userName);
		}
		if (appId != null) {
			map.put("appId", appId);
		}
		warrant = SessionUtil.validateString(warrant, "是否授权", false);
		if (!"".equals(warrant)) {
			map.put("warrant", warrant);
		}
		return map;
	}

}
