package com.afis.cloud.auth.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.ConfigManagements;
import com.afis.cloud.auth.business.store.impl.ConfigManagementsImpl;
import com.afis.cloud.auth.model.protocol.AdminConfigFrontRequest;
import com.afis.cloud.entities.model.auth.TableConfig;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.ApiOperation;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/13 9:39
 */
@RestController
@RequestMapping("/auth/admin/UserTableConfig")
public class UserTableConfigController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	ConfigManagements configManagements = new ConfigManagementsImpl();

	@ApiOperation(value = "系统配置维护", notes = "系统配置维护")
	@PostMapping
	public WebResponse insertOrUpdateTableConfig(
			@RequestBody AdminConfigFrontRequest configParam)
			throws SQLException, CommonException, AuthenticationException {
		if (logger.isDebugEnabled()) {
			logger.debug("【insertOrUpdateTableConfig】 configParam:{}", configParam);
		}
		// 非空校验
		checkInput(configParam);

		TableConfig adminConfigResponse = configManagements.insertOrUpdateTableConfig(configParam);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, adminConfigResponse);
	}

	@ApiOperation(value = "系统配置查看", notes = "系统配置查看")
	@GetMapping
	public WebResponse getTableConfig( @RequestParam String uid,
			@RequestParam Long appId, @RequestParam Long operateId)
			throws SQLException {
		if (logger.isDebugEnabled()) {
			logger.debug("【getTableConfig】 objId,appId:{}{}", uid, appId);
		}

		TableConfig tableConfig = configManagements.getTableConfig(operateId, appId, uid);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, tableConfig);
	}

	private void checkInput(AdminConfigFrontRequest configParam) throws CommonException {
		SessionUtil.validateString(String.valueOf(configParam.getAppId()), "应用ID", true);
		SessionUtil.validateString(configParam.getColumns(), "Cloumns", true);
		SessionUtil.validateString(configParam.getUid(), "对象ID", true);
	}
}
