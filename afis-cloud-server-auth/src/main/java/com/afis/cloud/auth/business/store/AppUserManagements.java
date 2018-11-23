package com.afis.cloud.auth.business.store;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.afis.cloud.auth.model.protocol.AdminAppUserResponse;
import com.afis.cloud.auth.model.protocol.AdminUserAppFrontRequest;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.AppUser;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.web.modal.UserDetails;

public interface AppUserManagements{

	public static final String SQLMAP_NAMESPACE = AppUserManagements.class.getName();

	PageResult<AdminAppUserResponse> getAppUserPermissons(Map<String, Object> map, int start, int limit)
			throws SQLException;

	void addAppUserPermissons(String functionUrl, AdminUserAppFrontRequest user)
			throws SQLException, AuthenticationException, CommonException;

	void deleteAppUserPermissons(String functionUrl, Long id, AdminUserAppFrontRequest userParam)
			throws SQLException, AuthenticationException, CommonException;

	void appUserWarrant(Long userId, String functionUrl, AdminUserAppFrontRequest user)
			throws SQLException, AuthenticationException, CommonException;

	AppUser editAppWarrantUserById(String functionUrl, Long id, AdminUserAppFrontRequest user)
			throws SQLException, AuthenticationException, CommonException;

	List<Application> getUserApps(Long userId) throws SQLException;

	/**
	 * 应用界面提供的用户授权
	 * 
	 * @param userId
	 * @param applicationId
	 * @param request
	 */
	void appUserWarrant(UserDetails userDetails, long applicationId, String password, String functionUrl)
			throws SQLException, AuthenticationException, CommonException;

	PageResult<AdminAppUserResponse> getAppUserWarrant(Map<String, Object> map, int start, int limit)
			throws SQLException;
}
