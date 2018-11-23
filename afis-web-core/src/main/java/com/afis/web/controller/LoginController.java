package com.afis.web.controller;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.afis.ExceptionResultCode;
import com.afis.utils.VerificationCode;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.AuthProtocolConstants;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.OkHttpUtil;
import com.afis.web.utils.SessionUtil;

@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WebProperties webProperties;

	/**
	 * 认证服务的后台管理登录页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/auth/login", method = RequestMethod.GET)
	public String loginPage() {
		return "admin";
	}

	/**
	 * 应用的登录页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String custLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect(createLoginUrl());
		return null;
	}

	/**
	 * 生成验证码的代码
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/captcha.png", method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response) {
		VerificationCode.Code image = VerificationCode.getImage(5, webProperties.getSecurity().getCaptchaType(),
				new Font(webProperties.getSecurity().getCaptchaFontFamily(), Font.BOLD,
						webProperties.getSecurity().getCaptchaFontSize()),
				webProperties.getSecurity().getCaptchaHeight(), false);
		HttpSession session = request.getSession();
		session.setAttribute("LOGIN_CAPTCHA", image.getCode());
		ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
		try {
			ImageIO.write(image.getImage(), "JPEG", bout);
			byte[] data = bout.toByteArray();
			ServletOutputStream out = response.getOutputStream();
			out.write(data);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("send Login VerCode occur Error Exception is {}.", e);
		}
	}

	/**
	 * 回调url
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws WebBusinessException
	 */
	@RequestMapping(value = "/direct", method = RequestMethod.GET)
	@ResponseBody
	public WebResponse directUrl(HttpServletRequest request, @RequestParam String ticket)
			throws IOException, WebBusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("获取到的ticket:{}", ticket);
		}
		// 根据ticket向认证中心发起获取token的请求
		String tokenUrl = generateTokenUrl(ticket);
		WebResponse response = OkHttpUtil.webGetToCloudService(request, tokenUrl, null);
		if (logger.isDebugEnabled()) {
			logger.debug("获取token返回值:{}", response);
		}
		if (ExceptionResultCode.SUCCESS == response.getResponseCode()) {// 成功
			// response.getData()中包含了token以及在认证中心的帐号信息
			// 将token保存到cookie中，根据认证中心的帐号获取对应应用的帐号，自己做保存
		} else {// 失败
			if (logger.isDebugEnabled()) {

			}
		}
		return response;
	}

	/**
	 * /auth/token?type=token&ticket=[The_AUTHORIZATION_TICKET]&app_code=[YOUR_APP_CODE]&time=[毫秒数]&encrypt=[加密的结果]
	 * 
	 * @return
	 */
	private String generateTokenUrl(String ticket) {
		long time = SessionUtil.getSystemTime();
		StringBuffer sb = new StringBuffer();
		sb.append(webProperties.getAuth().getTokenUrl());
		sb.append("?").append(AuthProtocolConstants.TokenProtocol.TICKET.getKey()).append("=").append(ticket);
		sb.append("&").append(AuthProtocolConstants.TokenProtocol.APP_CODE.getKey()).append("=")
				.append(webProperties.getSystem().getAppCode());
		sb.append("&").append(AuthProtocolConstants.TokenProtocol.TIME.getKey()).append("=").append(time);
		sb.append("&").append(AuthProtocolConstants.TokenProtocol.ENCRYPT.getKey()).append("=").append(SessionUtil
				.getEncrypt(time, webProperties.getAuth().getRedirectUrl(), webProperties.getSystem().getAppKey()));
		sb.append("&").append(AuthProtocolConstants.TokenProtocol.REDIRECT_URL.getKey()).append("=")
				.append(webProperties.getAuth().getRedirectUrl());
		return sb.toString();
	}

	/**
	 * 生成跳转到认证登录页的url
	 * 认证登录url?type=ticket&app_code=[YOUR_APP_CODE]&time=[毫秒数]&encrypt=[加密的结果]&redirect_url=[YOUR_APP_DIRERCT_URL]
	 * 
	 * @return
	 */
	private String createLoginUrl() {
		long time = SessionUtil.getSystemTime();
		StringBuffer sb = new StringBuffer();
		sb.append(webProperties.getAuth().getLoginUrl());
		sb.append("?").append(AuthProtocolConstants.AuthorizeProtocol.APP_CODE.getKey()).append("=")
				.append(webProperties.getSystem().getAppCode());
		sb.append("&").append(AuthProtocolConstants.AuthorizeProtocol.TIME.getKey()).append("=").append(time);
		sb.append("&").append(AuthProtocolConstants.AuthorizeProtocol.ENCRYPT.getKey()).append("=").append(SessionUtil
				.getEncrypt(time, webProperties.getAuth().getRedirectUrl(), webProperties.getSystem().getAppKey()));
		sb.append("&").append(AuthProtocolConstants.AuthorizeProtocol.REDIRECT_URL.getKey()).append("=")
				.append(webProperties.getAuth().getRedirectUrl());
		return sb.toString();
	}
}
