package com.afis.web.auth.controller.admin;

import com.afis.web.annotation.AfisAuthentication;
import com.afis.web.auth.protocol.admin.AdminOnlineUserFrontRequest;
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
@RequestMapping("/auth/admin/onlineUsers")
public class OnlineUsersController extends AbstractController {

	@Autowired
	private WebProperties webProperties;

	@GetMapping
	@AfisAuthentication
	@ApiOperation("在线用户列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "start", value = "起始条数", dataType = "int", required = true, defaultValue = "0"),
			@ApiImplicitParam(name = "limit", value = "每页条数", dataType = "int", required = true),
			@ApiImplicitParam(name = "userAccount", value = "用户账号", dataType = "String"),
			@ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String"),
			@ApiImplicitParam(name = "clientType", value = "类型", dataType = "String"),
			@ApiImplicitParam(name = "registerTimeStart", value = "开始注册时间", dataType = "String"),
			@ApiImplicitParam(name = "registerTimeEnd", value = "结束注册时间", dataType = "String"),
			@ApiImplicitParam(name = "registerAPP", value = "注册应用", dataType = "Long"),
			@ApiImplicitParam(name = "lastLoginTimeStart", value = "最后登录时间", dataType = "String"),
			@ApiImplicitParam(name = "lastLoginTimeEnd", value = "最后登录时间", dataType = "String") })
	public WebResponse getOnlineUsers(HttpServletRequest request, @RequestParam Integer start, @RequestParam Integer limit)
			throws WebBusinessException {
		// 非空校验
		ValidateScreenUtil.checkPageNum(start, limit);
		String param = ValidateScreenUtil.getStringDecode(request);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webGetToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI() + "?" + param, null);
		return response;
	}

	@DeleteMapping
	@AfisAuthentication
	@ApiOperation("在线用户踢出")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户ID", dataType = "Long", required = true)})
	public WebResponse deleteOnlineUsers(HttpServletRequest request, @RequestBody AdminOnlineUserFrontRequest param)
			throws WebBusinessException {
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webDeleteToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), param);
		return response;
	}


}
