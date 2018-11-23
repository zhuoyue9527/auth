package com.afis.cloud.model;


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
		VALID("1", "有效"), INVALID("2", "无效");

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
	 * @author chengen
	 *
	 */
	public enum ClientType implements CommonKeyValue {
		MANAGER("1", "认证管理员账号"), TRADER("2", "应用账号");
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
	
	 /**
     *操作员或会员状态
     */
	public enum OperatorMemberStatus implements CommonKeyValue {
    	Valid("1","正常"),
    	Invalid("2","注销"), 
    	Frozen("3", "冻结");
    	
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
	
	public enum MemberType implements CommonKeyValue {
    	Common("1","普通会员"),
    	Agreement("2","协议会员"),
    	VIP("3","VIP会员");
    	
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
     * 证件类型
     * @author chengen
     *
     */
    public enum Cercertificate implements CommonKeyValue{
    	SFZ("1","身份证"),HZ("1","护照"),JGZ("1","军官证");
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
	 * 操作员绑定关系类型
	 */
	public enum Type implements CommonKeyValue {
		MEMBER("1", "会员"), FACTOR("2", "厂家");

		private String key;
		private String value;

		private Type(String key, String value) {
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
	 * ticket队列名：key:ticket,value:TicketMappingModel
	 */
	public static final String TICKET_QUEUE_KEY = "AUTH-TICKET";
	
	/**
	 * loginkey的前缀
	 * key:loginKey,Value:List<token>
	 */
	public static final String LOGIN_KEY_PREFIX = "AUTH-LOGIN-";
	/**
	 * redis中token作为key的前缀
	 * 
	 * key:token,value:loginKey
	 */
	public static final String TOKEN_KEY_PREFIX = "AUTH-TOKEN-";
	
	/**
	 * 登录失败的次数记录
	 * 
	 */
	public static final String LOGIN_FAIL_PREFIX = "LOGIN-FAIL-";
}
