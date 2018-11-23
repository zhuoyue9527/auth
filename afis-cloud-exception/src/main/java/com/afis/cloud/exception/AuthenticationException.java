package com.afis.cloud.exception;

/**
 * 权限异常
 * 
 * @author Administrator
 *
 */
public class AuthenticationException extends WebBusinessException {

	public static final int MIN_CODE = 50000;
	public static final int MAX_CODE = 50499;

	/**
	 * 未登录或已掉线
	 */
	public static final int NOT_LOGIN = 50001;
	/**
	 * token已过期
	 */
	public static final int TOKEN_EXPIRED = 50002;
	/**
	 * 登录已超时
	 */
	public static final int LOGIN_TIME_OUT = 50003;
	/**
	 * 在线环境发生了变化
	 */
	public static final int LOGIN_LOCATION_CHANGED = 50004;
	/**
	 * 无访问权限
	 */
	public static final int NO_PERMISSION = 50005;

	public static final int PARAMS_NOT_EXIST = 50006;

	public static final int CAPTCHA_NOT_RIGHT = 50007;

	/**
	 * 用户不存在或者密码错误
	 */
	public static final int USER_NOT_EXIST = 50008;

	public static final int USER_STATUS_INVALID = 50009;

	public static final int USER_FORBID_LOGIN = 50010;

	public static final int PASSWORD_DIFFERENT = 50011;

	public static final int USER_NOT_TRADER = 50012;
	/**
	 * 获取权限失败
	 */
	public static final int USER_OBTAIN_ROLE_FAIL = 50013;
	/**
	 * {0}字符长度过长
	 */
	public static final int BYTES_TOO_LONG_ERROR = 50014;

	/**
	 * 操作失败，该信息已冻结无需再次操作
	 */
	public static final int OPERATOR_FREEZEED_ERROR = 50018;
	/**
	 * 该信息已冻结,不能恢复操作
	 */
	public static final int OPERATOR_RESTORE_ERROR = 50019;
	/**
	 * 该信息已注销不能冻结操作
	 */
	public static final int OPERATOR_CANCELED_ERROR = 50020;
	/**
	 * 该信息已注销,不能解冻操作
	 */
	public static final int OPERATOR_CANCELEDTHRW_ERROR = 50021;
	
	/**
	 * 生成token时，传入的ticket无效
	 */
	public static final int AUTH_GRANT_TOKEN_TICKET_INVALID = 50022;
	
	/**
	 * token无效
	 */
	public static final int AUTH_TOKEN_INVALID = 50023;
	
	/**
	 * 用户的应用授权权限不存在或为不允许
	 */
	public static final int AUTH_USER_AUTHORIZE_PERMIT = 50024;
	
	/**
	 * 用户已经授权，不需要重复操作，请重新登录后试试
	 */
	public static final int AUTH_USER_AUTHORIZE_HAS_PERMIT = 50025;

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
