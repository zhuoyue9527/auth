package com.afis.cloud.auth.model;

import java.io.Serializable;

public class TokenModel implements Serializable {
	private String token;
	private long appId;// 应用id
	private String appCode;
	private long createTime;// 生成时间
	private long lastUpdateTime;// 最后更新时间
	private long userId;// 用户id
	private String userName;// 用户帐号(登录名)

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "TokenMappingModel [token=" + token + ", appId=" + appId + ", appCode=" + appCode + ", createTime="
				+ createTime + ", lastUpdateTime=" + lastUpdateTime + ", userId=" + userId + ", userName=" + userName
				+ "]";
	}

}
