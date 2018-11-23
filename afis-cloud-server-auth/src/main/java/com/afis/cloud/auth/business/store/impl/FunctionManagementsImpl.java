package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.afis.cloud.auth.business.store.FunctionManagements;
import com.afis.cloud.auth.model.FunctionListModel;
import com.afis.cloud.business.impl.BusinessCore;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.dao.auth.FunctionDAO;
import com.afis.cloud.entities.dao.impl.auth.FunctionDAOImpl;
import com.afis.cloud.entities.model.auth.Function;
import com.afis.cloud.entities.model.auth.FunctionExample;
import com.afis.cloud.exception.CommonException;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/8 10:28
 */
public class FunctionManagementsImpl extends BusinessCore implements FunctionManagements {

	private FunctionDAO functionDao = new FunctionDAOImpl(sqlMapClient);

	@Override
	public PageResult<Function> getFunctions(Map<String, Object> map, int start, int limit)
			throws SQLException, CommonException {
		return this.page(FunctionManagements.SQLMAP_NAMESPACE + ".getFunctions", map, start, limit);
	}

	@Override
	public List<FunctionListModel> getFunctionsByGroups() throws SQLException {
		return this.queryForList(FunctionManagements.SQLMAP_NAMESPACE + ".getFunctionsByGroups");
	}

	@Override
	public List<FunctionListModel> getFunctionsByAppId(Long appId) throws SQLException {
		return this.queryForList(FunctionManagements.SQLMAP_NAMESPACE + ".getFunctionsByAppId", appId);
	}

	@Override
	public List<Function> getAllFunction() throws SQLException {
		return functionDao.selectByExample(new FunctionExample());
	}
}
