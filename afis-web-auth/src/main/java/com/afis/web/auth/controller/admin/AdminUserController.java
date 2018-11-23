package com.afis.web.auth.controller.admin;

import com.afis.web.annotation.AfisAuthentication;
import com.afis.web.auth.protocol.admin.AdminUserFrontRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.controller.AbstractController;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.OkHttpUtil;
import com.afis.web.utils.ValidateScreenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:用户管理
 * @Author: lizheng
 * @Date: 2018/11/1 16:22
 */
@RestController
@Api("用户管理")
@RequestMapping("/auth/admin/users")
public class AdminUserController  extends AbstractController {

	@Autowired
	private WebProperties webProperties;

	@GetMapping
	@AfisAuthentication
	@ApiOperation("用户分页列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "start", value = "起始条数", dataType = "int", required = true, defaultValue = "0"),
			@ApiImplicitParam(name = "limit", value = "每页条数", dataType = "int", required = true),
			@ApiImplicitParam(name = "userAccount", value = "用户账号", dataType = "String"),
			@ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String"),
			@ApiImplicitParam(name = "clientType", value = "类型", dataType = "String"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "String"),
			@ApiImplicitParam(name = "registerTimeStart", value = "开始注册时间", dataType = "String"),
			@ApiImplicitParam(name = "registerTimeEnd", value = "结束注册时间", dataType = "String"),
			@ApiImplicitParam(name = "registerAPP", value = "注册应用", dataType = "Long"),
			@ApiImplicitParam(name = "startDay", value = "开始时间", dataType = "String"),
			@ApiImplicitParam(name = "endDay", value = "结束时间", dataType = "String") })
	public WebResponse getUsers(HttpServletRequest request, @RequestParam Integer start, @RequestParam Integer limit)
			throws WebBusinessException {
		// 非空校验
		ValidateScreenUtil.checkPageNum(start, limit);
		String param = ValidateScreenUtil.getStringDecode(request);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webGetToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI() + "?" + param, null);
		return response;
	}

	@GetMapping("/vague")
	@AfisAuthentication
	@ApiOperation("模糊查询用户列表")
	@ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String")})
	public WebResponse getUsersByUserName(HttpServletRequest request, @RequestParam String userName)
			throws WebBusinessException {
		// 非空校验
		String param = ValidateScreenUtil.getStringDecode(request);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webGetToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI() + "?" + param, null);
		return response;
	}

	@PostMapping
	@AfisAuthentication
	@ApiOperation("用户新增")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userAccount", value = "用户账号", dataType = "String"),
			@ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String"),
			@ApiImplicitParam(name = "clientType", value = "类型", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "String"),
			@ApiImplicitParam(name = "mobile", value = "联系手机", dataType = "String"),
			@ApiImplicitParam(name = "registerAppId", value = "注册应用", dataType = "Long"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "String") })
	public WebResponse addUsers(HttpServletRequest request, @RequestBody AdminUserFrontRequest userRequest)
			throws WebBusinessException {
		// 非空校验
		checkInput(userRequest);
		// 绑定登陆IP和操作应用ID
		userRequest.setOperateAppId(webProperties.getSystem().getAppId());
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPostToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);
		return response;
	}

	@PatchMapping("/{id}")
	@AfisAuthentication
	@ApiOperation("用户编辑")
	@ApiImplicitParams({ @ApiImplicitParam(name = "userAccount", value = "用户账号", dataType = "String"),
			@ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String"),
			@ApiImplicitParam(name = "clientType", value = "类型", dataType = "String"),
			@ApiImplicitParam(name = "password", value = "密码", dataType = "String"),
			@ApiImplicitParam(name = "mobile", value = "联系手机", dataType = "String"),
			@ApiImplicitParam(name = "registerAppId", value = "注册应用", dataType = "Long"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "String"),
			@ApiImplicitParam(name = "id", value = "用户ID", dataType = "Long") })
	public WebResponse editUsers(@PathVariable Long id, HttpServletRequest request,
			@RequestBody AdminUserFrontRequest userRequest) throws WebBusinessException {
		// check id parameter
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不能为空");
			throw exception;
		}
		userRequest.setOperateAppId(webProperties.getSystem().getAppId());
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);

		return response;
	}

	@GetMapping("/{id}")
	@AfisAuthentication
	@ApiOperation("用户查看")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "ID", dataType = "Long", required = true) })
	public WebResponse getUsersById(HttpServletRequest request, @PathVariable Long id) throws WebBusinessException {

		// check id parameter
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不能为空");
			throw exception;
		}
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webGetToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI() + "?" + request.getQueryString(),
				null);
		return response;
	}

	@PatchMapping("/frozen/{id}")
	@AfisAuthentication
	@ApiOperation("用户冻结")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Long") })
	public WebResponse frozenUsers(@PathVariable Long id, HttpServletRequest request) throws WebBusinessException {
		// check id parameter
		AdminUserFrontRequest userRequest = getAdminUserFrontRequest(id);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);
		return response;
	}

	@PatchMapping("/thaw/{id}")
	@AfisAuthentication
	@ApiOperation("用户解冻")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", dataType = "Long") })
	public WebResponse thawUsers(@PathVariable Long id, HttpServletRequest request) throws WebBusinessException {
		AdminUserFrontRequest userRequest = getAdminUserFrontRequest(id);

		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);
		return response;
	}

	private AdminUserFrontRequest getAdminUserFrontRequest(@PathVariable Long id) throws AuthenticationException {
		// check id parameter
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不能为空");
			throw exception;
		}
		AdminUserFrontRequest userRequest = new AdminUserFrontRequest();
		userRequest.setOperateAppId(webProperties.getSystem().getAppId());
		return userRequest;
	}

	@PatchMapping("/cancel/{id}")
	@AfisAuthentication
	@ApiOperation("用户注销")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", dataType = "String") })
	public WebResponse cancelUsers(@PathVariable Long id, HttpServletRequest request) throws WebBusinessException {
		AdminUserFrontRequest userRequest = getAdminUserFrontRequest(id);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);
		return response;
	}

	@PatchMapping("/unlock/{id}")
	@AfisAuthentication
	@ApiOperation("用户解锁")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "用户ID", dataType = "String") })
	public WebResponse unlockUsers(@PathVariable Long id, HttpServletRequest request) throws WebBusinessException {
		AdminUserFrontRequest userRequest = getAdminUserFrontRequest(id);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);
		return response;
	}

	private void checkInput(AdminUserFrontRequest userRequest) throws AuthenticationException {
		// check userAccount parameter
		if (userRequest.getUserAccount() == null || userRequest.getUserAccount().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"用户账号");
			throw exception;
		}
		// check username parameter
		if (userRequest.getUserName() == null || userRequest.getUserName().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"用户名不能为空");
			throw exception;
		}
		// check clientType parameter
		if (userRequest.getClientType() == null || userRequest.getClientType().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"类型不能为空");
			throw exception;
		}
		// check password parameter
		if (userRequest.getPassword() == null || userRequest.getPassword().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"密码不能为空");
			throw exception;
		}
		// check registerAppId parameter
		if (userRequest.getRegisterAppId() == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"注册应用ID不能为空");
			throw exception;
		}

	}
}
