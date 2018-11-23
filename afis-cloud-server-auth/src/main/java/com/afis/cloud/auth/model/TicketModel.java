package com.afis.cloud.auth.model;

import java.io.Serializable;

/**
 * ticket 映射到的对象
 * 
 * @author Chengen
 *
 */
public class TicketModel implements Serializable {
	private String ticket;// ticket值
	private long appId;// 应用id
	private String appCode;// 应用code
	private long createTime;// ticket生成时间
	private long userId;// 用户id
	private String userName;// 用户帐号(登录名)

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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

	@Override
	public String toString() {
		return "TicketModel [ticket=" + ticket + ", appId=" + appId + ", appCode=" + appCode + ", createTime="
				+ createTime + ", userId=" + userId + ", userName=" + userName + "]";
	}

}
