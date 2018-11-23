package com.afis.web.utils;

import com.afis.utils.Converter;
import com.afis.utils.MD5;
import com.afis.web.modal.UserDetails;
import com.afis.web.modal.WebResponse;

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
	 */
	public static Long getOperator(UserDetails userDetails) {
		if (userDetails != null) {
			return Converter.getLong(userDetails.getId());
		}
		return null;
	}

	/**
	 * 生成返回WebResponse对象
	 * 
	 * @param respCode
	 * @param respDesc
	 * @param result
	 * @return
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

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static long getSystemTime() {
		return System.currentTimeMillis();
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
}
