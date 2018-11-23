package com.afis.web.auth.protocol.cust.request;

public class UserAuthorizeCust2WebRequest {
	private String loginKey;
	private String appCode;
	private String password;

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
		return "UserAuthorizeCust2WebRequest [loginKey=" + loginKey + ", appCode=" + appCode + ", password=" + password
				+ "]";
	}

}
