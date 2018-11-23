package com.afis.web.auth.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afis.ExceptionResultCode;
import com.afis.web.annotation.AfisAuthentication;
import com.afis.web.auth.protocol.admin.AdminApplicationRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.controller.AbstractController;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.HttpOrignalUtil;
import com.afis.web.utils.OkHttpUtil;
import com.afis.web.utils.SessionUtil;
import com.afis.web.utils.ValidateScreenUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/7 10:20
 */
@RestController
@Api("应用授权管理")
@RequestMapping("/auth/admin/appFunctions")
public class AdminAppFunctionController extends AbstractController {

	@Autowired
	private WebProperties webProperties;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping
	@AfisAuthentication
	@ApiOperation("应用授权列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "start", value = "起始条数", dataType = "int", required = true, defaultValue = "0"),
			@ApiImplicitParam(name = "limit", value = "每页条数", dataType = "int", required = true),
			@ApiImplicitParam(name = "appId", value = "操作应用", dataType = "Long"),
			@ApiImplicitParam(name = "appName", value = "应用名称", dataType = "String"),
			@ApiImplicitParam(name = "startDay", value = "开始日期", dataType = "String"),
			@ApiImplicitParam(name = "endDay", value = "结束日期", dataType = "String"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "String") })
	public WebResponse getApplications(HttpServletRequest request, @RequestParam Integer start,
			@RequestParam Integer limit) throws WebBusinessException {
		// 非空校验
		ValidateScreenUtil.checkPageNum(start, limit);
		String param = ValidateScreenUtil.getStringDecode(request);
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webGetToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI() + "?" + param, null);
		return response;
	}

	@GetMapping("/{id}")
	@AfisAuthentication
	@ApiOperation("应用授权查看")
	public WebResponse getApplications(HttpServletRequest request, @PathVariable Long id) throws WebBusinessException {
		// 校验ID
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不能为空");
			throw exception;
		}
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webGetToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), null);
		return response;
	}

	@PostMapping
	@ApiOperation("应用授权新增")
	@ApiImplicitParams({ @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "String"),
			@ApiImplicitParam(name = "urlCallback", value = "回调URL", dataType = "String"),
			@ApiImplicitParam(name = "logoPath", value = "logo路径", dataType = "String"),
			@ApiImplicitParam(name = "mobile", value = "联系手机", dataType = "String"),
			@ApiImplicitParam(name = "operateAppId", value = "操作应用", dataType = "Long"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "String"),
			@ApiImplicitParam(name = "functionList", value = "功能列表", dataType = "List") })
	public WebResponse postAddApplication(HttpServletRequest request, @RequestBody AdminApplicationRequest userRequest)
			throws WebBusinessException, IOException {
		// 非空校验
		checkInput(userRequest);
		userRequest.setOperateAppId(webProperties.getSystem().getAppId());
		// 临时路径
		userRequest.setVirtualPath(webProperties.getUploader().getVirtualPath());
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPostToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);
		logger.debug("response:{}", response);
		// 返回成功需要移动图片
		moveFile(userRequest, response);
		return response;
	}

	private void moveFile(AdminApplicationRequest userRequest, WebResponse response) throws IOException {
		if (response.getResponseCode() == ExceptionResultCode.SUCCESS) {
			String imgUrl = response.getData().toString();
			if (imgUrl.length() > 0
					&& !userRequest.getLogoPath().startsWith(webProperties.getUploader().getVirtualPath())) {
				// 获取服务器存储附件的路径
				String destPath = webProperties.getUploader().getDestinationPath();
				imgUrl = imgUrl.substring(0, imgUrl.lastIndexOf("/")).replace(webProperties.getUploader().getVirtualPath(),
						"");
				logger.debug("imgUrl:{}", imgUrl);
				// 获取返回的存储路径
				String fileStorePath = destPath + imgUrl;
				// 转存图片
				File temp = new File(fileStorePath);
				if (!temp.exists()) {
					temp.mkdirs();
				}
				// 存储名称
				String logoPath = userRequest.getLogoPath();
				String fileStoreName = logoPath.substring(logoPath.lastIndexOf("/"));
				logger.debug("sourse:{}", destPath + logoPath);
				logger.debug("target:{}", fileStorePath + fileStoreName);
				Files.move(Paths.get(destPath + logoPath), Paths.get(fileStorePath + fileStoreName));
			}
		}
	}

	@PatchMapping("/{id}")
	@AfisAuthentication
	@ApiOperation("应用授权编辑")
	@ApiImplicitParams({ @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "String"),
			@ApiImplicitParam(name = "urlCallback", value = "回调URL", dataType = "String"),
			@ApiImplicitParam(name = "appCode", value = "授权码", dataType = "String"),
			@ApiImplicitParam(name = "status", value = "状态", dataType = "String"),
			@ApiImplicitParam(name = "logoPath", value = "logo路径", dataType = "String"),
			@ApiImplicitParam(name = "mobile", value = "联系手机", dataType = "String"),
			@ApiImplicitParam(name = "operateAppId", value = "操作应用", dataType = "Long"),
			@ApiImplicitParam(name = "remark", value = "备注", dataType = "String"),
			@ApiImplicitParam(name = "functionList", value = "功能列表", dataType = "List") })
	public WebResponse patchEditApplication(@PathVariable Long id, HttpServletRequest request,
			@RequestBody AdminApplicationRequest userRequest) throws WebBusinessException, IOException {
		// 校验ID
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不能为空");
			exception.setDesc("ID不能为空");
			throw exception;
		}
		// 非空校验
		checkInput(userRequest);
		userRequest.setOperateAppId(webProperties.getSystem().getAppId());
		userRequest.setVirtualPath(webProperties.getUploader().getVirtualPath());
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);
		// 返回成功需要移动图片
		moveFile(userRequest, response);
		return response;
	}

	@PatchMapping("/cancel/{id}")
	@AfisAuthentication
	@ApiOperation("应用授权注销")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "应用ID", dataType = "Long") })
	public WebResponse patchCancelApplication(@PathVariable Long id, HttpServletRequest request)
			throws WebBusinessException {
		// 校验ID
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不能为空");
			exception.setDesc("ID不能为空");
			throw exception;
		}
		AdminApplicationRequest userRequest = new AdminApplicationRequest();
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);

		return response;
	}

	@PatchMapping("/recover/{id}")
	@AfisAuthentication
	@ApiOperation("应用授权恢复")
	@ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "应用ID", dataType = "Long") })
	public WebResponse patchRecoverApplication(@PathVariable Long id, HttpServletRequest request)
			throws WebBusinessException {
		// 校验ID
		if (id == null) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"ID不能为空");
			exception.setDesc("ID不能为空");
			throw exception;
		}
		AdminApplicationRequest userRequest = new AdminApplicationRequest();
		// 请求cloud的服务
		WebResponse response = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);

		return response;
	}

	private void checkInput(AdminApplicationRequest userRequest) throws AuthenticationException {
		// check appName parameter
		if (userRequest.getAppName() == null || userRequest.getAppName().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"应用名称不能为空");
			exception.setDesc("应用名称不能为空");
			throw exception;
		}
	}

}
