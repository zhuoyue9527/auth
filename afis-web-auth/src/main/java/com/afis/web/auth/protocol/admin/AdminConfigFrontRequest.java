package com.afis.web.auth.protocol.admin;

import com.afis.web.model.DefaultCloudRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Description:新增用户授权权限请求参数(界面--->管理端)
 * @Author: lizheng
 * @Date: 2018/11/2 13:50
 */
@ApiModel("table列维护")
public class AdminConfigFrontRequest extends DefaultCloudRequest {
	@ApiModelProperty(name = "appId", position = 0, required = true, dataType = "Long", value = "系统ID")
	private Long appId;
	@ApiModelProperty(name = "columns", position = 1, required = true, dataType = "String", value = "列")
	private String columns;
	@ApiModelProperty(name = "uid", position = 2, dataType = "String", value = "对象ID")
	private String uid;

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "AdminConfigFrontRequest [appId=" + appId + ", columns=" + columns + ", uid=" + uid + "]";
	}

}
