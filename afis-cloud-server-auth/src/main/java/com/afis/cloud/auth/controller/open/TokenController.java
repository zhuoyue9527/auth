package com.afis.cloud.auth.controller.open;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.TicketModel;
import com.afis.cloud.auth.model.TokenModel;
import com.afis.cloud.auth.model.protocol.open.request.CustomerTokenRequest;
import com.afis.cloud.auth.model.protocol.open.response.CustomerTokenResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.Api;

/**
 * 用户端的获取token的接口
 * 
 * @author Chengen
 *
 */
@RestController
@RequestMapping("/auth/v1/access_token")
@Api("根据ticket获取token的接口")
public class TokenController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@PostMapping
	public WebResponse postToken(HttpServletRequest request, @RequestBody CustomerTokenRequest tokenRequest)
			throws AuthenticationException, CommonException {
		// 校验传入的参数
		checkInput(tokenRequest);
		// 校验ticket是否有效
		TicketModel ticket = checkTicket(tokenRequest);
		// 生成token
		TokenModel token = getToken(ticket);
		// 删除ticket
		authCacheUtil.removeTicketFromRedis(ticket.getTicket());

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, new CustomerTokenResponse(token));
	}

	private TokenModel getToken(TicketModel ticket) {
		TokenModel token = authCacheUtil.getTokenFromRedis(ticket.getAppCode(), ticket.getUserName());
		if (token == null) {
			token = new TokenModel();
			token.setAppCode(ticket.getAppCode());
			token.setAppId(ticket.getAppId());
			token.setCreateTime(System.currentTimeMillis());
			token.setLastUpdateTime(System.currentTimeMillis());
			token.setToken(SessionUtil.getRandomUUID());
			token.setUserId(ticket.getUserId());
			token.setUserName(ticket.getUserName());
			// 将token保存到redis中
			authCacheUtil.addTokenToRedis(token);
		}
		return token;
	}

	private void checkInput(CustomerTokenRequest tokenRequest) throws AuthenticationException {
		if (tokenRequest.getAppCode() == null || tokenRequest.getAppCode().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST);
			exception.setDesc("appCode");
			throw exception;
		}
		if (tokenRequest.getTicket() == null || tokenRequest.getTicket().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST);
			exception.setDesc("ticket");
			throw exception;
		}
	}

	private TicketModel checkTicket(CustomerTokenRequest tokenRequest) throws CommonException, AuthenticationException {

		ApplicationModel application = authCacheUtil.getApplicationFromRedis(tokenRequest.getAppCode().trim());

		if (application == null || !CommonConstants.Status.VALID.getKey().equals(application.getStatus())) {
			SessionUtil.addDebugLog(logger, "应用未注册或应用状态非正常");
			throw new CommonException(CommonException.ANY_DESC, "应用未注册或应用状态非正常");
		}

		TicketModel ticketModel = authCacheUtil.getTicketFromRedis(tokenRequest.getTicket());

		if (ticketModel == null || !ticketModel.getAppCode().equals(tokenRequest.getAppCode())) {
			throw new AuthenticationException(AuthenticationException.AUTH_GRANT_TOKEN_TICKET_INVALID);
		}

		return ticketModel;
	}
}
