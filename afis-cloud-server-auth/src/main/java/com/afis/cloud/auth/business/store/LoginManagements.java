package com.afis.cloud.auth.business.store;

import java.sql.SQLException;

import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.protocol.AdminLoginRequest;
import com.afis.cloud.auth.model.protocol.cust.request.CustomerLoginRequest;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.web.modal.UserDetails;

public interface LoginManagements {
	
	public static final String SQLMAP_NAMESPACE = LoginManagements.class.getName();
	
	public UserDetails adminLogin(AdminLoginRequest request, int failNum)
			throws SQLException, AuthenticationException, CommonException;

	public UserDetails customerLogin(CustomerLoginRequest loginRequest, ApplicationModel application, int failNum)
			throws SQLException, AuthenticationException, CommonException;
}
