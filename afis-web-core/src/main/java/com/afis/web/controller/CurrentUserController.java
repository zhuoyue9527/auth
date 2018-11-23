package com.afis.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.web.annotation.AfisAuthentication;
import com.afis.web.modal.UserDetails;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.SessionUtil;

@RestController
public class CurrentUserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/me")
	@AfisAuthentication
	public WebResponse queryCurrentUser(HttpServletRequest request) {
		logger.debug("获取当前用户信息");
		UserDetails userDetails = (UserDetails) request.getAttribute(UserDetails.class.getName());
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, userDetails);
	}
}
