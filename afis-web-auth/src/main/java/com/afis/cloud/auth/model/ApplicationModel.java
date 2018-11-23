package com.afis.cloud.auth.model;

import java.util.Date;

public class ApplicationModel {
	
	private long id;

	private String appCode;

	private String appName;

	private String key;

	private String urlCallback;

	private String logoPath;

	private String status;

	private String remark;

	private Long operateAppId;

	private Long operator;

	private Date operateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrlCallback() {
		return urlCallback;
	}

	public void setUrlCallback(String urlCallback) {
		this.urlCallback = urlCallback;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return "Application [id=" + id + ", appCode=" + appCode + ", appName=" + appName + ", key=" + key
				+ ", urlCallback=" + urlCallback + ", logoPath=" + logoPath + ", status=" + status + ", remark="
				+ remark + ", operateAppId=" + operateAppId + ", operator=" + operator + ", operateTime=" + operateTime
				+ "]";
	}

}