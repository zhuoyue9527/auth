package com.afis.cloud.utils;

import java.math.BigDecimal;

public class DataConstans {
	
	/**
	 * 日期格式：yyyyMMdd
	 */
	public static final String YYYYMMDD = "yyyyMMdd";
	
	/**
	 * 日期格式：yyyy-MM-dd
	 */
	public static final String YYYYMMDD_WITH_BAR = "yyyy-MM-dd";
	
	/**
	 * 日期格式：yyyy-MM-dd HH:mm:ss
	 */
	public static final String YYYYMMDDHHMMSS_WITH_BAR = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 日期格式：HH:mm:ss
	 */
	public static final String HHMMSS_WITH_COLON = "HH:mm:ss";
	
	/**
	 * 日期格式：HHmm
	 */
	public static final String HHMM = "HHmm";
	
	/**
	 * 日期格式：yyyy-MM-dd HH:mm
	 */
	public static final String YYYYMMDDHHMM_WITH_COLON = "yyyy-MM-dd HH:mm";
	
	/**
	 * 日期格式：yyyy年MM月dd日
	 */
	public static final String YYYYMMDD_WITH_CN = "yyyy年MM月dd日";

    /**
     * 日期格式：yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 格式化日期辅助字符串
	 */
	public static final String ADDITIONAL_PRE = " 00:00:00";
	
	/**
	 * 格式化日期辅助字符串
	 */
	public static final String ADDITIONAL_END = " 23:59:59";
	 /**
     * 中文：删除
     */
    public static final String DELETE_CN = "删除";
    
    /**
     * 中文：添加
     */
    public static final String ADD_CN = "添加";
    
    /**
     * 中文：修改
     */
    public static final String UPDATE_CN = "修改";
    
    
    /**
	 * 中文：交易日
	 */
	public static final String TRADE_DAY_CN = "交易日";
	
	
	/**
     * 中文：当前操作员
     */
    public static final String LOGIN_ACCOUNT_CN = "当前操作员";
    
    /**
     * 中文：已注销
     */
    public static final String ALREADY_CANCEL_CN = "已注销";
    
	/**
	 * 中文：或者
	 */
    public static final String OR_CN = "或者";
    
    /**
     * 中文：登录操作员
     */
    public static final String LOGIN_OPERATOR_CN = "登录操作员";
    
	/**
	 * 常量-1
	 */
	public static final int NEGATIVE_ONE = -1;
	
	/**
	 * 常量0
	 */
	public static final int ZERO = 0;
	
	/**
	 * 常量0
	 */
	public static final long ZERO_L = 0L;
	
	/**
	 * 常量1
	 */
	public static final int ONE = 1;
	
	/**
	 * 常量2
	 */
	public static final int TWO = 2;
	
	/**
	 * 常量3
	 */
	public static final int THREE = 3;
	
	/**
	 * 常量4
	 */
	public static final int FOUR = 4;
	
	/**
	 * 常量5
	 */
	public static final int FIVE = 5;
	
	/**
	 * 常量6
	 */
	public static final int SIX = 6;
	
	/**
	 * 常量7
	 */
	public static final int SEVEN = 7;
	
	/**
	 * 常量10
	 */
	public static final int TEN = 10;
	
	/**
	 * 常量18
	 */
	public static final long EIGHTEEN = 18L;
	
	/**
	 * 常量20
	 */
	public static final int TWENTY = 20;
	
	/**
	 * 常量20
	 */
	public static final long TWENTY_L = 20;
	
	/**
	 * 常量60
	 */
	public static final int SIXTY = 60;
	
	/**
	 * 一分钟的毫秒数：60000
	 */
	public static final long MINUTE_MILLISECOND= 60000L;
	
	/**
	 * 一天的毫秒数
	 */
	public static final long DAY_MILLISECOND = 86400000L;
	
	/**
	 * 横杠
	 */
	public static final String BAR = "-";
	
	/**
	 * 逗号
	 */
	public static final String COMMA = ",";
	
	/**
	 * 百分号
	 */
	public static final String SIGN = "%";
	
	/**
	 * 空字符串
	 */
	public static final String STRING_EMPTY = "";
	
	/**
	 * 空格字符串
	 */
	public static final String STRING_SPACE = " ";
	
	/**
	 * null字符串
	 */
	public static final String STRING_NULL = "null";
	
	
	
	/**
	 * 是否强制
	 */
	public static final String IS_FORCE = "isForce";
	
	/**
	 * 成功
	 */
	public static final String SUCCESS = "Success";
	
	/**
	 * 通知管理端时传的需求ID参数
	 */
	public static final String PROP_DEMANDID = "?demandId=";
	
	/**
	 * 通知管理端时传的是否撤销-否参数
	 */
	public static final String PROP_IS_CANCEL_FALSE = "&isCancel=false";
	
	/**
	 * 通知管理端时传的是否撤销-是参数
	 */
	public static final String PROP_IS_CANCEL_TRUE = "&isCancel=true";
	
	/**
	 * 成交链长度
	 */
	public static final String CHAIN_LENGTH = "chainLength";
	
	/**
	 * 系统时间
	 */
	public static final String SYSTIME = "sysTime";
	
	/**
	 * 成交编码
	 */
	public static final String TRADE_CODE = "tradeCode";
	
	/**
	 * 市场人员ID
	 */
	public static final String MARKETER_ID = "marketerId";
	
	/**
	 * 撮合员ID
	 */
	public static final String MATCH_MAKER_ID = "matchMakerId";
	
	/**
	 * 匹配撮合员
	 */
	public static final String MATCH_MAKER_MATCH = "matchMakerMatch";
	
	/**
	 * 生效日期
	 */
	public static final String VALID_DATE = "validDate";
	
	/**
	 * 手续费类型
	 */
	public static final String FEE_RATE_MODE = "feeRateMode";
	
	/**
	 * 需求编码第一位
	 */
	public static final String DEMAND_PRE = "D";
	
	/**
	 * 订单编码第一位
	 */
	public static final String ORDER_PRE = "O";
	
	/**
	 * 编码序列号格式
	 */
	public static final String CODE_FORMAT = "%06d";
	
	/**
	 * 己方订单ID
	 */
	public static final String SELF_ORDER_ID = "selfOrderId";
	
	/**
	 * 对方订单ID
	 */
	public static final String OPPONENT_ORDER_ID = "opponentOrderId";
	
	/**
	 * 银行ID
	 */
	public static final String BANK_ID = "bankId";
	
	/**
	 * 会员银行ID
	 */
	public static final String BANK_MEMBER_ID = "bankMemberId";
	
	/**
	 * 余额
	 */
	public static final String BALANCE = "balance";
	
	/**
	 * 出金在途
	 */
	public static final String  OUTWAY = "outWay";
	
	/**
	 * 资金项ID
	 */
	public static final String CAPTIALITEM_ID = "captialItemId";
	
	/**
	 * 出项
	 */
	public static final String OUTFUND = "outFund";
	
	/**
	 * 入项
	 */
	public static final String INFUND = "inFund";
	
	/**
	 * 支出在途
	 */
	public static final String EXPENDITUREWAY = "expenditureWay";
	
	/**
	 * 收入在途
	 */
	public static final String INCOMEWAY = "incomeWay";
	
	/**
	 * 转让盈利冻结
	 */
	public static final String PROFIT = "profit";
	
	/**
	 * 查询参数：beforeDays
	 */
	public static final String BEFOREDAYS = "beforeDays";
	
	/**
	 * 查询参数：afterDays
	 */
	public static final String AFTERDAYS = "afterDays";
	
	/**
	 * 查询参数：minDate
	 */
	public static final String MINDATE = "minDate";
	
	/**
	 * 查询参数：maxDate
	 */
	public static final String MAXDATE = "maxDate";
	
	/**
	 * 查询参数：startTime
	 */
	public static final String STARTTIME = "startTime";
	
	/**
	 * 查询参数：endTime
	 */
	public static final String ENDTIME = "endTime";
	
	/**
	 * 运算符：>=
	 */
	public static final String OPERATOR_FLAG = ">=";
	
	/**
	 * 查询参数：findNull
	 */
	public static final String FINDNULL = "findNull";
	
	/**
	 * 查询参数：IS NULL
	 */
	public static final String ISNULL = "IS NULL";
	
	/**
	 * 浮动盈利
	 */
	public static final String FLOATPROFIT = "floatProfit";
	
	/**
	 * 浮动亏损
	 */
	public static final String FLOATLOSS = "floatLoss";
	
	/**
	 * 风险金
	 */
	public static final String RISK = "risk";
	
	/**
	 * 查询参数：findOrderby
	 */
	public static final String FINDORDERBY = "findOrderby";
	
	/**
	 * 查询参数：orderby
	 */
	public static final String ORDERBY = "orderby";
	
	/**
	 * 排序顺序：逆序
	 */
	public static final String DESC = "desc";
	
	/**
	 * result
	 */
	public static final String RESULT = "result";
	
	/**
	 * 查询参数：isValid
	 */
	public static final String ISVALID = "isValid";
	
	/**
	 * 查询参数：reportDate
	 */
	public static final String REPORTDATE = "reportDate";
	
	/**
	 * 查询参数：dealTime
	 */
	public static final String DEALTIME = "dealTime";
	
	public static final String ID = "id";

	public static  final String FUNDINOUT_FILE_HEAD = "XH_CRJ";

	public static  final String TRADE_FILE_HEAD = "XH_TRN";

	public static  final String FILE_TYPE_TXT = ".txt";
	/**
	 * 模糊查询默认限制条数
	 */
	public static final int LIMIT_NUMBER_DEFAULT = 10;
	
	/**
	 * 模糊查询最大限制条数
	 */
	public static final int MAX_LIMIT_NUMBER = 20;
	
	public static final BigDecimal MAX_VALUE_OF_LONG = new BigDecimal(Long.MAX_VALUE);
    public static final BigDecimal MIN_VALUE_OF_LONG = new BigDecimal(Long.MIN_VALUE);
    
    public static final BigDecimal MAX_VALUE_OF_INTEGER = new BigDecimal(Integer.MAX_VALUE);
    public static final BigDecimal MIN_VALUE_OF_INTEGER = new BigDecimal(Integer.MIN_VALUE);
    
    /**
     * 初始密码
     */
    public static final String PASSWORD ="123456" ;
    
    /**
     * 金额小数位
     */
	public static final int DIVISOR_FOR_MONEY = 100;
	
	/**
	 * 重量小数位
	 */
    public static final int DIVISOR_FOR_WEIGHT = 1000;
    
    /**
     * 汇率小数位
     */
    public static final int DIVISOR_FOR_EXCHANGE_RATE = 10000;
    
    /**
     * 比率手续费单位，按照正常比率计算，如10000表示万分比，默认为百万分比
     */
    public static final int DIVISOR_FOR_RATIO = 1000000;
    
    /**
     * 固定手续费单位，1表示为分，每比分小一位，单位要*10，如单位为厘时，只应该为10，默认值100。
     */
    public static long FIXED_UNIT = 100;
}
