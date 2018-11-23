package com.afis.cloud.auth.model.protocol.open.request;

/**
 * 刷新token的参数
 * 
 * @author Chengen
 *
 */
public class CustomerRefreshTokenRequest {
	private String token;// token
	private String appCode;// appCode

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
		return "CustomerRefreshTokenRequest [token=" + token + ", appCode=" + appCode + "]";
	}

}
