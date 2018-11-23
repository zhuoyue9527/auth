package com.afis.web.modal;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Chengen
 *
 */
public class UserDetails implements Serializable {
	private long id;
	private String userName;// 用户帐号(登录名)
	private String loginKey;//
	private String sourceIp;// Ip信息
	private String browserInfo;// 浏览器信息
	private String systemInfo;// 系统信息
	private long loginTime;// 登录时间
	private long lastAccessTime;// 最后访问时间
	private String clientType;// 用户类型
	private List<UserApplications> appList;// 应用授权情况

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getSourceIp() {
		return sourceIp;
	}

	public void setSourceIp(String sourceIp) {
		this.sourceIp = sourceIp;
	}

	public String getBrowserInfo() {
		return browserInfo;
	}

	public void setBrowserInfo(String browserInfo) {
		this.browserInfo = browserInfo;
	}

	public String getSystemInfo() {
		return systemInfo;
	}

	public void setSystemInfo(String systemInfo) {
		this.systemInfo = systemInfo;
	}

	public long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public List<UserApplications> getAppList() {
		return appList;
	}

	public void setAppList(List<UserApplications> appList) {
		this.appList = appList;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", userName=" + userName + ", loginKey=" + loginKey + ", sourceIp=" + sourceIp
				+ ", browserInfo=" + browserInfo + ", systemInfo=" + systemInfo + ", loginTime=" + loginTime
				+ ", lastAccessTime=" + lastAccessTime + ", clientType=" + clientType + ", appList=" + appList + "]";
	}

}
