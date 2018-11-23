package com.afis.cloud.auth.controller;

import java.sql.SQLException;
import java.util.HashMap;
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
import com.afis.cloud.auth.business.store.AppFunctionManagements;
import com.afis.cloud.auth.business.store.impl.AppFunctionManagementsImpl;
import com.afis.cloud.auth.model.protocol.AdminApplicationRequest;
import com.afis.cloud.auth.model.protocol.AdminApplicationResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.db.PageResult;
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
 * @Date: 2018/11/7 10:58
 */
@RestController
@RequestMapping("/auth/admin/appFunctions")
public class AdminAppFunctionController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@GetMapping
	@ApiOperation("应用授权列表")
	public WebResponse getApplications(@RequestParam(required = false) Long appId,
			@RequestParam(required = false) String userName, @RequestParam(required = false) String startDay,
			@RequestParam(required = false) String endDay, @RequestParam(required = false) String status,
			@RequestParam Integer start, @RequestParam Integer limit)
			throws WebBusinessException, SQLException {
		// 非空校验分页信息
		checkPageParam(start, limit);
		// 封装页面传递的参数
		Map<String, Object> map = packParm(appId, userName, startDay, endDay, status);
		// 数据查询
		AppFunctionManagements appFunctionService = new AppFunctionManagementsImpl(kafkaTemplate, authCacheUtil);
		PageResult<AdminApplicationResponse> usersList = appFunctionService.getApplications(map, start, limit);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, usersList);
	}

	@ApiOperation(value = "应用权限查看", notes = "应用权限查看")
	@GetMapping("/{id}")
	public WebResponse getApplicationById(@PathVariable Long id)
			throws SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("【getApplicationById】 id:{}", id);
		}
		AppFunctionManagements appFunctionService = new AppFunctionManagementsImpl(kafkaTemplate, authCacheUtil);
		AdminApplicationResponse adminApplicationRequest = appFunctionService.getApplicationById(id);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, adminApplicationRequest);
	}

	@ApiOperation(value = "应用权限添加", notes = "应用权限添加")
	@PostMapping
	public WebResponse postAddApplication(HttpServletRequest request, @RequestBody AdminApplicationRequest userParam)
			throws SQLException, CommonException {
		if (logger.isDebugEnabled()) {
			logger.debug("【postAddApplication】 userParam:{}", userParam);
		}
		// 非空校验
		checkInput(userParam);
		AppFunctionManagements appFunctionService = new AppFunctionManagementsImpl(kafkaTemplate, authCacheUtil);
		String imageUrl = appFunctionService.postAddApplication(SessionUtil.getFunctionUrl(request),
				userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, imageUrl);
	}

	@ApiOperation(value = "应用权限编辑", notes = "应用权限编辑")
	@PatchMapping("/{id}")
	public WebResponse patchEditApplication(@PathVariable Long id, HttpServletRequest request,
			@RequestBody AdminApplicationRequest userParam)
			throws SQLException, CommonException {
		if (logger.isDebugEnabled()) {
			logger.debug("【patchEditApplication】 userParam:{}", userParam);
		}
		// 非空校验
		checkInput(userParam);
		userParam.setId(id);
		AppFunctionManagements appFunctionService = new AppFunctionManagementsImpl(kafkaTemplate, authCacheUtil);
		String imageUrl = appFunctionService.patchEditApplication(SessionUtil.getFunctionUrl(request),
				userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, imageUrl);
	}

	@ApiOperation(value = "应用权限注销", notes = "应用权限注销")
	@PatchMapping("/cancel/{id}")
	public WebResponse patchCancelApplication(@PathVariable Long id, HttpServletRequest request,
			@RequestBody AdminApplicationRequest userParam)
			throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【patchCancelApplication】 id:{}", id);
		}
		userParam.setId(id);
		AppFunctionManagements appFunctionService = new AppFunctionManagementsImpl(kafkaTemplate, authCacheUtil);
		Application adminApplicationRequest = appFunctionService
				.patchCancelApplication(SessionUtil.getFunctionUrl(request), userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, adminApplicationRequest);
	}

	@ApiOperation(value = "应用权限恢复", notes = "应用权限恢复")
	@PatchMapping("/recover/{id}")
	public WebResponse patchRecoverApplication(@PathVariable Long id, HttpServletRequest request,
			@RequestBody AdminApplicationRequest userParam) throws SQLException, CommonException {
		if (logger.isDebugEnabled()) {
			logger.debug("【patchRecoverApplication】 id:{}", id);
		}
		userParam.setId(id);
		AppFunctionManagements appFunctionService = new AppFunctionManagementsImpl(kafkaTemplate, authCacheUtil);
		Application adminApplicationRequest = appFunctionService
				.patchRecoverApplication(SessionUtil.getFunctionUrl(request), userParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, adminApplicationRequest);
	}

	private void checkInput(AdminApplicationRequest userParam) throws CommonException {
		SessionUtil.validateString(userParam.getAppName(), "应用名称", true);
		SessionUtil.validateString(userParam.getKey(), "密钥", true);
	}

	private void checkPageParam(Integer start, Integer limit) throws CommonException {

		SessionUtil.getIntegerFromWebRequest(String.valueOf(start), "起始条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);
		SessionUtil.getIntegerFromWebRequest(String.valueOf(limit), "每页条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);

	}

	private Map<String, Object> packParm(Long appId, String userName, String startDay, String endDay, String status)
			throws CommonException {
		Map<String, Object> map = new HashMap<String, Object>();

		if (appId != null) {
			map.put("appId", appId);
		}
		userName = SessionUtil.validateString(userName, "应用名称", false);
		if (!"".equals(userName)) {
			map.put("userName", userName);
		}
		startDay = SessionUtil.validateString(startDay, "开始日期", false);
		if (!"".equals(startDay)) {
			map.put("clientType", startDay);
		}
		endDay = SessionUtil.validateString(endDay, "结束日期", false);
		if (!"".equals(endDay)) {
			map.put("clientType", endDay);
		}
		status = SessionUtil.validateString(status, "状态", false);
		if (!"".equals(status)) {
			map.put("status", status);
		}
		return map;
	}
}
