package com.afis.web.modal;

/**
 * 调用认证时的参数封装
 * 
 * @author Chengen
 *
 */
public class AuthProtocolConstants {

	/**
	 * 登录需要传递的参数
	 * 
	 * @author Chengen
	 *
	 */
	public enum AuthorizeProtocol implements CommonKeyValue {
		TYPE("type", ""), APP_CODE("app_code", ""), TIME("time", ""), ENCRYPT("encrypt",
				""), REDIRECT_URL("redirect_url", ""), TICKET("ticket", "");

		private String key;
		private String value;

		private AuthorizeProtocol(String key, String value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public String getValue() {
			return value;
		}
	}

	/**
	 * 与token请求相关的参数
	 * 
	 * @author Chengen
	 *
	 */
	public enum TokenProtocol implements CommonKeyValue {
		TICKET("ticket", ""), APP_CODE("app_code", ""), TIME("time", ""), ENCRYPT("encrypt",
				""), REDIRECT_URL("redirect_url", "");
		private String key;
		private String value;

		private TokenProtocol(String key, String value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public String getKey() {
			return key;
		}

		@Override
		public String getValue() {
			return value;
		}
	}
}
