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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/7 10:20
 */
@RestController
@Api("功能列表")
@RequestMapping("/auth/admin/functions")
public class AdminFunctionController  extends AbstractController {

    @Autowired
    private WebProperties webProperties;

    @GetMapping
    @AfisAuthentication
    @ApiOperation("功能列表分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "start", value = "起始条数", dataType = "int", required = true, defaultValue = "0"),
            @ApiImplicitParam(name = "limit", value = "每页条数", dataType = "int", required = true),
            @ApiImplicitParam(name = "type", value = "类型", dataType = "String")})
    public WebResponse getFunctions(HttpServletRequest request,
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

    @GetMapping("/byGroups")
    @AfisAuthentication
    @ApiOperation("功能列表分组查询")
    public WebResponse getFunctionsByGroup(HttpServletRequest request) throws WebBusinessException {
        // 请求cloud的服务
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI(), null);
        return response;
    }
    @GetMapping("/byAppId/{id}")
    @AfisAuthentication
    @ApiOperation("功能列表分组查询")
    public WebResponse getFunctionsByAppId(HttpServletRequest request) throws WebBusinessException {
        // 请求cloud的服务
        String param = ValidateScreenUtil.getStringDecode(request);
        WebResponse response = OkHttpUtil.webGetToCloudService(request,
                webProperties.getLogin().getCloudUrl() + request.getRequestURI() + "?" + param, null);
        return response;
    }


}
