package com.afis.cloud.auth.model.protocol.cust.request;

public class OnlineUserLoginRequest extends BaseOnlineUserRequest {
	
	private String redirectUrl;

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public String toString() {
		return "OnlineUserLoginRequest [redirectUrl=" + redirectUrl + "]";
	}
}
