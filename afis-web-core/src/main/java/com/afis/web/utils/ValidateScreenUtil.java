package com.afis.web.utils;

import com.afis.utils.Converter;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.CommonException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
			}
		}
		return true;
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
	
	public static String getValidString(String str) {
		if (str == null || "null".equalsIgnoreCase(str)) {
			return "";
		}
		return str.trim();
	}
	
	/**
	 * 放大100倍，返回Long
	 * 
	 * @param premiumLimit
	 * @return
	 */
	public static Long premiumLimitEnlarge(double premiumLimit) {
		return Converter.getLongRoundPrimitive(premiumLimit * 100);
	}

	public static String getStringDecode(HttpServletRequest request) {
		String param = request.getQueryString();
		if (param != null) {
			if (!"".equals(request.getQueryString()) && request.getQueryString().getBytes().length > 0) {
				try {
					param = URLDecoder.decode(request.getQueryString(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return param;
	}

	public static void checkPageNum(Integer start, Integer limit) throws AuthenticationException {
		if (start == null || "".equals(start)) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"页码不能为空");
			exception.setDesc("页码不能为空");
			throw exception;
		}
		if (limit == null || "".equals(limit)) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"条数不能为空");
			exception.setDesc("条数不能为空");
			throw exception;
		}
	}

}
