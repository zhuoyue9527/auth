package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.afis.cloud.auth.business.store.ApplicationManagements;
import com.afis.cloud.auth.model.protocol.AdminApplicationResponse;
import com.afis.cloud.business.impl.BusinessCore;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.exception.CommonException;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/7 11:10
 */
public class ApplicationManagementsImpl extends BusinessCore implements ApplicationManagements {

	@Override
	public PageResult<AdminApplicationResponse> getApplications(Map<String, Object> map, int start, int limit)
			throws SQLException {
		PageResult<AdminApplicationResponse> list = this
				.page(ApplicationManagements.SQLMAP_NAMESPACE + ".getApplications", map, start, limit);
		return list;
	}

	@Override
	public List<AdminApplicationResponse> getApplicationByParam(Map<String, Object> map)
			throws SQLException, CommonException {
		return this.queryForList(ApplicationManagements.SQLMAP_NAMESPACE + ".getApplicationByParam", map);
	}

	@Override
	public List<Application> getApplicationByUserId(Long userId) throws SQLException, CommonException {
		return this.queryForList(ApplicationManagements.SQLMAP_NAMESPACE + ".getApplicationByUserId", userId);
	}

}
