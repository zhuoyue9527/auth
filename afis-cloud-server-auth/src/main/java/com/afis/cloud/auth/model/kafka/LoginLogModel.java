package com.afis.cloud.auth.model.kafka;

import java.io.Serializable;
import java.util.Date;

public class LoginLogModel implements Serializable {

	private long userId;

	private long loginApp;

	private String loginIp;

	private String status;

	private String systemInfo;

	private String browserInfo;

	private String remark;

	private Date loginTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getLoginApp() {
		return loginApp;
	}

	public void setLoginApp(long loginApp) {
		this.loginApp = loginApp;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(String systemInfo) {
		this.systemInfo = systemInfo;
	}

	public String getBrowserInfo() {
		return browserInfo;
	}

	public void setBrowserInfo(String browserInfo) {
		this.browserInfo = browserInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Override
	public String toString() {
		return "LoginLogModel [userId=" + userId + ", loginApp=" + loginApp + ", loginIp=" + loginIp + ", status="
				+ status + ", systemInfo=" + systemInfo + ", browserInfo=" + browserInfo + ", remark=" + remark
				+ ", loginTime=" + loginTime + "]";
	}
}
