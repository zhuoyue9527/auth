package com.afis.cloud.utils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.afis.cloud.exception.CommonException;
import com.afis.utils.Converter;
import com.afis.utils.DateUtils;

public class BizHelper {

	public final static int PREMIUM_MULTIPLY = 100;

	public final static int SQL_IN_LIMIT = 1000;

	public static void handleDBException(SQLException e, String... desc) throws SQLException, CommonException {
		String temp = e.getMessage();
		int index = temp.toUpperCase().indexOf("ORA-");
		if (index != -1) {
			// 唯一键冲突
			if (temp.substring(index + 4, index + 9).equals("00001")) {
				CommonException e2 = new CommonException(CommonException.CANNOT_DUPLIATED);
				e2.setDesc(desc[0]);
				throw e2;
			}
			// 违反完整约束条件 - 未找到父项关键字
			if (temp.substring(index + 4, index + 9).equals("02291")) {
				CommonException e2 = new CommonException(CommonException.NOT_EXISTS);
				if (desc.length > 1) {
					e2.setDesc(desc[1]);
				}
				throw e2;
			}
			// 违反完整约束条件 (用户名.约束名) - 已找到子记录
			if (temp.substring(index + 4, index + 9).equals("02292")) {
				CommonException e3 = new CommonException(CommonException.BIND_CANNOT_DELETED);
				if (desc.length > 2) {
					e3.setDesc(desc[2], desc[3]);
				}
				throw e3;
			}
			// 字段长度超长
			if (temp.substring(index + 4, index + 9).equals("12899")) {
				throw new CommonException(CommonException.CHARACTER_TOO_LONG);
			}
		}
		throw e;
	}

	/**
	 * 数据库超长异常
	 * 
	 * @author zhuzhenghua
	 * @date 2018年8月24日 下午4:19:07
	 *
	 * @param e
	 * @param desc
	 * @throws CommonException
	 * @throws SQLException
	 */
	public static void outOfLengthSqlException(SQLException e, String desc) throws CommonException, SQLException {
		if (e != null) {
			String errorNumber = e.getMessage().toUpperCase();
			// ORA-12899: 列 的值太大 (实际值: 54, 最大值: 50) ;插入的数据长度超出字段的设置长度
			if(errorNumber.indexOf("ORA-12899") > -1){
				CommonException c = new CommonException(CommonException.INPUT_IS_TOO_LONG);
				c.setDesc(desc);
				throw c;
			}
			// ORA-01704：字符串文字太长;直接拼接的sql中,单引号内的字符超过了4000,采取绑定变量的方式不会出现这个错误
			if(errorNumber.indexOf("ORA-01704") > -1){
				CommonException c = new CommonException(CommonException.INPUT_IS_TOO_LONG);
				c.setDesc(desc);
				throw c;
			}
			// ORA-01461 : 仅能绑定要插入 LONG 列的 LONG 值;实际要插入的值超过VARCHAR2允许的最大长度4000时,oracle自动将该字段值转化为Long类型，然后提示插入操作失败
			if(errorNumber.indexOf("ORA-01461") > -1){
				CommonException c = new CommonException(CommonException.INPUT_IS_TOO_LONG);
				c.setDesc(desc);
				throw c;
			}
		}
		throw e;
	}

	/**
	 * 获取两个日期之间的日期
	 *
	 * @param start
	 *            开始日期
	 * @param end
	 *            结束日期
	 * @param needWeekend
	 *            0-不包含周末 1-包含周末
	 * @return 日期集合
	 */
	public static List<Date> getBetweenDates(Date start, Date end, String needWeekend) {
		List<Date> result = new ArrayList<Date>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);

		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);

		while (tempStart.before(tempEnd)) {
			if ("0".equals(needWeekend)) {
				if (tempStart.get(Calendar.DAY_OF_WEEK) != 7 && tempStart.get(Calendar.DAY_OF_WEEK) != 1) {
					result.add(tempStart.getTime());
				}
			} else {
				result.add(tempStart.getTime());
			}
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}

		// 不包含周末并且结束日期是周末时，不加，其余情况都加
		if (!((tempEnd.get(Calendar.DAY_OF_WEEK) == 7 || tempEnd.get(Calendar.DAY_OF_WEEK) == 1)
				&& "0".equals(needWeekend))) {
			result.add(tempEnd.getTime());
		}

		return result;
	}

	/**
	 * 处理：字符串为null或者"null"时，返回""
	 * 
	 * @author zhuzhenghua
	 * @date 2017年11月10日 下午2:03:01
	 *
	 * @param value
	 * @return
	 */
	public static String dNullForString(String value) {
		if (value == null || DataConstans.STRING_NULL.equalsIgnoreCase(value)) {
			return DataConstans.STRING_EMPTY;
		}
		return value.trim();
	}

	public static Date getTodayDate() throws ParseException {
		String dateStr = getToday();
		return DateUtils.parse(dateStr, DataConstans.YYYYMMDD);
	}

	/**
	 * 获得当天年月日 格式:yyyyMMdd
	 * 
	 * @return
	 */
	public static String getToday() {
		Date date = new Date();
		return DateUtils.format(date, DataConstans.YYYYMMDD);
	}

	/**
	 * 缩小100倍，返回double
	 * 
	 * @param premiumLimit
	 * @return
	 */
	public static double premiumLimitNarrow(Long premiumLimit) {
		return 1D * premiumLimit / PREMIUM_MULTIPLY;
	}

	/**
	 *
	 * @param premiumLimit
	 * @return
	 */
	public static double premiumLimitDouble(Long premiumLimit) {
		return 1D * premiumLimit;
	}

	/**
	 * 放大100倍，返回Long
	 * 
	 * @param premiumLimit
	 * @return
	 */
	public static Long premiumLimitEnlarge(double premiumLimit) {
		return Converter.getLongRoundPrimitive(premiumLimit * PREMIUM_MULTIPLY);
	}

	public static String getValidString(String str) {
		if (str == null || "null".equalsIgnoreCase(str)) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 所传时间当天最后时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date convertEndDate(Date date) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1); // 把日期往后增加一天.整数往后推,负数往前移动
		long time = calendar.getTimeInMillis() - 1;
		return new Date(time);
	}

	/**
	 * 比较日期(忽略时间)
	 * 
	 * @param d1
	 * @param d2
	 * @return 相同返回true, 不相同返回false
	 */
	public static boolean compareDate(Date d1, Date d2) {

		// d1不是null, d2是null
		if (d1 != null && d2 == null) {
			return false;
		}
		// d1是null, d2不是null
		if (d1 == null && d2 != null) {
			return false;
		}
		// d1不等于d2
		if (d1 != null && d2 != null && !DateUtils.format(d1, DataConstans.YYYYMMDD).equals(DateUtils.format(d2, DataConstans.YYYYMMDD))) {
			return false;
		}
		return true;
	}

	/**
	 * 格式化字符串，返回日期
	 * 
	 * @author zhuzhenghua
	 * @date 2017年11月10日 下午3:26:23
	 *
	 * @param value
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String value, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(value);
	}

	/**
	 * 格式化日期，返回字符串
	 * 
	 * @author zhuzhenghua
	 * @date 2017年11月10日 下午3:33:53
	 *
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static String formatDate(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		return DataConstans.STRING_EMPTY;
	}

	/**
	 * 格式化日期，返回日期
	 *
	 * @author zhuzhenghua
	 * @date 2017年11月13日 上午10:10:59
	 *
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parseFormatDate(Date date, String format) throws ParseException {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(sdf.format(date));
		}
		return null;
	}

	/**
	 * 计算数值的负值
	 * 
	 * @author zhuzhenghua
	 * @date 2018年8月2日 上午10:48:36
	 *
	 * @param value
	 * @return
	 */
	public static long getNagtiveValue(long value) {
		return DataConstans.NEGATIVE_ONE * value;
	}

	/**
	 * List分段工具
	 * 
	 * @param source
	 *            被分组的List
	 * @param n
	 *            分成几组
	 * @return
	 */
	public static <T> List<List<T>> splitList(List<T> source, int n) {
		List<List<T>> result = new ArrayList<List<T>>();
		int remaider = source.size() % n; // (先计算出余数)
		int number = source.size() / n; // 然后是商
		int offset = 0;// 偏移量
		for (int i = 0; i < n; i++) {
			List<T> value = null;
			if (remaider > 0) {
				value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			result.add(value);
		}
		return result;
	}
}
