package com.afis.cloud.exception;

/**
 * 公共业务异常
 * 
 * @className: CommonException
 * @Company: Afis
 * @author zhuzhenghua
 * @date 2018年8月24日 上午10:33:53
 * @version
 */
public class CommonException extends WebBusinessException {

	public static final int MIN_CODE = 50500;
	public static final int MAX_CODE = 50999;

	public CommonException(int errorCode) {
		super(errorCode);
	}

	public CommonException(int errorCode, String message) {
		super(errorCode);
		this.setDesc(message);
	}

	/**
	 * {0}不存在 例如：商品不存在、库存不存在、匹配单不存在
	 *
	 */
	public static final int NOT_EXISTS = 50500;

	/**
	 * 数据已变动，请刷新重做
	 */
	public static final int DATA_EXPIRED = 50501;

	/** {0}超时 */
	public static final int TIME_OUT = 50502;

	/** 接口调用异常：{0} */
	public static final int INTERFACE_EXCEPTION = 50503;

	/** 不是{0} */
	public static final int NOT_IS = 50504;

	/** {0}不能为空 */
	public static final int NULL_NOT_ALLOWED = 50505;

	/** {0}应大于0 */
	public static final int MUST_GREATER_THAN_ZERO = 50506;	
	
	/** 不能操作其他会员的数据 */
	public static final int INFO_NOT_BELONG_TO_YOU = 50507;

	/** 交易时间为空 */
	public static final int TRADE_TIME_EMPTY = 50509;

	/**
	 * 更新失败，{0}不存在
	 */
	public static final int UPDATE_FAIL = 50510;

	/**
	 * 删除失败，{0}不存在
	 */
	public static final int DELETE_FAIL = 50511;

	/**
	 * 新增失败
	 */
	public static final int ADD_FAIL = 50512;

	/**
	 * {0}必须为数字类型
	 */
	public static final int NUMBER_CAST_ERROR = 50513;

	/**
	 * 文件复制错误
	 */
	public static final int COPY_FILE_ERROR = 50514;

	/**
	 * 没有对应的交易会员
	 */
	public static final int NO_TRADE_MEMBER = 50515;

	/**
	 * {0}没有对应的交易会员
	 */
	public static final int NO_TRADE_MEMBER_PARAM = 50516;

	/**
	 * {0}字符长度过长
	 */
	public static final int CHARACTER_TOO_LONG = 50517;

	/**
	 * 该条信息已注销，无需再次注销或{0}不存在
	 */
	public static final int LOGOUT_FAIL = 50518;

	/**
	 * 买卖双方不能为同一个交易会员
	 */
	public static final int SELLER_BUYEER_CANNOT_SAME_MEMBER = 50519;

	/**
	 * {0}输入过长
	 */
	public static final int INPUT_IS_TOO_LONG = 50520;

	/** {0}已存在 */
	public static final int CANNOT_DUPLIATED = 50521;

	/** 该{0}已与{1}绑定，不能删除 */
	public static final int BIND_CANNOT_DELETED = 50522;

	/** {0} */
	public static final int ANY_DESC = 50523;

	/** 配置文件读取错误 */
	public static final int CONFIG_FILE_ERROR = 50524;
	/**
	 * 注销失败，{0}不存在或状态已注销
	 */
	public static final int CANCEL_FAIL = 50525;

	/**
	 * {0}不能为0
	 */
	public static final int NOT_ALLOW_ZERO = 50526;

	/**
	 * {0}必须为正数
	 */
	public static final int MUST_POSITIVE = 50527;

	/**
	 * {0}大于最大值{1}
	 */
	public static final int OUT_OF_MAX = 50528;

	/**
	 * {0}小于最小值{1}
	 */
	public static final int OUT_OF_MIN = 50529;

	/**
	 * 操作失败，操作员类型必须是{0}
	 */
	public static final int CLIENT_TYPE_MUST_BE = 50530;

	/**
	 * 操作失败，操作员状态必须是{0}
	 */
	public static final int OPERATOR_STATUS_MUST_BE = 50531;

	/**
	 * 操作失败，操作员绑定类型必须是{0}
	 */
	public static final int BIND_TYPE_MUST_BE = 50532;

	/**
	 * 操作失败，操作员所属会员状态必须是{0}
	 */
	public static final int BIND_MEMBER_STATUS_MUST_BE = 50533;

	/**
	 * 日期格式化错误
	 */
	public static final int DATE_FORMAT_ERROR = 50534;

	/**
	 * 扣除资金异常，请联系管理员
	 */
	public static final int FUND_TRANSFER_FAIL = 50535;
	/**
	 * 该条信息已冻结或注销，无需再次操作或{0}不存在
	 */
	public static final int LOGOUT_FREEZE_FAIL = 50536;

	public static final int SERVICE_NOT_DEVELOPE = 50537;

	public static final int SERVICE_NOT_EXIST = 50538;
	
	/** 只有状态为{0}的{1}才能{2} */
	public static final int OBJ_ONLY_STATUS_CAN_DO_SOMETHING = 50539;
	/** 只能{0}自己的{1}	 */
	public static final int ONLY_CAN_DO_SOMETHING_ABOUT_MYTHING= 50540;

}