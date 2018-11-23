package com.afis.cloud.auth.business.store;

import java.sql.SQLException;
import java.util.Map;

import com.afis.cloud.auth.model.protocol.AdminApplicationRequest;
import com.afis.cloud.auth.model.protocol.AdminApplicationResponse;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.AppFunction;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;

public interface AppFunctionManagements {

	public static final String SQLMAP_NAMESPACE = AppFunctionManagements.class.getName();

	String postAddApplication(String functionUrl, AdminApplicationRequest userParam)
			throws SQLException, CommonException;

	String patchEditApplication(String functionUrl, AdminApplicationRequest userParam)
			throws SQLException, CommonException;

	Application patchCancelApplication(String functionUrl, AdminApplicationRequest userParam)
			throws SQLException, AuthenticationException, CommonException;

	Application patchRecoverApplication(String functionUrl, AdminApplicationRequest userParam)
			throws SQLException, CommonException;

	PageResult<AdminApplicationResponse> getApplications(Map<String, Object> map, int start, int limit)
			throws SQLException, CommonException;

	AdminApplicationResponse getApplicationById(Long id) throws SQLException;

	AppFunction getAppFunctionByUK(long functionId, long appId) throws SQLException;

}