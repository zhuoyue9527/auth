package com.afis.cloud.auth.controller.cust;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.protocol.cust.request.BaseOnlineUserRequest;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.utils.SessionUtil;
import com.afis.web.modal.UserDetails;

public class AbstractOnlineUserController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected AuthCacheUtil authCacheUtil;

	protected void checkOnlineInput(BaseOnlineUserRequest loginRequest) throws AuthenticationException {
		if (loginRequest.getLoginKey() == null || loginRequest.getLoginKey().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"loginKey not exits");
			exception.setDesc("loginKey");
			throw exception;
		}
		if (loginRequest.getAppCode() == null || loginRequest.getAppCode().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"appCode not exits");
			exception.setDesc("appCode");
			throw exception;
		}
	}

	/**
	 * 根据appCode获取缓存中的Application信息
	 * 
	 * @param appCode
	 * @return
	 * @throws CommonException
	 */
	protected ApplicationModel checkApplication(String appCode) throws CommonException {
		ApplicationModel application = authCacheUtil.getApplicationFromRedis(appCode);

		if (application == null || !CommonConstants.Status.VALID.getKey().equals(application.getStatus())) {
			SessionUtil.addDebugLog(logger, "应用未注册或应用状态非正常");
			throw new CommonException(CommonException.ANY_DESC, "应用未注册或应用状态非正常");
		}
		return application;
	}

	/**
	 * 根据loginKey获取登录帐号
	 * 
	 * @param loginKey
	 * @return
	 * @throws AuthenticationException
	 */
	protected UserDetails getUserDetails(String loginKey) throws AuthenticationException {
		UserDetails userDetails = authCacheUtil.getOnlineUserFromRedis(loginKey);
		if (userDetails == null) {
			throw new AuthenticationException(AuthenticationException.NOT_LOGIN);
		}
		return userDetails;
	}
}
