package com.afis.web.auth.controller.admin;


import com.afis.web.annotation.AfisAuthentication;
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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/7 10:20
 */
@RestController
@Api("应用管理")
@RequestMapping("/auth/admin/applications")
public class AdminApplicationController extends AbstractController {

    @Autowired
    private WebProperties webProperties;

    @GetMapping
    @AfisAuthentication
    @ApiOperation("应用列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "start", value = "起始条数", dataType = "int", required = true, defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "int", required = true),
            @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "String"),
            @ApiImplicitParam(name = "startDay", value = "开始日期", dataType = "String"),
            @ApiImplicitParam(name = "endDay", value = "结束日期", dataType = "String")})
    public WebResponse getApplications(HttpServletRequest request,
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

    @GetMapping("/limit")
    @AfisAuthentication
    @ApiOperation("查询应用")
    @ApiImplicitParams({@ApiImplicitParam(name = "limit", value = "条数", dataType = "Integer"),
            @ApiImplicitParam(name = "appName", value = "应用名称", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "应用状态", dataType = "String")})
public WebResponse getApplications(HttpServletRequest request) throws WebBusinessException {
        // 转译get请求中文
        String param = ValidateScreenUtil.getStringDecode(request);
        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
            webProperties.getLogin().getCloudUrl() + request.getRequestURI()+"?" + param, null);
        return response;
        }

    @GetMapping("/{id}")
    @AfisAuthentication
    @ApiOperation("根据userId查询应用列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "用户Id", dataType = "Long")})
    public WebResponse getApplicationsByUserId(HttpServletRequest request,@PathVariable Long id) throws WebBusinessException {
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

    @GetMapping("/key")
    @AfisAuthentication
    @ApiOperation("获取密钥")
    public WebResponse getApplicationsKey(HttpServletRequest request) throws WebBusinessException {
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI(), null);
        return response;
    }

}
