package com.afis.web.exception;

import com.afis.hfp.SystemException;

/**
 * Created by hsw on 2017/1/11.
 */
public class WebSystemException extends SystemException {

    public static final int MIN_CODE = -19999;
    public static final int MAX_CODE = -29999;
    public static final int CODE = MIN_CODE;

    public static final int CONVERTER_EXCEPTION = -20000;

    private String[] desc;

    public WebSystemException(int errorCode) {
        super(errorCode);
    }

    public WebSystemException(int errorCode, String message) {
        super(errorCode, message);
    }

    public WebSystemException(int errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public WebSystemException(int errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }

    public WebSystemException() {
    }

    public WebSystemException(String message) {
        super(message);
    }

    public WebSystemException(Throwable throwable) {
        super(throwable);
    }

    public WebSystemException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public String[] getDesc() {
        return this.desc;
    }

    public void setDesc(String... desc) {
        this.desc = desc;
    }
}
