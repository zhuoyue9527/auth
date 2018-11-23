package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.afis.cloud.auth.business.store.LogManagements;
import com.afis.cloud.auth.model.kafka.LoginLogModel;
import com.afis.cloud.auth.model.kafka.OperateLogModel;
import com.afis.cloud.auth.model.protocol.AdminLoginLogResponse;
import com.afis.cloud.auth.model.protocol.AdminOperateLogResponse;
import com.afis.cloud.business.impl.BusinessCore;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.dao.auth.LoginLogDAO;
import com.afis.cloud.entities.dao.auth.OperateLogDAO;
import com.afis.cloud.entities.dao.impl.auth.LoginLogDAOImpl;
import com.afis.cloud.entities.dao.impl.auth.OperateLogDAOImpl;
import com.afis.cloud.entities.model.auth.LoginLog;
import com.afis.cloud.entities.model.auth.LoginLogSelective;
import com.afis.cloud.entities.model.auth.OperateLogSelective;
import com.afis.cloud.exception.CommonException;

public class LogManagementsImpl extends BusinessCore implements LogManagements {

	private LoginLogDAO loginLogDao = new LoginLogDAOImpl(sqlMapClient);

	private OperateLogDAO operateLogDAO = new OperateLogDAOImpl(sqlMapClient);

	@Override
	public void insertLoginLog(LoginLogModel log) throws SQLException {
		LoginLogSelective selective = new LoginLogSelective();
		selective.setBrowserInfo(log.getBrowserInfo());
		selective.setLoginApp(log.getLoginApp());
		selective.setLoginIp(log.getLoginIp());
		selective.setLoginTime(log.getLoginTime());
		selective.setRemark(log.getRemark());
		selective.setStatus(log.getStatus());
		selective.setSystemInfo(log.getSystemInfo());
		selective.setUserId(log.getUserId());
		loginLogDao.insert(selective);
	}

	@Override
	public void insertOperateLog(OperateLogModel log) throws SQLException {
		OperateLogSelective selective = new OperateLogSelective();
		selective.setFunctionId(log.getFunctionId());
		selective.setLoginIp(log.getLoginIp());
		selective.setStatus(log.getStatus());
		selective.setRemark(log.getRemark());
		selective.setOperateAppId(log.getOperateAppId());
		selective.setOperator(log.getOperator());
		selective.setOperateTime(new Date());
		operateLogDAO.insert(selective);
	}

	@Override
	public PageResult<AdminLoginLogResponse> getLoginLogs(Map<String, Object> map, int start, int limit)
			throws SQLException, CommonException {
		return this.page(LogManagements.SQLMAP_NAMESPACE + ".getLoginLogs", map, start, limit);
	}

	@Override
	public PageResult<AdminOperateLogResponse> getOperateLogs(Map<String, Object> map, int start, int limit)
			throws SQLException, CommonException {
		return this.page(LogManagements.SQLMAP_NAMESPACE + ".getOperateLogs", map, start, limit);
	}

	@Override
	public List<LoginLog> getSystemInfos() throws SQLException {
		return this.queryForList(LogManagements.SQLMAP_NAMESPACE + ".getSystemInfos");
	}

	@Override
	public List<LoginLog> getBrowsers() throws SQLException {
		return this.queryForList(LogManagements.SQLMAP_NAMESPACE + ".getBrowsers");
	}
}
