package com.afis.web.auth.protocol.admin;

import com.afis.web.model.DefaultCloudRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @Description:应用授权添加请求参数
 * @Author: lizheng
 * @Date: 2018/11/2 13:50
 */
@ApiModel("应用授权添加请求对象")
public class AdminApplicationRequest extends DefaultCloudRequest {



    @ApiModelProperty(name = "id", position = 0, required = true, dataType = "Long", value = "ID")
    private Long id;
    @ApiModelProperty(name = "appCode", position = 1, required = true, dataType = "string", value = "APP授权码")
    private String appCode;
    @ApiModelProperty(name = "appName", position = 2, required = true, dataType = "string", value = "APP名称")
    private String appName;
    @ApiModelProperty(name = "key", position = 3, required = true, dataType = "string", value = "密钥")
    private String key;
    @ApiModelProperty(name = "status", position = 3, required = false, dataType = "string", value = "状态")
    private String status;
    @ApiModelProperty(name = "urlCallback", position = 4, required = false, dataType = "string", value = "返回跳转URL")
    private String urlCallback;
    @ApiModelProperty(name = "logoPath", position = 5, required = false, dataType = "string", value = "logo路径")
    private String logoPath;
    @ApiModelProperty(name = "remark", position = 6, required = false, dataType = "string", value = "备注")
    private String remark;
    @ApiModelProperty(name = "operateAppId", position = 7, required = false, dataType = "string", value = "操作应用")
    private Long operateAppId;
    @ApiModelProperty(name = "functionList", position = 9, required = false, dataType = "List", value = "功能列表")
    private List<Long> functionList;

    @ApiModelProperty(name = "virtualPath", position = 10, required = false, dataType = "string", value = "临时路径")
    private String virtualPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlCallback() {
        return urlCallback;
    }

    public void setUrlCallback(String urlCallback) {
        this.urlCallback = urlCallback;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getOperateAppId() {
        return operateAppId;
    }

    public void setOperateAppId(Long operateAppId) {
        this.operateAppId = operateAppId;
    }

    public List<Long> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<Long> functionList) {
        this.functionList = functionList;
    }

    public String getVirtualPath() {
        return virtualPath;
    }

    public void setVirtualPath(String virtualPath) {
        this.virtualPath = virtualPath;
    }

    @Override
    public String toString() {
        return "AdminApplicationRequest{" +
                "id=" + id +
                ", appCode='" + appCode + '\'' +
                ", appName='" + appName + '\'' +
                ", key='" + key + '\'' +
                ", status='" + status + '\'' +
                ", urlCallback='" + urlCallback + '\'' +
                ", logoPath='" + logoPath + '\'' +
                ", remark='" + remark + '\'' +
                ", operateAppId=" + operateAppId +
                ", functionList=" + functionList +
                ", virtualPath='" + virtualPath + '\'' +
                '}';
    }
}
