package com.afis.cloud.auth.business.store;

import java.sql.SQLException;
import java.util.Map;

import com.afis.cloud.auth.model.protocol.AdminOnlineUserFrontRequest;
import com.afis.cloud.auth.model.protocol.AdminUserResponse;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.exception.CommonException;

public interface OnlineUserManagements {

	public static final String SQLMAP_NAMESPACE = OnlineUserManagements.class.getName();

	void deleteOnlineUserByLoginKey(String loginKey) throws SQLException;

	void deleteOnlineUserByUserId(long userId) throws SQLException;

	void insertOnlineUser(String loginKey, long userId) throws SQLException;

	PageResult<AdminUserResponse> getOnlineUsers(Map<String, Object> map, int start, int limit)
			throws SQLException, CommonException;

	void removeOnlineUser(String functionUrl, AdminOnlineUserFrontRequest param) throws SQLException, CommonException;

}
