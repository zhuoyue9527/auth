package com.afis.cloud.auth.model.protocol.cust.request;

public class UserAuthorizeRequest extends BaseOnlineUserRequest {
	private String password;// 应用密码

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
