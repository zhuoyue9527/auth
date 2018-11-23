package com.afis.cloud.auth.model.protocol.open.response;

import com.afis.cloud.auth.model.TokenModel;

/**
 * 获取token后返回给用户的结构
 * 
 * @author Chengen
 *
 */
public class CustomerTokenResponse {
	private String token;
	private long userId;// 用户id
	private String userName;// 用户帐号(登录名)

	public CustomerTokenResponse(TokenModel model) {
		this.token = model.getToken();
		this.userId = model.getUserId();
		this.userName = model.getUserName();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
