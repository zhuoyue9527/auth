package com.afis.web.auth.protocol.open.request;

/**
 * 应用接口默认传递参数
 * 
 * 应用接口-用户维护，用户授权
 * 
 * @author Chengen
 *
 */
public class AbstractInterfaceRequest {
	private String appCode;
	private String token;

	public AbstractInterfaceRequest() {
		super();
	}

	public AbstractInterfaceRequest(String appCode, String token) {
		this.appCode = appCode;
		this.token = token;
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
