package com.afis.cloud.auth.model.protocol.open.request;

/**
 * 用户授权
 * 
 * @author Chengen
 * 
 *
 */
public class UserAuthorizeRequest extends AbstractInterfaceRequest {
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserAppAuthorizeRequest [password=" + password + "]";
	}
}
