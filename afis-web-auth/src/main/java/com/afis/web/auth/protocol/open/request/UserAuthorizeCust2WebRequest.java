package com.afis.web.auth.protocol.open.request;

import io.swagger.annotations.ApiModel;

/**
 * 用户授权
 * @author Administrator
 *
 */
@ApiModel("用户授权")
public class UserAuthorizeCust2WebRequest extends AbstractInterfaceRequest {
	
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
