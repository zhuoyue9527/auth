package com.afis.cloud.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.afis.cloud.exception.CommonException;

import javax.servlet.http.HttpServletRequest;

public class ValidateScreenUtil {
	/**
	 * 参数不能为空
	 * 
	 * @param object
	 * @return
	 * @throws CommonException
	 */
	public static boolean validateObject(Object object, String name) throws CommonException {
		if (object == null) {
			CommonException e = new CommonException(CommonException.NULL_NOT_ALLOWED);
			e.setDesc(name);
			throw e;
		} else {
			if (object instanceof String) {
				if ("".equals(object)) {
					CommonException e = new CommonException(CommonException.NULL_NOT_ALLOWED);
					e.setDesc(name);
					throw e;
				}
			} else if (object instanceof List<?>) {
				if (((List<?>) object).size() == 0) {
					CommonException e = new CommonException(CommonException.NULL_NOT_ALLOWED);
					e.setDesc(name);
					throw e;
				}
			}
		}
		return true;
	}

	/**
	 * 判断对象非空
	 * 
	 * @param object
	 * @param name
	 * @return
	 * @throws CommonException
	 */
	public static Object validateObjectNotNull(Object object, String name) throws CommonException {
		if (object == null) {
			CommonException e = new CommonException(CommonException.NOT_EXISTS);
			e.setDesc(name);
			throw e;
		}
		return object;
	}

	/**
	 * 对象不存在
	 * 
	 * @author zhuzhenghua
	 * @date 2018年7月31日 上午10:59:48
	 *
	 * @param object
	 * @param name
	 * @throws CommonException
	 */
	public static void objectNotExsit(Object object, String name) throws CommonException {
		if (object == null) {
			CommonException e = new CommonException(CommonException.NOT_EXISTS);
			e.setDesc(name);
			throw e;
		} else {
			if (object instanceof List<?>) {
				if (((List<?>) object).size() < DataConstans.ONE) {
					CommonException e = new CommonException(CommonException.NOT_EXISTS);
					e.setDesc(name);
					throw e;
				}
			}
		}
	}

	/**
	 * 判断list：为空或者长度小于1
	 * 
	 * @author zhuzhenghua
	 * @date 2017年11月10日 下午1:33:08
	 *
	 * @param value
	 * @return
	 */
	public static boolean vNullForList(List<?> value) {
		if (value == null || value.size() < DataConstans.ONE) {
			return true;
		}
		return false;
	}

	/**
	 * 更新并验证数据是否过期
	 * 
	 * @author zhuzhenghua
	 * @date 2018年7月31日 下午2:40:10
	 *
	 * @param n
	 * @throws CommonException
	 */
	public static void vDataExpired(int n) throws CommonException {
		if (n < DataConstans.ONE) {
			throw new CommonException(CommonException.DATA_EXPIRED);
		}
	}

	/**
	 * 字符串trim
	 * 
	 * @param str
	 * @return
	 */
	public static String getValidString(String str) {
		if (str == null || "null".equalsIgnoreCase(str)) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 
	 * @param str
	 * @param name
	 * @throws CommonException
	 */
	public static Long validateNumberCast(String str, String name) throws CommonException {
		try {
			if ("".equals(str.trim()) || str == null) {
				return null;
			} else {
				if (str.length() > 19) {
					CommonException exc = new CommonException(CommonException.INPUT_IS_TOO_LONG);
					exc.setDesc(name);
					throw exc;
				}
				return Long.parseLong(str);
			}
		} catch (NumberFormatException e) {
			CommonException exc = new CommonException(CommonException.NUMBER_CAST_ERROR);
			exc.setDesc(name);
			throw exc;
		}
	}

	public static Double validateDoubleCast(String str, String name) throws CommonException {
		try {
			if ("".equals(str.trim()) || str == null) {
				return null;
			} else {
				return Double.parseDouble(str);
			}
		} catch (NumberFormatException e) {
			CommonException exc = new CommonException(CommonException.NUMBER_CAST_ERROR);
			exc.setDesc(name);
			throw exc;
		}
	}

	/**
	 * 模糊查询限制条数
	 * 
	 * @param limitNumber
	 * @return
	 */
	public static int limitNumber(String limitNumber) {
		if (limitNumber == null || limitNumber.equals("")) {
			return DataConstans.LIMIT_NUMBER_DEFAULT;
		} else {
			int number = Integer.parseInt(limitNumber);
			if (number > DataConstans.MAX_LIMIT_NUMBER) {
				return DataConstans.MAX_LIMIT_NUMBER;
			}
		}
		return Integer.parseInt(limitNumber);
	}

	public static boolean vNotNullForString(String value) {
		if (value != null && !"".equals(value.trim()) && !"null".equals(value.toLowerCase())) {
			return true;
		}
		return false;
	}

	public static boolean vNullForString(String value) {
		if (value == null || DataConstans.STRING_EMPTY.equals(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 检查long
	 * 
	 * @author zhuzhenghua
	 * @date 2018年8月1日 上午9:51:48
	 *
	 * @param str
	 *            原始值
	 * @param name
	 *            数值译名
	 * @param mustPositive
	 *            是否必须为正数
	 * @param allowZero
	 *            是否允许为0
	 * @param notEmpty
	 *            是否必填
	 * @param maxLength
	 *            最大长度
	 * @param divisor
	 *            小数位除数，用于报错时转换为正常的数值，例如金额为100，重量为1000
	 * @return
	 * @throws CommonException
	 */
	public static Long validateLong(String str, String name, boolean mustPositive, boolean allowZero, boolean notEmpty,
			Integer maxLength, Integer divisor) throws CommonException {
		if (notEmpty) {
			validateObject(str, name);
		}
		try {
			if (vNotNullForString(str)) {
				str = str.trim();
				if (maxLength != null && str.length() > maxLength.intValue()) {
					CommonException exc = new CommonException(CommonException.INPUT_IS_TOO_LONG);
					exc.setDesc(name);
					throw exc;
				}
				BigDecimal val = new BigDecimal(str);
				// 检查是否超过long的最大值
				if (val.compareTo(DataConstans.MAX_VALUE_OF_LONG) == 1) {
					String max = "";
					if (divisor != null && divisor.intValue() != 0) {
						max = DataConstans.MAX_VALUE_OF_LONG.divide(new BigDecimal(divisor)).toString();
					}
					CommonException exc = new CommonException(CommonException.OUT_OF_MAX);
					exc.setDesc(name, max);
					throw exc;
				}
				// 检查是否超过long的最小值
				if (val.compareTo(DataConstans.MIN_VALUE_OF_LONG) == -1) {
					String min = "";
					if (divisor != null && divisor.intValue() != 0) {
						min = DataConstans.MIN_VALUE_OF_LONG.divide(new BigDecimal(divisor)).toString();
					}
					CommonException exc = new CommonException(CommonException.OUT_OF_MIN);
					exc.setDesc(name, min);
					throw exc;
				}
				long n = val.longValue();
				if (!allowZero && n == 0) {
					CommonException exc = new CommonException(CommonException.NOT_ALLOW_ZERO);
					exc.setDesc(name);
					throw exc;
				} else if (mustPositive && n < 0) {
					CommonException exc = new CommonException(CommonException.MUST_POSITIVE);
					exc.setDesc(name);
					throw exc;
				}
				return n;
			} else {
				return null;
			}
		} catch (NumberFormatException e) {
			CommonException exc = new CommonException(CommonException.NUMBER_CAST_ERROR);
			exc.setDesc(name);
			throw exc;
		}
	}

	/**
	 * 检查int
	 * 
	 * @author zhuzhenghua
	 * @date 2018年8月1日 上午9:51:48
	 *
	 * @param str
	 *            原始值
	 * @param name
	 *            数值译名
	 * @param mustPositive
	 *            是否必须为正数
	 * @param allowZero
	 *            是否允许为0
	 * @param notEmpty
	 *            是否必填
	 * @param maxLength
	 *            最大长度
	 * @param divisor
	 *            小数位除数，用于报错时转换为正常的数值，例如金额为100，重量为1000
	 * @return
	 * @throws CommonException
	 */
	public static Integer validateInteger(String str, String name, boolean mustPositive, boolean allowZero,
			boolean notEmpty, Integer maxLength, Integer divisor) throws CommonException {
		if (notEmpty) {
			validateObject(str, name);
		}
		try {
			if (vNotNullForString(str)) {
				str = str.trim();
				if (maxLength != null && str.length() > maxLength.intValue()) {
					CommonException exc = new CommonException(CommonException.INPUT_IS_TOO_LONG);
					exc.setDesc(name);
					throw exc;
				}
				BigDecimal val = new BigDecimal(str);
				// 检查是否超过int的最大值
				if (val.compareTo(DataConstans.MAX_VALUE_OF_INTEGER) == 1) {
					String max = "";
					if (divisor != null && divisor.intValue() != 0) {
						max = DataConstans.MAX_VALUE_OF_INTEGER.divide(new BigDecimal(divisor)).toString();
					}
					CommonException exc = new CommonException(CommonException.OUT_OF_MAX);
					exc.setDesc(name, max);
					throw exc;
				}
				// 检查是否超过int的最小值
				if (val.compareTo(DataConstans.MIN_VALUE_OF_INTEGER) == -1) {
					String min = "";
					if (divisor != null && divisor.intValue() != 0) {
						min = DataConstans.MIN_VALUE_OF_INTEGER.divide(new BigDecimal(divisor)).toString();
					}
					CommonException exc = new CommonException(CommonException.OUT_OF_MIN);
					exc.setDesc(name, min);
					throw exc;
				}
				int n = val.intValue();
				if (!allowZero && n == 0) {
					CommonException exc = new CommonException(CommonException.NOT_ALLOW_ZERO);
					exc.setDesc(name);
					throw exc;
				} else if (mustPositive && n < 0) {
					CommonException exc = new CommonException(CommonException.MUST_POSITIVE);
					exc.setDesc(name);
					throw exc;
				}
				return n;
			} else {
				return null;
			}
		} catch (NumberFormatException e) {
			CommonException exc = new CommonException(CommonException.NUMBER_CAST_ERROR);
			exc.setDesc(name);
			throw exc;
		}
	}

	/**
	 * 检查Date
	 * 
	 * @author zhuzhenghua
	 * @date 2018年8月1日 上午11:23:21
	 *
	 * @param str
	 * @param name
	 * @param isMandatory
	 *            是否必填
	 * @param format
	 *            格式化
	 * @param additional
	 *            追加格式化
	 * @return
	 * @throws CommonException
	 */
	public static Date validateDate(String str, String name, boolean notEmpty, String format, String additional)
			throws CommonException {
		if (notEmpty) {
			validateObject(str, name);
		}
		if (vNullForString(str)) {
			return null;
		} else {
			try {
				if (additional != null) {
					str = str.trim() + additional;
				}
				return BizHelper.parseDate(str.trim(), format);
			} catch (Exception e) {
				CommonException c = new CommonException(CommonException.DATE_FORMAT_ERROR);
				c.setDesc(name);
				throw c;
			}
		}
	}

}
