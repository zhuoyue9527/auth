package com.afis.cloud.auth.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.LogManagements;
import com.afis.cloud.auth.business.store.impl.LogManagementsImpl;
import com.afis.cloud.auth.model.protocol.AdminLoginLogResponse;
import com.afis.cloud.auth.model.protocol.AdminOperateLogResponse;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.LoginLog;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.ApiOperation;

/**
 * @Description:日志管理
 * @Author: lizheng
 * @Date: 2018/11/8 10:15
 */
@RestController
@RequestMapping("/auth/admin/logs")
public class AdminLogController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	LogManagements logService = new LogManagementsImpl();

	@GetMapping("/loginLogs")
	@ApiOperation("登录日志列表")
	public WebResponse getLoginLogs(@RequestParam(required = false) String userName,@RequestParam(required = false) String userAccount,
			@RequestParam(required = false) Long loginAppName, @RequestParam(required = false) String status,
			@RequestParam(required = false) String systemInfo, @RequestParam(required = false) String browserInfo,
			@RequestParam(required = false) String loginTimeStart, @RequestParam(required = false) String loginTimeEnd,
			@RequestParam Integer start, @RequestParam Integer limit)
			throws CommonException, SQLException {
		// 非空校验分页信息
		checkPageParam(start, limit);
		// 封装页面传递的参数
		Map<String, Object> map = packLoginLogsParm(userAccount,userName, loginAppName, systemInfo, browserInfo, status,
				loginTimeStart, loginTimeEnd);
		// 数据查询
		PageResult<AdminLoginLogResponse> loginLogResponse = logService.getLoginLogs(map, start, limit);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, loginLogResponse);
	}

	@GetMapping("/operateLogs")
	@ApiOperation("操作日志列表")
	public WebResponse getOperateLogs(@RequestParam(required = false) String functionName,
			@RequestParam(required = false) String status, @RequestParam(required = false) Long operateAppId,
			@RequestParam(required = false) String operator, @RequestParam(required = false) String startDay,
			@RequestParam(required = false) String endDay, @RequestParam Integer start,
			@RequestParam Integer limit) throws CommonException, SQLException {
		// 非空校验分页信息
		checkPageParam(start, limit);
		// 封装页面传递的参数
		Map<String, Object> map = packOperateLogsParm(functionName, status, operateAppId, operator, startDay, endDay);
		// 数据查询
		PageResult<AdminOperateLogResponse> operateLogs = logService.getOperateLogs(map, start, limit);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, operateLogs);
	}

	@GetMapping("/systemInfos")
	@ApiOperation("操作系统列表")
	public WebResponse getOperateLogs() throws SQLException {
		// 数据查询
		List<LoginLog> systemInfos = logService.getSystemInfos();
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, systemInfos);
	}

	@GetMapping("/browserInfos")
	@ApiOperation("操作系统列表")
	public WebResponse getBrowsers() throws SQLException {
		// 数据查询
		List<LoginLog> systemInfos = logService.getBrowsers();
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, systemInfos);
	}

	private void checkPageParam(Integer start, Integer limit) throws CommonException {
		SessionUtil.getIntegerFromWebRequest(String.valueOf(start), "起始条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);
		SessionUtil.getIntegerFromWebRequest(String.valueOf(limit), "每页条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);
	}

	private Map<String, Object> packLoginLogsParm(String userAccount,String userName, Long loginAppId, String systemInfo,
			String browserInfo, String status, String startDay, String endDay) throws CommonException {
		Map<String, Object> map = new HashMap<String, Object>();

		if (loginAppId != null) {
			map.put("loginAppId", loginAppId);
		}
		userAccount = SessionUtil.validateString(userAccount, "用户账号", false);
		if (!"".equals(userAccount)) {
			map.put("userAccount", userAccount);
		}
		userName = SessionUtil.validateString(userName, "用户名称", false);
		if (!"".equals(userName)) {
			map.put("userName", userName);
		}
		systemInfo = SessionUtil.validateString(systemInfo, "操作系统", false);
		if (!"".equals(systemInfo)) {
			map.put("systemInfo", systemInfo);
		}
		browserInfo = SessionUtil.validateString(browserInfo, "浏览器", false);
		if (!"".equals(browserInfo)) {
			map.put("browserInfo", browserInfo);
		}
		status = SessionUtil.validateString(status, "状态", false);
		if (!"".equals(status)) {
			map.put("status", status);
		}
		startDay = SessionUtil.validateString(startDay, "开始日期", false);
		if (!"".equals(startDay)) {
			map.put("startDay", startDay);
		}
		endDay = SessionUtil.validateString(endDay, "结束日期", false);
		if (!"".equals(endDay)) {
			map.put("endDay", endDay);
		}

		return map;
	}

	private Map<String, Object> packOperateLogsParm(String functionName, String status, Long operateAppId, String operator,
			String startDay, String endDay) throws CommonException {
		Map<String, Object> map = new HashMap<String, Object>();

		if (functionName != null) {
			map.put("functionName", functionName);
		}
		if (operateAppId != null) {
			map.put("operateAppId", operateAppId);
		}
		if (operator != null) {
			map.put("operator", operator);
		}
		status = SessionUtil.validateString(status, "状态", false);
		if (!"".equals(status)) {
			map.put("status", status);
		}
		startDay = SessionUtil.validateString(startDay, "开始日期", false);
		if (!"".equals(startDay)) {
			map.put("startDay", startDay);
		}
		endDay = SessionUtil.validateString(endDay, "结束日期", false);
		if (!"".equals(endDay)) {
			map.put("endDay", endDay);
		}

		return map;
	}
}
