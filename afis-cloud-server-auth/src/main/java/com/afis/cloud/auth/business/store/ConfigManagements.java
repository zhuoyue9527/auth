package com.afis.cloud.auth.business.store;

import java.sql.SQLException;
import java.util.Map;

import com.afis.cloud.auth.model.protocol.AdminConfigFrontRequest;
import com.afis.cloud.entities.model.auth.TableConfig;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;

public interface ConfigManagements {

	public static final String SQLMAP_NAMESPACE = ConfigManagements.class.getName();

	TableConfig getTableConfig(Long operateId, Long appId, String uid) throws SQLException;

	TableConfig insertOrUpdateTableConfig(AdminConfigFrontRequest configParam)
			throws SQLException, AuthenticationException, CommonException;

}
