package com.afis.cloud.auth.business.store;

import java.sql.SQLException;

import com.afis.cloud.auth.model.protocol.open.request.AbstractInterfaceRequest;
import com.afis.cloud.auth.model.protocol.open.request.UserAuthorizeRequest;
import com.afis.cloud.auth.model.protocol.open.response.AppUserResponse;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;

public interface UserAuthorizeManagements {

	public static final String SQLMAP_NAMESPACE = UserAuthorizeManagements.class.getName();

	void addUserWarrant(String functionUrl, Long userId, UserAuthorizeRequest userParam)
			throws SQLException, AuthenticationException, CommonException;

	void deleteAppUserWarrant(String functionUrl, Long userId, AbstractInterfaceRequest userParam)
			throws SQLException, AuthenticationException, CommonException;

	AppUserResponse getAppUserByUserIdAndAppCode(Long userId, String appCode)
			throws SQLException, AuthenticationException, CommonException;

}
