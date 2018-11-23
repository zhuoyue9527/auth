package com.afis.cloud.auth.model.protocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Description:新增用户授权权限请求参数(界面--->管理端)
 * @Author: lizheng
 * @Date: 2018/11/2 13:50
 */
@ApiModel("新增用户授权权限")
public class AdminUserAppFrontRequest extends DefaultRequest {
	@ApiModelProperty(name = "appIdList", position = 0, required = true, dataType = "List", value = "应用列表")
	private List<Long> appIdList;
	@ApiModelProperty(name = "userId", position = 1, required = true, dataType = "Long", value = "账号ID")
	private Long userId;
	@ApiModelProperty(name = "warrant", position = 3, required = true, dataType = "string", value = "会否授权")
	private String warrant;
	@ApiModelProperty(name = "appPassword", position = 4, required = true, dataType = "string", value = "登录密码")
	private String appPassword;
	@ApiModelProperty(name = "operateAppId", position = 5, required = true, dataType = "Long", value = "账号ID")
	private Long operateAppId;

	public List<Long> getAppIdList() {
		return appIdList;
	}

	public void setAppIdList(List<Long> appIdList) {
		this.appIdList = appIdList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getWarrant() {
		return warrant;
	}

	public void setWarrant(String warrant) {
		this.warrant = warrant;
	}

	public String getAppPassword() {
		return appPassword;
	}

	public void setAppPassword(String appPassword) {
		this.appPassword = appPassword;
	}

	public Long getOperateAppId() {
		return operateAppId;
	}

	public void setOperateAppId(Long operateAppId) {
		this.operateAppId = operateAppId;
	}
}
