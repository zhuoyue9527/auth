package com.afis.web.auth.controller.open;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.afis.web.auth.protocol.open.request.AbstractInterfaceRequest;
import com.afis.web.auth.protocol.open.request.DefaultInterfaceWeb2CloudRequest;
import com.afis.web.auth.protocol.open.request.UserAuthorizeCust2WebRequest;
import com.afis.web.auth.protocol.open.request.UserAuthorizeWeb2CloudRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.controller.AbstractController;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.OkHttpUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/16 16:06
 */
@RestController
@RequestMapping("/auth/v1/appUsers")
@Api("用户授权")
public class UserAuthorizeController extends AbstractController {

	@Autowired
	private WebProperties webProperties;

	@ApiOperation("用户授权查询")
	@GetMapping("/{id}")
	public WebResponse getAppUser(HttpServletRequest request, @PathVariable Long id)
			throws WebBusinessException, SQLException {
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webGetToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), null);
		return response;
	}

	@PostMapping("/{id}")
	@ApiOperation("用户授权添加")
	public WebResponse postAppUser(@PathVariable Long userId, HttpServletRequest request,
			@RequestBody UserAuthorizeCust2WebRequest userParam) throws WebBusinessException {
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPostToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new UserAuthorizeWeb2CloudRequest(userParam));
		return response;
	}

	@DeleteMapping("/{id}")
	@ApiOperation("用户授权删除")
	public WebResponse deleteAppUser(@PathVariable Long userId, HttpServletRequest request,
			@RequestBody AbstractInterfaceRequest userParam) throws WebBusinessException {
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webDeleteToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new DefaultInterfaceWeb2CloudRequest(userParam));
		return response;
	}
}
