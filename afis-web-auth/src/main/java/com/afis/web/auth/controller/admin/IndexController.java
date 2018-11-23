package com.afis.web.auth.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afis.web.annotation.AfisAuthentication;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.UserDetails;
import com.afis.web.model.DefaultCloudRequest;
import com.afis.web.security.inf.AuthenticationManager;
import com.afis.web.utils.OkHttpUtil;

import io.swagger.annotations.ApiOperation;

@Controller
public class IndexController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private WebProperties webProperties;

	public static final String CLOUD_LOGOUT_URL = "/auth/admin/authorize";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@AfisAuthentication
	@ApiOperation("首页")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		return "index";
	}

	@AfisAuthentication
	@RequestMapping("/logout")
	@ApiOperation("管理端登出")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws WebBusinessException {
		UserDetails userDetails = (UserDetails) request.getAttribute(UserDetails.class.getName());

		// 发送请求到cloud进行登出处理
		this.sendcloudLogout(request);

		authenticationManager.logoutSuccess(request, response, webProperties.getSecurity().getCookieName(),
				userDetails);
	}

	private void sendcloudLogout(HttpServletRequest request) throws WebBusinessException {
		OkHttpUtil.webDeleteToCloudService(request, webProperties.getLogin().getCloudUrl() + CLOUD_LOGOUT_URL,
				new DefaultCloudRequest());
	}
}
