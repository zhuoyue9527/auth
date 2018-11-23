package com.afis.web.auth.protocol.open.request;

import com.afis.web.model.DefaultCloudRequest;

public class UserAuthorizeWeb2CloudRequest extends DefaultCloudRequest {
	private String password;
	private String appCode;
	private String token;
	
	public UserAuthorizeWeb2CloudRequest(UserAuthorizeCust2WebRequest request) {
		this.password = request.getPassword();
		this.appCode = request.getAppCode();
		this.token = request.getToken();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
