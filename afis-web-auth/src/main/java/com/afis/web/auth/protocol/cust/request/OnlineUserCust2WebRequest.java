package com.afis.web.auth.protocol.cust.request;

public class OnlineUserCust2WebRequest {
	private String loginKey;
	private String appCode;
	private String redirectUrl;

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

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public String toString() {
		return "OnlineUserCust2WebRequest [loginKey=" + loginKey + ", appCode=" + appCode + ", redirectUrl="
				+ redirectUrl + "]";
	}

}
