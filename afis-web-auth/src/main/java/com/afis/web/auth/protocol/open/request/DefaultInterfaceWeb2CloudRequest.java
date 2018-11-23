package com.afis.web.auth.protocol.open.request;

import com.afis.web.model.DefaultCloudRequest;

/**
 * 接口请求默认对象
 * 
 * 从web请求cloud
 * 
 * @author Chengen
 *
 */
public class DefaultInterfaceWeb2CloudRequest extends DefaultCloudRequest {
	private String appCode;
	private String token;

	public DefaultInterfaceWeb2CloudRequest(AbstractInterfaceRequest request) {
		this.appCode = request.getAppCode();
		this.token = request.getToken();
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
