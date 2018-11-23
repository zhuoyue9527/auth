package com.afis.cloud.auth.model.protocol.open.response;

import com.afis.cloud.entities.model.auth.User;

/**
 * 获取token后返回给用户的结构
 * 
 * @author Chengen
 *
 */
public class UserResponse extends User {

	private String operateAppName;
	private String operateName;
	private String registeAppName;
	private String registeOperateName;

	public String getOperateAppName() {
		return operateAppName;
	}

	public void setOperateAppName(String operateAppName) {
		this.operateAppName = operateAppName;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getRegisteAppName() {
		return registeAppName;
	}

	public void setRegisteAppName(String registeAppName) {
		this.registeAppName = registeAppName;
	}

	public String getRegisteOperateName() {
		return registeOperateName;
	}

	public void setRegisteOperateName(String registeOperateName) {
		this.registeOperateName = registeOperateName;
	}

	@Override
	public String toString() {
		return "UserResponse{" +
				"operateAppName='" + operateAppName + '\'' +
				", operateName='" + operateName + '\'' +
				", registeAppName='" + registeAppName + '\'' +
				", registeOperateName='" + registeOperateName + '\'' +
				'}';
	}
}
