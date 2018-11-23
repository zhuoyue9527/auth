package com.afis.web.modal;

import java.io.Serializable;

public class UserApplications implements Serializable {
	private long userId;
	private long appId;
	private String appCode;
	private String warrantPermit;
	private String warrant;

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getWarrantPermit() {
		return warrantPermit;
	}

	public void setWarrantPermit(String warrantPermit) {
		this.warrantPermit = warrantPermit;
	}

	public String getWarrant() {
		return warrant;
	}

	public void setWarrant(String warrant) {
		this.warrant = warrant;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserApplications [userId=" + userId + ", appId=" + appId + ", appCode=" + appCode + ", warrantPermit="
				+ warrantPermit + ", warrant=" + warrant + "]";
	}

}
