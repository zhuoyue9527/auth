package com.afis.web.auth.controller.open;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.web.auth.controller.cust.AbstractOpenController;
import com.afis.web.auth.protocol.open.request.AbstractInterfaceRequest;
import com.afis.web.auth.protocol.open.request.DefaultInterfaceWeb2CloudRequest;
import com.afis.web.auth.protocol.open.request.UserCust2WebRequest;
import com.afis.web.auth.protocol.open.request.UserWeb2CloudRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.OkHttpUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth/v1/users")
@Api("用户接口-用户维护")
public class UserController extends AbstractOpenController {

	@Autowired
	private WebProperties webProperties;

	@PostMapping
	@ApiOperation("用户添加")
	public WebResponse addUsers(HttpServletRequest request, @RequestBody UserCust2WebRequest userRequest)
			throws WebBusinessException {
		// 非空校验
		checkInput(userRequest);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPostToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new UserWeb2CloudRequest(userRequest));
		return response;
	}

	@PatchMapping("/{userId}")
	@ApiOperation("用户编辑")
	public WebResponse editUsers(@PathVariable Long userId, HttpServletRequest request,
			@RequestBody UserCust2WebRequest userRequest) throws WebBusinessException {
		// check id parameter
		checkIdParameter(userId);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new UserWeb2CloudRequest(userRequest));
		return response;
	}

	private void checkIdParameter(@PathVariable Long id) throws AuthenticationException {
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"id not exits");
			exception.setDesc("id");
			throw exception;
		}
	}

	@GetMapping("/{userId}")
	@ApiOperation("用户查看")
	public WebResponse getUsersById(HttpServletRequest request, @PathVariable Long userId, @RequestParam String token,
			@RequestParam String appCode) throws WebBusinessException {
		// check id parameter
		checkIdParameter(userId);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webGetToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI() + "?" + request.getQueryString(),
				null);
		return response;
	}

	@PatchMapping("/frozen/{userId}")
	@ApiOperation("用户冻结")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long") })
	public WebResponse frozenUser(HttpServletRequest request, @PathVariable Long userId,
			@RequestBody AbstractInterfaceRequest param) throws WebBusinessException {
		// check id parameter
		checkIdParameter(userId);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new DefaultInterfaceWeb2CloudRequest(param));
		return response;
	}

	@PatchMapping("/thaw/{userId}")
	@ApiOperation("用户解冻")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long") })
	public WebResponse thawUsers(@PathVariable Long userId, HttpServletRequest request,
			@RequestBody AbstractInterfaceRequest param) throws WebBusinessException {
		// check id parameter
		checkIdParameter(userId);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new DefaultInterfaceWeb2CloudRequest(param));
		return response;
	}

	@PatchMapping("/cancel/{userId}")
	@ApiOperation("用户注销")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "String") })
	public WebResponse cancelUsers(@PathVariable Long userId, HttpServletRequest request,
			@RequestBody AbstractInterfaceRequest param) throws WebBusinessException {
		// check id parameter
		checkIdParameter(userId);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new DefaultInterfaceWeb2CloudRequest(param));
		return response;
	}

	@PatchMapping("/unlock/{userId}")
	@ApiOperation("用户解锁")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "String") })
	public WebResponse unlockUsers(@PathVariable Long userId, HttpServletRequest request,
			@RequestBody AbstractInterfaceRequest param) throws WebBusinessException {
		// check id parameter
		checkIdParameter(userId);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new DefaultInterfaceWeb2CloudRequest(param));
		return response;
	}

	private void checkInput(UserCust2WebRequest userRequest) throws AuthenticationException {
		// check userAccount parameter
		if (userRequest.getUserAccount() == null || userRequest.getUserAccount().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"userAccount not exits");
			exception.setDesc("userAccount");
			throw exception;
		}
		// check username parameter
		if (userRequest.getUserName() == null || userRequest.getUserName().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"username not exits");
			exception.setDesc("userName");
			throw exception;
		}
		// check password parameter
		if (userRequest.getPassword() == null || userRequest.getPassword().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"password not exits");
			exception.setDesc("password");
			throw exception;
		}
	}
}
