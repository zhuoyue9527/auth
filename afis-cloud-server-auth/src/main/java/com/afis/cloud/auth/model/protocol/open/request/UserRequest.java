package com.afis.cloud.auth.model.protocol.open.request;

/**
 * 用户维护时的对象
 * 
 * @author Chengen
 *
 */
public class UserRequest extends AbstractInterfaceRequest {
	private String userAccount;
	private String userName;
	private String password;
	private String mobile;
	private String remark;

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
}
