package com.afis.web.auth.controller.admin;

import javax.servlet.http.HttpServletRequest;

import com.afis.web.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.web.annotation.AfisAuthentication;
import com.afis.web.auth.protocol.admin.AdminConfigFrontRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.UserDetails;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.OkHttpUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/13 9:19
 */
@RestController
@Api("系统配置管理")
@RequestMapping("/auth/admin/UserTableConfig")
public class UserTableConfigController extends AbstractController {

	@Autowired
	private WebProperties webProperties;

	@PostMapping
	@AfisAuthentication
	@ApiOperation("系统列编辑")
	@ApiImplicitParams({ @ApiImplicitParam(name = "columns", value = "列字段", dataType = "String"),
			@ApiImplicitParam(name = "uid", value = "对象ID", dataType = "String") })
	public WebResponse insertOrUpdateTableConfig(HttpServletRequest request,
			@RequestBody AdminConfigFrontRequest configRequest) throws WebBusinessException {
		// 非空校验
		checkInput(configRequest);
		
		configRequest.setAppId(webProperties.getSystem().getAppId());
		// 请求cloud的服务
		return OkHttpUtil.webPostToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), configRequest);
	}

	@GetMapping
	@ApiOperation("系统列查看")
	@AfisAuthentication
	@ApiImplicitParams({ @ApiImplicitParam(name = "uid", value = "对象ID", dataType = "String") })
	public WebResponse getTableConfig(HttpServletRequest request, @RequestParam String uid)
			throws WebBusinessException {
		UserDetails userDetails = (UserDetails) request.getAttribute(UserDetails.class.getName());
		Long userId = userDetails.getId();
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webGetToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI() + "?uid=" + uid + "&operateId="
						+ userId + "&appId=" + webProperties.getSystem().getAppId(),
				null);

		return response;
	}

	private void checkInput(AdminConfigFrontRequest configRequest) throws AuthenticationException {
		// check Columns parameter
		if (configRequest.getColumns() == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"Columns不能为空");
			throw exception;
		}
		// check 对象ID parameter
		if (configRequest.getUid() == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"对象ID不能为空");
			throw exception;
		}
	}
}
