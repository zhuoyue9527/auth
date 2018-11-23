package com.afis.cloud.auth.model.protocol.open.request;

/**
 * 根据ticket请求token的参数
 * 
 * @author Chengen
 *
 */
public class CustomerTokenRequest {
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

	@Override
	public String toString() {
		return "CustomerTokenRequest [appCode=" + appCode + ", ticket=" + ticket + "]";
	}
}
