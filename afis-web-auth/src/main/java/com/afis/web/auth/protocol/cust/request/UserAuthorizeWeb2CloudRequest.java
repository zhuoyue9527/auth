package com.afis.web.auth.protocol.cust.request;

import com.afis.web.model.DefaultCloudRequest;

public class UserAuthorizeWeb2CloudRequest extends DefaultCloudRequest {
	private String loginKey;
	private String appCode;
	private String password;

	public UserAuthorizeWeb2CloudRequest(UserAuthorizeCust2WebRequest webRequest) {
		this.loginKey = webRequest.getLoginKey();
		this.appCode = webRequest.getAppCode();
		this.password = webRequest.getPassword();
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserAuthorizeWeb2CloudRequest [loginKey=" + loginKey + ", appCode=" + appCode + ", password=" + password
				+ "]";
	}
}
