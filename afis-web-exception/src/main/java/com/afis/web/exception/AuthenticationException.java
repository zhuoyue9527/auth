package com.afis.web.exception;

/**
 * Created by hsw on 2017/1/12.
 */
public class AuthenticationException extends WebBusinessException {

    //未登录
    public static final int NOT_LOGIN = 50001;
    //token已过期
    public static final int TOKEN_EXPIRED = 50002;
    //登录已超时
    public static final int LOGIN_TIME_OUT = 50003;
    //在线环境发生了变化
    public static final int LOGIN_LOCATION_CHANGED = 50004;
    //无访问权限
    public static final int NO_PERMISSION = 50005;
    
    public static final int PARAMS_NOT_EXIST = 50006;
    //验证码不正确
    public static final int CAPTCHA_NOT_RIGHT = 50007;
    //用户不存在
    public static final int USER_NOT_EXIST = 50008;
    
    public static final int USER_STATUS_INVALID = 50009;
    
    //获取权限失败
    public static final int USER_OBTAIN_ROLE_FAIL = 50013;
    
    public AuthenticationException(int errorCode) {
        super(errorCode);
    }

    public AuthenticationException(int errorCode, String message) {
    	super(errorCode, message);
    }

    public AuthenticationException(int errorCode, Throwable throwable) {
        super(errorCode, throwable);
    }

    public AuthenticationException(int errorCode, String message, Throwable throwable) {
        super(errorCode, message, throwable);
    }

    public AuthenticationException() {
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(Throwable throwable) {
        super(throwable);
    }

    public AuthenticationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
