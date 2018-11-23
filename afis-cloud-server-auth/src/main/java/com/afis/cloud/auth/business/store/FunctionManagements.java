package com.afis.cloud.auth.business.store;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.afis.cloud.auth.model.FunctionListModel;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.Function;
import com.afis.cloud.exception.CommonException;

public interface FunctionManagements {

	public static final String SQLMAP_NAMESPACE = FunctionManagements.class.getName();

	PageResult<Function> getFunctions(Map<String, Object> map, int start, int limit)
			throws SQLException, CommonException;

	List<FunctionListModel> getFunctionsByGroups() throws SQLException;

	List<FunctionListModel> getFunctionsByAppId(Long appId) throws SQLException;

	List<Function> getAllFunction() throws SQLException;
}
