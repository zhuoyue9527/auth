package com.afis.cloud.auth.business.store;

import java.sql.SQLException;

import com.afis.cloud.auth.model.protocol.open.request.AbstractInterfaceRequest;
import com.afis.cloud.auth.model.protocol.open.request.UserRequest;
import com.afis.cloud.auth.model.protocol.open.response.UserResponse;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;

public interface OpenUserManagements {

	public static final String SQLMAP_NAMESPACE = OpenUserManagements.class.getName();

	User addUser(String functionUrl, UserRequest userRequest)
			throws SQLException, AuthenticationException, CommonException;

	User patchUser(String functionUrl, UserRequest userRequest, Long id)
			throws SQLException, CommonException, AuthenticationException;

	UserResponse getUser(String token, String appCode, Long id)
			throws SQLException, CommonException, AuthenticationException;

	User cancelUser(String functionUrl, Long id, AbstractInterfaceRequest userRequest)
			throws SQLException, CommonException, AuthenticationException;

	User frozenUser(String functionUrl, Long id, AbstractInterfaceRequest userRequest)
			throws SQLException, CommonException, AuthenticationException;

	User thawUser(String functionUrl, Long id, AbstractInterfaceRequest userRequest)
			throws SQLException, CommonException, AuthenticationException;

	User unlockUser(String functionUrl, Long id, int failNum, AbstractInterfaceRequest userRequest)
			throws SQLException, CommonException, AuthenticationException;
}
