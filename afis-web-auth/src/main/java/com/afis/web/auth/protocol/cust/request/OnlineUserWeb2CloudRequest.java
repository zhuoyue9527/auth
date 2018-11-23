package com.afis.web.auth.protocol.cust.request;

import com.afis.web.model.DefaultCloudRequest;

public class OnlineUserWeb2CloudRequest extends DefaultCloudRequest {
	private String loginKey;
	private String appCode;
	private String redirectUrl;

	public OnlineUserWeb2CloudRequest(OnlineUserCust2WebRequest loginRequest) {
		this.loginKey = loginRequest.getLoginKey();
		this.appCode = loginRequest.getAppCode();
		this.redirectUrl = loginRequest.getRedirectUrl();
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

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public String toString() {
		return "OnlineUserWeb2CloudRequest [loginKey=" + loginKey + ", appCode=" + appCode + ", redirectUrl="
				+ redirectUrl + "]";
	}

}
