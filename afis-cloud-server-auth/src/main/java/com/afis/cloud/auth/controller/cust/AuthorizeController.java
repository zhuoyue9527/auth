package com.afis.cloud.auth.controller.cust;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.LoginManagements;
import com.afis.cloud.auth.business.store.impl.LoginManagementsImpl;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.TicketModel;
import com.afis.cloud.auth.model.protocol.cust.request.CustomerLoginRequest;
import com.afis.cloud.auth.model.protocol.cust.request.OnlineUserLoginRequest;
import com.afis.cloud.auth.model.protocol.cust.response.CustomerLoginResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;
import com.afis.web.modal.UserDetails;

import io.swagger.annotations.ApiOperation;

/**
 * 用户端的登录
 * 
 * @author Chengen
 *
 */
@RestController
@RequestMapping("/auth/authorize")
public class AuthorizeController extends AbstractOnlineUserController {

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Value("${login.fail.num}")
	private int failNum;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 认证提供的登录页的提交
	 * 
	 * @param app_code
	 * @param time
	 * @param encrypt
	 * @return
	 * @throws AuthenticationException
	 * @throws CommonException
	 * @throws SQLException
	 */
	@PostMapping
	@ApiOperation("输入用户名和密码登录")
	public WebResponse postAuthorize(@RequestBody CustomerLoginRequest loginRequest)
			throws AuthenticationException, CommonException, SQLException {
		// 非空校验
		checkInput(loginRequest);

		ApplicationModel application = checkApplication(loginRequest.getAppCode().trim());

		String redirectUrl = checkRedirectUrl(loginRequest, application);
		LoginManagements loginManagements = new LoginManagementsImpl(kafkaTemplate, authCacheUtil);
		UserDetails userDetails = loginManagements.customerLogin(loginRequest, application, failNum);
		userDetails.setLoginKey(SessionUtil.getRandomUUID());

		CustomerLoginResponse response = createCustomerLoginResponse(userDetails, redirectUrl, application,Boolean.TRUE);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, response);
	}

	@PatchMapping
	@ApiOperation("选择已登录帐号登录")
	public WebResponse patchAuthorize(@RequestBody OnlineUserLoginRequest loginRequest)
			throws AuthenticationException, CommonException, SQLException {
		// 非空校验
		checkOnlineInput(loginRequest);

		ApplicationModel application = checkApplication(loginRequest.getAppCode().trim());

		String redirectUrl = checkRedirectUrl(loginRequest, application);

		UserDetails userDetails = getUserDetails(loginRequest.getLoginKey());

		CustomerLoginResponse response = createCustomerLoginResponse(userDetails, redirectUrl, application,Boolean.FALSE);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, response);
	}

	private CustomerLoginResponse createCustomerLoginResponse(UserDetails userDetails, String redirectUrl,
			ApplicationModel application,boolean needAddOnlineUser) throws SQLException {
		CustomerLoginResponse response = new CustomerLoginResponse();
		response.setUserDetails(userDetails);
		response.setRedirectUrl(redirectUrl);
		response.setTicket(addOnlineUserToRedis(userDetails, application,needAddOnlineUser));
		return response;
	}

	private String addOnlineUserToRedis(UserDetails userDetails, ApplicationModel application,
			boolean needAddOnlineUser) throws SQLException {
		if(needAddOnlineUser) {
			authCacheUtil.addOnlineUserToRedis(userDetails, Boolean.FALSE);
		}
		
		return addTicketToRedis(userDetails, application);
	}

	private String addTicketToRedis(UserDetails userDetails, ApplicationModel application) {
		TicketModel model = new TicketModel();
		model.setAppId(application.getId());
		model.setAppCode(application.getAppCode());
		model.setTicket(SessionUtil.getRandomUUID());
		model.setCreateTime(System.currentTimeMillis());
		model.setUserId(userDetails.getId());
		model.setUserName(userDetails.getUserName());
		authCacheUtil.addTicketToRedis(model);

		return model.getTicket();
	}

	private String checkRedirectUrl(CustomerLoginRequest loginRequest, ApplicationModel application)
			throws AuthenticationException {
		String redirectUrl = loginRequest.getRedirectUrl();
		if (redirectUrl == null || redirectUrl.trim().length() == 0) {
			redirectUrl = application.getUrlCallback();
		}
		if (redirectUrl == null || redirectUrl.trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST);
			exception.setDesc("redirectUrl");
			throw exception;
		}

		return redirectUrl;
	}

	private String checkRedirectUrl(OnlineUserLoginRequest loginRequest, ApplicationModel application)
			throws AuthenticationException {
		String redirectUrl = loginRequest.getRedirectUrl();
		if (redirectUrl == null || redirectUrl.trim().length() == 0) {
			redirectUrl = application.getUrlCallback();
		}
		if (redirectUrl == null || redirectUrl.trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST);
			exception.setDesc("redirectUrl");
			throw exception;
		}

		return redirectUrl;
	}

	private void checkInput(CustomerLoginRequest loginRequest) throws AuthenticationException {
		if (loginRequest.getAppCode() == null || loginRequest.getAppCode().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST);
			exception.setDesc("appCode");
			throw exception;
		}
		if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST);
			exception.setDesc("userName");
			throw exception;
		}
		if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST);
			exception.setDesc("password");
			throw exception;
		}
	}
}
