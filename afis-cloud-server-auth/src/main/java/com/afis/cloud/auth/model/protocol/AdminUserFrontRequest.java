package com.afis.cloud.auth.model.protocol;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AdminUserFrontRequest extends DefaultRequest {

	@ApiModelProperty(name = "userAccount", position = 0, required = true, dataType = "string", value = "登录账号")
	private String userAccount;
	@ApiModelProperty(name = "userName", position = 1, required = true, dataType = "string", value = "账号名称")
	private String userName;
	@ApiModelProperty(name = "password", position = 2, required = true, dataType = "string", value = "密码")
	private String password;
	@ApiModelProperty(name = "mobile", position = 3, required = false, dataType = "string", value = "联系手机")
	private String mobile;
	@ApiModelProperty(name = "clientType", position = 4, required = true, dataType = "string", value = "类型")
	private String clientType;
	@ApiModelProperty(name = "registerAppId", position = 5, required = true, dataType = "Long", value = "注册应用")
	private Long registerAppId;
	@ApiModelProperty(name = "remark", position = 6, required = false, dataType = "string", value = "备注")
	private String remark;
	@ApiModelProperty(name = "id", position = 7, required = false, dataType = "Long", value = "ID")
	private Long id;

	private Long registerOperator;

	private Date registerTime;

	private Long operateAppId;

	private Long operator;

	private Date operateTime;

	private Date lastLoginTime;

	private String lastLoginIp;

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

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public Long getRegisterAppId() {
		return registerAppId;
	}

	public void setRegisterAppId(Long registerAppId) {
		this.registerAppId = registerAppId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRegisterOperator() {
		return registerOperator;
	}

	public void setRegisterOperator(Long registerOperator) {
		this.registerOperator = registerOperator;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Long getOperateAppId() {
		return operateAppId;
	}

	public void setOperateAppId(Long operateAppId) {
		this.operateAppId = operateAppId;
	}

	public Long getOperator() {
		return operator;
	}

	public void setOperator(Long operator) {
		this.operator = operator;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Override
	public String toString() {
		return "AdminUserFrontRequest{" + "userAccount='" + userAccount + '\'' + ", userName='" + userName + '\''
				+ ", password='" + password + '\'' + ", mobile='" + mobile + '\'' + ", clientType='" + clientType + '\''
				+ ", registerAppId=" + registerAppId + ", remark='" + remark + '\'' + ", id=" + id + '\''
				+ ", registerOperator=" + registerOperator + ", registerTime=" + registerTime + ", operateAppId="
				+ operateAppId + ", operator=" + operator + ", operateTime=" + operateTime + ", lastLoginTime="
				+ lastLoginTime + ", lastLoginIp='" + lastLoginIp + '\'' + '}';
	}
}
