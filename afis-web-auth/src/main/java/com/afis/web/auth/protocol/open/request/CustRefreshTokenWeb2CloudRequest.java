package com.afis.web.auth.protocol.open.request;

import com.afis.web.model.DefaultCloudRequest;

/**
 * web-->cloud
 * 
 * @author Chengen
 *
 */
public class CustRefreshTokenWeb2CloudRequest extends DefaultCloudRequest {

	private String token;

	private String appCode;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	@Override
	public String toString() {
		return "CustRefreshTokenWeb2CloudRequest [token=" + token + ", appCode=" + appCode + "]";
	}
}
