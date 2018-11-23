package com.afis.cloud.auth.model.protocol.cust.response;

import com.afis.web.modal.UserDetails;

/**
 * 用户登录返回结果
 * 
 * @author Administrator
 *
 */
public class CustomerLoginResponse {

	private String ticket;// ticket
	private String redirectUrl;// 回调url
	private UserDetails userDetails;// 登录信息

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
}
