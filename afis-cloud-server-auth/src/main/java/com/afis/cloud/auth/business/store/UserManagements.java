package com.afis.cloud.auth.business.store;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.afis.cloud.auth.model.protocol.AdminUserFrontRequest;
import com.afis.cloud.auth.model.protocol.AdminUserResponse;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;

public interface UserManagements {

	public static final String SQLMAP_NAMESPACE = UserManagements.class.getName();

	PageResult<AdminUserResponse> getUsers(String functionUrl, Map<String, Object> map, int start, int limit)
			throws SQLException, CommonException;

	User addUser(String functionUrl, AdminUserFrontRequest user)
			throws SQLException, AuthenticationException, CommonException;

	User editUser(String functionUrl, AdminUserFrontRequest user)
			throws SQLException, AuthenticationException, CommonException;

	AdminUserResponse getUserById(String functionUrl, Long id) throws SQLException, CommonException;

	User frozenUser(String functionUrl, long id,AdminUserFrontRequest userParam) throws SQLException, CommonException;

	User thawUser(String functionUrl, long id,AdminUserFrontRequest userParam) throws SQLException, CommonException;

	User cancelUser(String functionUrl, long id,AdminUserFrontRequest userParam) throws SQLException, CommonException;

	User unlockUser(String functionUrl, long id, int failNum,AdminUserFrontRequest userParam) throws SQLException, CommonException;

	List<User> getUsersByUserName(String userAccount) throws SQLException, CommonException;
}
