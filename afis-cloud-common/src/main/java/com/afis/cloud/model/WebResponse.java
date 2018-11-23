package com.afis.cloud.model;

import java.io.Serializable;

/**
 * Json格式的返回结果 Created by hsw on 2017/1/10.
 */
public class WebResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int responseCode;
	private String responseDesc;
	private Object data;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "WebResponse{" + " responseCode=" + responseCode + ", responseDesc='" + responseDesc + '\'' + ", data="
				+ data + '}';
	}
}
