package com.afis.cloud.auth.model.protocol.cust.request;

/**
 * 用户登录请求参数
 * 
 * @author Chengen
 *
 */
public class CustomerLoginRequest {
	private String username;// 登录名
	private String password;// 密码
	private String appCode;// app授权码
	private String loginIp;// 登录iP
	private String browserInfo;// 浏览器信息
	private String systemInfo;// 系统信息
	private String redirectUrl;// 回调url

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

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
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

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public String toString() {
		return "CustomerLoginRequest [username=" + username + ", password=" + password + ", appCode=" + appCode
				+ ", loginIp=" + loginIp + ", browserInfo=" + browserInfo + ", systemInfo=" + systemInfo
				+ ", redirectUrl=" + redirectUrl + "]";
	}

}
