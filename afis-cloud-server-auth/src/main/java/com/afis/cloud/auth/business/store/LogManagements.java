package com.afis.cloud.auth.business.store;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.afis.cloud.auth.model.kafka.LoginLogModel;
import com.afis.cloud.auth.model.kafka.OperateLogModel;
import com.afis.cloud.auth.model.protocol.AdminLoginLogResponse;
import com.afis.cloud.auth.model.protocol.AdminOperateLogResponse;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.LoginLog;
import com.afis.cloud.exception.CommonException;

public interface LogManagements {

	public static final String SQLMAP_NAMESPACE = LogManagements.class.getName();

	public void insertLoginLog(LoginLogModel log) throws SQLException;

	public void insertOperateLog(OperateLogModel log) throws SQLException;

	PageResult<AdminLoginLogResponse> getLoginLogs(Map<String, Object> map , int start, int limit) throws SQLException, CommonException;

	PageResult<AdminOperateLogResponse> getOperateLogs(Map<String, Object> map , int start, int limit) throws SQLException, CommonException;

	List<LoginLog> getSystemInfos() throws SQLException;

	List<LoginLog> getBrowsers() throws SQLException;
}
