package com.afis.cloud.auth.model.kafka;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/5 11:18
 */
public class OperateLogModel implements Serializable {

	private long id;

	private long functionId;

	private String loginIp;

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

	public long getFunctionId() {
		return functionId;
	}

	public void setFunctionId(long functionId) {
		this.functionId = functionId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
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
		return "OperateLogModel [id=" + id + ", functionId=" + functionId + ", loginIp=" + loginIp + ", status="
				+ status + ", remark=" + remark + ", operateAppId=" + operateAppId + ", operator=" + operator
				+ ", operateTime=" + operateTime + "]";
	}

}
