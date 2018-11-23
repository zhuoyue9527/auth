package com.afis.web.modal;

public class CommonConstants {
	
	/**
	 * 是/否
	 * @author chengen
	 *
	 */
	public enum TrueOrFalse  implements CommonKeyValue {
		TRUE("1","是"),FALSE("0","否");
		private String key;
		private String value;

		private TrueOrFalse(String key, String value) {
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
	 * 状态
	 */
	public enum Status implements CommonKeyValue {
		Valid("1", "有效"), Invalid("2", "无效");

		private String key;
		private String value;

		private Status(String key, String value) {
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
	 * 类型
	 * 
	 * @author chengen
	 *
	 */
	public enum ClientType implements CommonKeyValue {
		MANAGER("1", "管理员"), TRADER("2", "交易员");
		private String key;
		private String value;

		private ClientType(String key, String value) {
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

	public enum Cercertificate implements CommonKeyValue {
		SFZ("1", "身份证"), HZ("1", "护照"), JGZ("1", "军官证");
		private String key;
		private String value;

		private Cercertificate(String key, String value) {
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
	 * 会员类型
	 * 
	 * @author chengen
	 *
	 */
	public enum MemberType implements CommonKeyValue {
		PERSONAL("1", "个人会员"), BUSINESS("2", "企业会员");

		private String key;
		private String value;

		private MemberType(String key, String value) {
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
	 * 操作员或会员状态
	 */
	public enum OperatorMemberStatus implements CommonKeyValue {
		Valid("1", "正常"), Invalid("2", "注销");

		private String key;
		private String value;

		private OperatorMemberStatus(String key, String value) {
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

	public static final String PARAM_WEBREQUEST = "webRequest";

	public static final String PARAM_USERDETAILS = "userDetails";

	public static final String USER_DETAILS_PREFIX = "AUTH-USER-";
}
