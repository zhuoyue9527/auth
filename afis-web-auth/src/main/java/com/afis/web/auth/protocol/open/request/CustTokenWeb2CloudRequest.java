package com.afis.web.auth.protocol.open.request;

import com.afis.web.model.DefaultCloudRequest;

/**
 * 请求获取token的参数
 * 
 * @author Chengen
 *
 */
public class CustTokenWeb2CloudRequest extends DefaultCloudRequest {

	private String appCode;

	private String ticket;

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
}
