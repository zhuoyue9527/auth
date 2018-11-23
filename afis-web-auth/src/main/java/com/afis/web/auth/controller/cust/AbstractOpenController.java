package com.afis.web.auth.controller.cust;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.web.auth.config.AuthConstants;
import com.afis.web.controller.AbstractController;
import com.afis.web.utils.SessionUtil;

public class AbstractOpenController extends AbstractController{

	@Autowired
	private RedisTemplate redisTemplate;

	@Value("${customer.encrypt.valid.time}")
	private int encryptValidTime;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 校验url是否有效
	 * 
	 * @param appCode
	 * @param time
	 * @param redirect_url
	 * @param encrypt
	 * @return
	 */
	protected boolean checkEncrypt(String appCode, long time, String redirect_url, String encrypt) {
		ApplicationModel application = getApplication(appCode);
		if (application == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("can not find application from app_code:{}", appCode);
			}
			return false;
		}

		return checkTimeAndEncrypt(time, redirect_url, application.getKey(), encrypt);
	}
	
	/**
	 * 根据appCode获取应用信息
	 * @param appCode
	 * @return
	 */
	protected ApplicationModel getApplication(String appCode) {
		 return (ApplicationModel) redisTemplate.opsForHash().get(AuthConstants.APPLICTION,
					appCode);
	}

	private boolean checkTimeAndEncrypt(long time, String redirectUrl, String appKey, String encrypt) {
		// 校验time是否超时
		if (System.currentTimeMillis() - time > encryptValidTime * 60 * 1000) {
			if (logger.isDebugEnabled()) {
				logger.debug("connection time out！{}", time);
			}
			return false;
		}

		// 校验加密结果与传过来的encrypt是否一致
		return SessionUtil.getEncrypt(time, redirectUrl, appKey).equals(encrypt);
	}
}
