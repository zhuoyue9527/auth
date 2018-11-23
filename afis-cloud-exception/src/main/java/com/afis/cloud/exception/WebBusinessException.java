package com.afis.cloud.exception;

import com.afis.BusinessException;

/**
 * web业务异常
 * 
 * @className: WebBusinessException
 * @Company: Afis
 * @author zhuzhenghua
 * @date 2018年8月24日 上午10:34:25
 * @version
 */
public class WebBusinessException extends BusinessException {
	public static final int MIN_CODE = 50000;
	public static final int MAX_CODE = 59999;
	public static final int CODE = MIN_CODE;

	private String[] desc;

	public WebBusinessException(int errorCode) {
		super(errorCode);
	}

	public WebBusinessException(int errorCode, String message) {
		super(errorCode, message);
	}

	public WebBusinessException(int errorCode, Throwable throwable) {
		super(errorCode, throwable);
	}

	public WebBusinessException(int errorCode, String message, Throwable throwable) {
		super(errorCode, message, throwable);
	}

	public WebBusinessException() {
	}

	public WebBusinessException(String message) {
		super(message);
	}

	public WebBusinessException(Throwable throwable) {
		super(throwable);
	}

	public WebBusinessException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public String[] getDesc() {
		return this.desc;
	}

	public void setDesc(String... desc) {
		this.desc = desc;
	}
}
