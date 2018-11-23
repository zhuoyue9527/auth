package com.afis.cloud.auth.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.afis.cloud.exception.WebBusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.ApplicationManagements;
import com.afis.cloud.auth.business.store.impl.ApplicationManagementsImpl;
import com.afis.cloud.auth.model.protocol.AdminApplicationResponse;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.ApiOperation;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/8 10:15
 */
@RestController
@RequestMapping("/auth/admin/applications")
public class AdminApplicationController {

	private Logger logger = LoggerFactory.getLogger(getClass());


	@GetMapping("/{id}")
	@ApiOperation("应用列表")
	public WebResponse getApplicationsByUserId(@PathVariable Long id)
			throws CommonException, SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("【getApplicationsByUserId】 id:{}", id);
		}
		// 数据查询
		ApplicationManagements applicationService = new ApplicationManagementsImpl();
		List<Application> applicationByUserId = applicationService.getApplicationByUserId(id);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, applicationByUserId);

	}

	@GetMapping("/limit")
	@ApiOperation("应用列表")
	public WebResponse getApplications(@RequestParam(required = false) String appName,
									   @RequestParam(required = false) String status,
			@RequestParam(required = false) Integer limit) throws CommonException, SQLException {

		// 封装页面传递的参数
		Map<String, Object> map = new HashMap<String, Object>();
		if ( appName != null && !"".equals(appName)) {
			map.put("appName", appName);
		}
		if (limit != null && !"".equals(limit)) {
			map.put("limit", limit);
		}
		if ( status != null && !"".equals(status)) {
			map.put("status", status);
		}
		// 数据查询
		ApplicationManagements applicationService = new ApplicationManagementsImpl();
		List<AdminApplicationResponse> applicationByParam = applicationService.getApplicationByParam(map);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, applicationByParam);

	}

	@GetMapping
	@ApiOperation("应用列表分页")
	public WebResponse getApplications(
			@RequestParam(required = false) String appName,@RequestParam(required = false) String status,
			@RequestParam(required = false) String startDay,@RequestParam(required = false) String endDay,
			@RequestParam Integer start,@RequestParam Integer limit) throws CommonException, SQLException {

		// 非空校验分页信息
		checkPageParam(start, limit);
		// 封装页面传递的参数
		Map<String, Object> map = packParm(appName, status, startDay, endDay);
		// 数据查询
		ApplicationManagements applicationService = new ApplicationManagementsImpl();
		PageResult<AdminApplicationResponse> applications = applicationService.getApplications(map, start, limit);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, applications);

	}

	@GetMapping("/key")
	@ApiOperation("密钥")
	public WebResponse getApplicationsKey(){
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, SessionUtil.getRandomUUID());
	}

	private void checkPageParam(Integer start, Integer limit) throws CommonException {
		SessionUtil.getIntegerFromWebRequest(String.valueOf(start), "起始条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);
		SessionUtil.getIntegerFromWebRequest(String.valueOf(limit), "每页条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);

	}

	private Map<String, Object> packParm(String appName, String status, String startDay, String endDay)
			throws CommonException {
		Map<String, Object> map = new HashMap<String, Object>();
		appName = SessionUtil.validateString(appName, "应用名称", false);
		if (!"".equals(appName)) {
			map.put("appName", appName);
		}
		startDay = SessionUtil.validateString(startDay, "开始日期", false);
		if (!"".equals(startDay)) {
			map.put("startDay", startDay);
		}
		endDay = SessionUtil.validateString(endDay, "结束日期", false);
		if (!"".equals(endDay)) {
			map.put("endDay", endDay);
		}
		status = SessionUtil.validateString(status, "状态", false);
		if (!"".equals(status)) {
			map.put("status", status);
		}
		return map;
	}

}
