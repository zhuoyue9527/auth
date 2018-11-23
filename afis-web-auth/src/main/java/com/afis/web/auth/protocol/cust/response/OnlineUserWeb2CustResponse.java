package com.afis.web.auth.protocol.cust.response;

import com.afis.web.modal.UserDetails;

public class OnlineUserWeb2CustResponse {
	
	private String userName;// 用户帐号(登录名)
	private String loginKey;//

	public OnlineUserWeb2CustResponse(UserDetails userDetails) {
		this.userName = userDetails.getUserName();
		this.loginKey = userDetails.getLoginKey();
	}
	
	public OnlineUserWeb2CustResponse(String userName,String loginkey) {
		this.userName = userName;
		this.loginKey = loginkey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	@Override
	public String toString() {
		return "OnlineUserWeb2CustResponse [userName=" + userName + ", loginKey=" + loginKey + "]";
	}

}
