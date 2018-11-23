package com.afis.web.auth.protocol.cust.request;

import com.afis.web.model.DefaultCloudRequest;

/**
 * web--->cloud
 * 
 * @author Chengen
 *
 */
public class CustLoginWeb2CloudRequest extends DefaultCloudRequest {
	private String username;// 登录名
	private String password;// 密码
	private String redirectUrl;// 回调url
	private String appCode;// app授权码
	private String loginIp;// 登录ip
	private String browserInfo;// 浏览器信息
	private String systemInfo;// 系统信息

	public CustLoginWeb2CloudRequest(CustLoginCust2WebRequest request) {
		this.username = request.getUsername();
		this.password = request.getPassword();
		this.appCode = request.getAppCode();
		this.redirectUrl = request.getRedirectUrl();
	}

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

}
