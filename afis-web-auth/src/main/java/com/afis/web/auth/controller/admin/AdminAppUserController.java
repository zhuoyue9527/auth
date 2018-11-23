package com.afis.web.auth.controller.admin;

import com.afis.web.annotation.AfisAuthentication;
import com.afis.web.auth.protocol.admin.AdminUserAppFrontRequest;
import com.afis.web.auth.protocol.admin.AdminUserFrontRequest;
import com.afis.web.config.WebProperties;
import com.afis.web.controller.AbstractController;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.HttpOrignalUtil;
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
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/10 10:41
 */
@RestController
@Api("用户授权管理")
@RequestMapping("/auth/admin/appUsers")
public class AdminAppUserController extends AbstractController {

    @Autowired
    private WebProperties webProperties;

    @PatchMapping("/add/{userId}")
    @AfisAuthentication
    @ApiOperation("用户授权新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appIdList", value = "应用列表", dataType = "List"),
            @ApiImplicitParam(name = "userId", value = "用户ID", dataType = "String")})
    public WebResponse addAppUsers(@PathVariable Long userId,HttpServletRequest request, @RequestBody AdminUserAppFrontRequest userRequest) throws WebBusinessException {
        // check appIdList parameter
        if (userRequest.getAppIdList() == null || userRequest.getAppIdList().size() == 0) {
            AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
                    "应用列表不能为空");
            throw exception;
        }
        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webPatchToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);

        return response;
    }


    @GetMapping
    @AfisAuthentication
    @ApiOperation("用户授权列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "start", value = "起始条数", dataType = "int", required = true, defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "int", required = true),
            @ApiImplicitParam(name = "userAccount", value = "用户账号", dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String"),
            @ApiImplicitParam(name = "userAppId", value = "应用ID", dataType = "Long")})
    public WebResponse getUsers(HttpServletRequest request,
                                @RequestParam(required = true) Integer start,
                                @RequestParam(required = true) Integer limit) throws WebBusinessException {
        //非空校验
        ValidateScreenUtil.checkPageNum(start, limit);
        String param = ValidateScreenUtil.getStringDecode(request);
        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI() + "?" + param, null);
        return response;
    }

    @GetMapping("/userApps/{userId}")
    @AfisAuthentication
    @ApiOperation("用户应用列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "userId", value = "用户ID", dataType = "Long")})
    public WebResponse getUserApps(HttpServletRequest request,@PathVariable Long userId) throws WebBusinessException {
        // check userId parameter
        if (userId == null) {
            AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
                    "用户ID不能为空");
            throw exception;
        }
        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI(), null);
        return response;
    }

    @PatchMapping("/edit/{id}")
    @AfisAuthentication
    @ApiOperation("用户授权编辑")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "ID", dataType = "long", required = true)})
    public WebResponse deleteAppUserById(HttpServletRequest request,@PathVariable Long id
            ,@RequestBody AdminUserAppFrontRequest userRequest) throws WebBusinessException {

        // check id parameter
        if (id == null) {
            AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
                    "用户ID不能为空");
            throw exception;
        }
        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webPatchToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI(), userRequest);
        return response;
    }


}
