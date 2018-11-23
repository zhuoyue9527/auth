package com.afis.web.auth.controller.admin;

import com.afis.web.config.WebProperties;
import com.afis.web.controller.AbstractController;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.CommonException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.OkHttpUtil;
import com.afis.web.utils.ValidateScreenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.sql.CommonDataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/8 16:41
 */
@RestController
@RequestMapping("/auth/admin/logs")
@Api("日志管理")
public class AdminLogController extends AbstractController {
    @GetMapping("/loginLogs")
    @ApiOperation("登录日志列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "start", value = "起始条数", dataType = "int", required = true, defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "int", required = true),
            @ApiImplicitParam(name = "userName", value = "用户名称", dataType = "String"),
            @ApiImplicitParam(name = "loginAppId", value = "登录应用ID", dataType = "Long"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "String"),
            @ApiImplicitParam(name = "systemInfo", value = "操作系统", dataType = "String"),
            @ApiImplicitParam(name = "browserInfo", value = "浏览器", dataType = "String"),
            @ApiImplicitParam(name = "startDay", value = "开始日期", dataType = "String"),
            @ApiImplicitParam(name = "endDay", value = "结束日期", dataType = "String")})
    public WebResponse getLoginLogs(HttpServletRequest request,
                                    @RequestParam(required = false) String userName,
                                    @RequestParam(required = false) Long loginAppId,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(required = false) String systemInfo,
                                    @RequestParam(required = false) String browserInfo,
                                    @RequestParam(required = false) String startDay,
                                    @RequestParam(required = false) String endDay,
                                    @RequestParam(required = true) Integer start,
                                    @RequestParam(required = true) Integer limit) throws WebBusinessException {
        //非空校验
        checkParam(start, limit);
        // 转译请求参数
        String param = ValidateScreenUtil.getStringDecode(request);
        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI()+"?" + param, null);
        return response;
    }



    @Autowired
    private WebProperties webProperties;

    @GetMapping("/operateLogs")
    @ApiOperation("操作日志列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "start", value = "起始条数", dataType = "int", required = true, defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "int", required = true),
            @ApiImplicitParam(name = "operateAppId", value = "操作应用ID", dataType = "Long"),
            @ApiImplicitParam(name = "functionId", value = "功能ID", dataType = "Long"),
            @ApiImplicitParam(name = "startDay", value = "开始日期", dataType = "String"),
            @ApiImplicitParam(name = "endDay", value = "结束日期", dataType = "String"),
            @ApiImplicitParam(name = "status", value = "状态", dataType = "String"),
            @ApiImplicitParam(name = "operator", value = "操作员", dataType = "String")})
    public WebResponse getOperateLogs(HttpServletRequest request,
                                       @RequestParam(required = false) Long operateAppId,
                                       @RequestParam(required = false) String functionName,
                                       @RequestParam(required = false) String operator,
                                       @RequestParam(required = false) String startDay,
                                       @RequestParam(required = false) String endDay,
                                       @RequestParam(required = false) String status,
                                       @RequestParam(required = true) Integer start,
                                       @RequestParam(required = true) Integer limit) throws WebBusinessException {
        //非空校验
        ValidateScreenUtil.checkPageNum(start, limit);
        String param = ValidateScreenUtil.getStringDecode(request);

        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI()+"?" + param, null);
        return response;
    }

    @GetMapping("/systemInfos")
    @ApiOperation("操作系统列表")
    public WebResponse getSystemInfos(HttpServletRequest request) throws WebBusinessException {
        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI(), null);
        return response;
    }
    @GetMapping("/browserInfos")
    @ApiOperation("浏览器列表")
    public WebResponse getBrowserInfos(HttpServletRequest request) throws WebBusinessException {
        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI(), null);
        return response;
    }











    private void checkParam(Integer start, Integer limit) throws AuthenticationException {
        if (start == null || "".equals(start)) {
            AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
                    "页码不能为空");
            throw exception;
        }
        if (limit == null || "".equals(limit)) {
            AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
                    "条数不能为空");
            throw exception;
        }
    }

}
