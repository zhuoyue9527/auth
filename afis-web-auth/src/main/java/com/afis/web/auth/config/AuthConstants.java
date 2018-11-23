package com.afis.web.auth.config;

public class AuthConstants {

	/**
	 * 分割符号
	 */
	public static final String SPLIT_SIGN = ":";
	
	/**
	 * key的结构是:AUTH-ONLINE-USER:loginKey,value:UserDetails
	 */
	public static final String ONLINE_USER = "AUTH-ONLINE-USER";

	/**
	 * 应用存放的队列名：key：appCode,value:Application
	 */
	public static final String APPLICTION = "AUTH-APPLICTION";// 应用列表的缓存
}
