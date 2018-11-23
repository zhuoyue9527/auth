package com.afis.web.auth.protocol.open.request;

import com.afis.web.model.DefaultCloudRequest;

/**
 * 用户维护时的对象
 *
 * @author Chengen
 */
public class UserWeb2CloudRequest extends DefaultCloudRequest {
	private String userAccount;
	private String userName;
	private String password;
	private String mobile;
	private String remark;
	private String token;
	private String appCode;
	
	public UserWeb2CloudRequest(UserCust2WebRequest request) {
		this.userAccount = request.getUserAccount();
		this.userName = request.getUserName();
		this.password = request.getPassword();
		this.mobile = request.getMobile();
		this.remark = request.getRemark();
		this.token = request.getToken();
		this.appCode = request.getAppCode();
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	@Override
	public String toString() {
		return "UserRequest [userAccount=" + userAccount + ", userName=" + userName + ", password=" + password
				+ ", mobile=" + mobile + ", remark=" + remark + ", token=" + token + ", appCode=" + appCode + "]";
	}

}
