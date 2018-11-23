package com.afis.cloud.auth.util;

public class AuthCache {

	/**
	 * 分割符号
	 */
	public static final String SPLIT_SIGN = ":";

	public static String currentDate = null;

	/**
	 * 用户名对应loginKey的队列名：key:userName,value:loginKey
	 */
	public static final String AUTH_USER_LOGINKEY = "AUTH-USER-LOGINKEY";

	/**
	 * key的结构是:AUTH-ONLINE-USER:loginKey,value:UserDetails
	 */
	public static final String ONLINE_USER = "AUTH-ONLINE-USER";
	
	/**
	 * key的结构是：AUTH_USER_TOKEN:APPCODE:USERNAME，value：token
	 */
	public static final String AUTH_USER_TOKEN = "AUTH_USER_TOKEN";

	/**
	 * key的结构是：AUTH-TOKEN:token，value：TokenModel
	 */
	public static final String AUTH_TOKEN = "AUTH-TOKEN";
	
	/**
	 * key的结构：AUTH-TICKET:ticket，value:TicketModel
	 */
	public static final String AUTH_TICKET = "AUTH-TICKET";

	/**
	 * 应用存放的队列名：key：appCode,value:Application
	 */
	public static final String APPLICTION = "AUTH-APPLICTION";// 应用列表的缓存
	
	/**
	 * 所有功能列表
	 */
	public static final String FUNCTION = "AUTH-FUNCTION-ALL";// 所有功能

	/**
	 * 当前日期，
	 */
	public static final String CURRENT_DAY = "AUTH-CURRENT-DAY";// 当前日期

	/**
	 * 失败次数的队列名：key:userName,value:失败次数
	 */
	public static final String USER_FAIL_COUNT = "AUTH-LOGIN-FAIL";// 失败次数
}
