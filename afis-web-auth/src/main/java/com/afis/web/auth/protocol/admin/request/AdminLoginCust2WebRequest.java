package com.afis.web.auth.protocol.admin.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录请求参数(界面--->管理端)
 * 
 * @author Chengen
 *
 */
@ApiModel("登录请求对象")
public class AdminLoginCust2WebRequest {
	@ApiModelProperty(name = "username", position = 0, required = true, dataType = "string", value = "登录名")
	private String username;// 登录名
	@ApiModelProperty(name = "password", position = 1, required = true, dataType = "string", value = "密码")
	private String password;// 密码
	@ApiModelProperty(name = "captcha", position = 2, dataType = "string", value = "验证码")
	private String captcha;// 验证码

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

	@Override
	public String toString() {
		return "AdminLoginFrontRequest [username=" + username + ", password=" + password + ", captcha=" + captcha + "]";
	}
}
