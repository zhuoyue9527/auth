package com.afis.cloud.auth.model;

import java.util.Date;

/**
 * 存在缓存中的应用信息
 * 
 * @author Chengen
 *
 */
public class AppFunctionModel {

	private long id;

	private long appId;

	private long functionId;

	private Long operateAppId;

	private Long operator;

	private Date operateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(long functionId) {
		this.functionId = functionId;
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

	@Override
	public String toString() {
		return "AppFunctionModel{" +
				"id=" + id +
				", appId=" + appId +
				", functionId=" + functionId +
				", operateAppId=" + operateAppId +
				", operator=" + operator +
				", operateTime=" + operateTime +
				'}';
	}
}