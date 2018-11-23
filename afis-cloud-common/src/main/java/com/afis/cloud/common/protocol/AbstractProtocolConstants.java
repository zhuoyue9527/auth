package com.afis.cloud.common.protocol;

import com.afis.cloud.model.CommonKeyValue;

/**
 * 抽象协议类
 * 
 * 定义公共的一些参数
 * 
 * @author chengen
 *
 */
public abstract class AbstractProtocolConstants {

	/**
	 * 公共的部分协议参数
	 * @author chengen
	 *
	 */
	public enum CommonProtocol implements CommonKeyValue {
		OPERATOR_ID("operatorId", "操作员ID"),
		APP_ID("appId","系统ID"),
		CURRENT_OPERATOR("operatorId","登录操作员");

		private String key;
		private String value;

		private CommonProtocol(String key, String value) {
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
	 * 分页的协议参数
	 * @author chengen
	 *
	 */
	public enum PageProtocol implements CommonKeyValue {
		START("start","起始条数"),
		LIMIT("limit","每页条数");
		
		private String key;
		private String value;

		private PageProtocol(String key, String value) {
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
	 * 
	 * @author chengen
	 *
	 */
	public enum TableConfigProtocol implements CommonKeyValue {
		UID("uid","对象ID"),
		COLUMNS("columns","排序字段");
		
		private String key;
		private String value;

		private TableConfigProtocol(String key, String value) {
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