package com.afis.web.exception;

import com.afis.hfp.BusinessException;

/**
 * Created by hsw on 2017/1/11.
 */
public class WebBusinessException extends BusinessException {

    public static final int MIN_CODE = 50000;
    public static final int MAX_CODE = 59999;
    public static final int CODE = MIN_CODE;
    
    /**
	 * 提交前，没有点击获取验证码按钮。
	 */
	public static final int NOT_SEND_VERIFICATIONCODE = 50000;
	/**
	 * 输入的短信验证码有误。
	 */
	public static final int THE_WRONG_VERIFICATIONCODE = 50001;

	/**
	 * 输入的短信验证码已过期。
	 */
	public static final int THE_OVERDUE_VERIFICATIONCODE = 50002;

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
