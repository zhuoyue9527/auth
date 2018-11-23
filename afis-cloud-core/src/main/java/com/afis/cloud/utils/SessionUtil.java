package com.afis.cloud.utils;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import com.afis.cloud.common.protocol.AbstractProtocolConstants;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.WebResponse;
import com.afis.utils.Converter;
import com.afis.utils.MD5;
import com.afis.web.modal.UserDetails;

/**
 * Session 帮助类
 * 
 * @author Administrator
 *
 */
public class SessionUtil {

	/**
	 * 获取当前操作员
	 * 
	 * @param userDetails
	 * @return
	 * @throws CommonException
	 */
	public static Long getOperator(UserDetails userDetails) throws CommonException {
		return getOperator(userDetails, Boolean.FALSE);
	}

	/**
	 * 获取登录ip
	 * 
	 * @param userDetails
	 * @return
	 */
	public static String getLoginIp(UserDetails userDetails) {
		return userDetails.getSourceIp();
	}

	/**
	 * 获取当前操作员
	 * 
	 * @param userDetails
	 * @return
	 * @throws CommonException
	 */
	public static Long getOperator(UserDetails userDetails, Boolean notEmpty) throws CommonException {
		Long value = null;
		if (userDetails != null) {
			value = Converter.getLong(userDetails.getId());
		}
		if (notEmpty) {
			if (value == null) {
				CommonException e = new CommonException(CommonException.NULL_NOT_ALLOWED);
				e.setDesc(AbstractProtocolConstants.CommonProtocol.CURRENT_OPERATOR.getValue());
				throw e;
			}
		}
		return value;
	}

	/**
	 * 生成返回WebResponse对象
	 * 
	 * @param resDesc
	 *            返回消息提示
	 * @param result
	 *            返回数据对象
	 */
	public static WebResponse generateWebResponse(Integer respCode, String respDesc, Object result) {
		WebResponse webResponse = new WebResponse();
		if (respCode != null) {
			webResponse.setResponseCode(respCode);
		}
		if (respDesc != null && respDesc.trim().length() > 0) {
			webResponse.setResponseDesc(respDesc.trim());
		}
		if (result != null) {
			webResponse.setData(result);
		}
		return webResponse;
	}

	public static String validateString(String obj, String name, boolean notEmpty) throws CommonException {
		obj = ValidateScreenUtil.getValidString(obj);
		if (notEmpty) {
			ValidateScreenUtil.validateObject(obj, name);
		}
		return obj;
	}

	/**
	 * 取出并校验请求中的Integer参数
	 *
	 * @param webRequest
	 * @param protocol
	 * @param maxLength
	 * @param notEmpty
	 * @param mustPositive
	 *            是否必须大于0
	 * @param allowZero
	 * @param divisor
	 * @return
	 * @throws CommonException
	 */
	public static Integer getIntegerFromWebRequest(String value, String name, Integer maxLength, boolean notEmpty,
			boolean mustPositive, boolean allowZero, Integer divisor) throws CommonException {
		return ValidateScreenUtil.validateInteger(value, name, mustPositive, allowZero, notEmpty, maxLength, divisor);
	}

	/**
	 * 根据appkey+time+redirectUrl进行加密处理
	 * 
	 * @param time
	 * @param appKey
	 * @return
	 */
	public static String getEncrypt(long time, String redirectUrl, String appKey) {
		return MD5.code32(appKey + time + (redirectUrl == null ? "" : redirectUrl));
	}

	/**
	 * debug日志
	 * 
	 * @param logger
	 * @param format
	 * @param objects
	 */
	public static void addDebugLog(Logger logger, String format, Object... objects) {
		if (logger.isDebugEnabled()) {
			logger.debug(format, objects);
		}
	}

	/**
	 * 生成32位的随机数 全部大写
	 * 
	 * @return
	 */
	public static String getRandomUUID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 获取functionUrl
	 * 
	 * @param request
	 * @return
	 */
	public static String getFunctionUrl(HttpServletRequest request) {
		return request.getMethod() + request.getRequestURI();
	}
}
