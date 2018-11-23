package com.afis.cloud.auth.model.protocol;

/**
 * 管理端提交到cloud的登录信息
 * 
 * @author Chengen
 *
 */
public class AdminLoginRequest {
	private String username;// 登录名
	private String password;// 密码
	private String loginIp;// 登录iP
	private String browserInfo;// 浏览器信息
	private String systemInfo;// 系统信息
	private long appId;// 应用Id

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
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

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	@Override
	public String toString() {
		return "AdminLoginRequest [username=" + username + ", password=" + password + ", loginIp=" + loginIp
				+ ", browserInfo=" + browserInfo + ", systemInfo=" + systemInfo + ", appId=" + appId + "]";
	}

}
