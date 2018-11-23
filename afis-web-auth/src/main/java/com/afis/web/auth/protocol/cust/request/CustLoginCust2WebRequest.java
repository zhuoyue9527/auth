package com.afis.web.auth.protocol.cust.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户端-->web
 * 
 * @author Chengen
 *
 */
@ApiModel("登录请求对象")
public class CustLoginCust2WebRequest {
	@ApiModelProperty(name = "username", position = 0, required = true, dataType = "string", value = "登录名")
	private String username;// 登录名
	@ApiModelProperty(name = "password", position = 1, required = true, dataType = "string", value = "密码")
	private String password;// 密码
	@ApiModelProperty(name = "captcha", position = 2, dataType = "string", value = "验证码")
	private String captcha;// 验证码
	@ApiModelProperty(name = "appCode", position = 3, required = true, dataType = "string", value = "app授权码")
	private String appCode;// app授权码
	@ApiModelProperty(name = "redirectUrl", position = 4, dataType = "string", value = "回调url")
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

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

}
