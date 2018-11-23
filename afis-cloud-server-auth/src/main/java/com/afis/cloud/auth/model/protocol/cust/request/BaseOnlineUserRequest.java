package com.afis.cloud.auth.model.protocol.cust.request;

/**
 * 选择在线用户的基本信息
 * 
 * @author Chengen
 *
 */
public class BaseOnlineUserRequest {
	private String loginKey;
	private String appCode;

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

	@Override
	public String toString() {
		return "BaseOnlineUserRequest [loginKey=" + loginKey + ", appCode=" + appCode + "]";
	}

}
