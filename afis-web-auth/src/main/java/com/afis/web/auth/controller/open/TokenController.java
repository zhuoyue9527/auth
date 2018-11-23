package com.afis.web.auth.controller.open;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.web.auth.controller.cust.AbstractOpenController;
import com.afis.web.auth.protocol.open.request.CustTokenWeb2CloudRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.CommonException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.OkHttpUtil;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/auth/v1/access_token")
@Api("根据ticket获取token的接口")
public class TokenController extends AbstractOpenController {

	@Autowired
	private WebProperties webProperties;

	@GetMapping
	public WebResponse getToken(HttpServletRequest request, HttpServletResponse response, @RequestParam String ticket,
			@RequestParam String app_code, @RequestParam long time, @RequestParam String encrypt,
			@RequestParam String redirect_url) throws WebBusinessException {
		if (checkEncrypt(app_code, time, redirect_url, encrypt)) {// 验证有效
			return this.sendCloudGetToken(request, app_code, ticket);
		} else {
			throw new CommonException(CommonException.ANY_DESC, "请求无效");
		}
	}

	private WebResponse sendCloudGetToken(HttpServletRequest request, String app_code, String ticket)
			throws WebBusinessException {
		CustTokenWeb2CloudRequest cloudRequest = createCloudRequest(app_code, ticket);
		return OkHttpUtil.webPostToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), cloudRequest);
	}

	private CustTokenWeb2CloudRequest createCloudRequest(String app_code, String ticket) {
		CustTokenWeb2CloudRequest request = new CustTokenWeb2CloudRequest();
		request.setAppCode(app_code);
		request.setTicket(ticket);
		return request;
	}
}
