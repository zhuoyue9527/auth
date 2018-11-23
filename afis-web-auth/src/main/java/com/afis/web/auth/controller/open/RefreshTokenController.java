package com.afis.web.auth.controller.open;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.web.auth.controller.cust.AbstractOpenController;
import com.afis.web.auth.protocol.open.request.CustRefreshTokenWeb2CloudRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.CommonException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.OkHttpUtil;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/auth/v1/refresh_token")
@Api("刷新token的时间")
public class RefreshTokenController extends AbstractOpenController {

	@Autowired
	private WebProperties webProperties;

	@GetMapping
	public WebResponse refreshToken(HttpServletRequest request, @RequestParam String token,
			@RequestParam String app_code, @RequestParam long time, @RequestParam String encrypt,
			@RequestParam String redirect_url) throws WebBusinessException {
		if (checkEncrypt(app_code, time, redirect_url, encrypt)) {// 验证有效
			return OkHttpUtil.webPostToCloudService(request,
					webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
					createWeb2CloudRequest(token, app_code));
		} else {
			throw new CommonException(CommonException.ANY_DESC, "请求无效");
		}

	}

	/**
	 * 生成请求cloud的对象
	 * 
	 * @param token
	 * @param app_code
	 * @return
	 */
	private CustRefreshTokenWeb2CloudRequest createWeb2CloudRequest(String token, String app_code) {
		CustRefreshTokenWeb2CloudRequest cloudRequest = new CustRefreshTokenWeb2CloudRequest();
		cloudRequest.setAppCode(app_code);
		cloudRequest.setToken(token);
		return cloudRequest;
	}

}
