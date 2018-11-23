package com.afis.cloud.auth.util;

import com.afis.cloud.model.CommonKeyValue;

public class AuthConstants {

	/**
	 * 登录状态
	 * 
	 * @author Chengen
	 *
	 */
	public enum LoginStatus implements CommonKeyValue {
		SUCCESS("1", "登录成功"), FAIL("2", "登录失败");
		private String key;
		private String value;

		private LoginStatus(String key, String value) {
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
	 * 操作状态
	 *
	 * @author Chengen
	 *
	 */
	public enum OperateStatus implements CommonKeyValue {
		SUCCESS("1", "操作成功"), FAIL("2", "操作失败");
		private String key;
		private String value;

		private OperateStatus(String key, String value) {
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
