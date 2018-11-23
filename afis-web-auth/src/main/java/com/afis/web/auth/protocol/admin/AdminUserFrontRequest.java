package com.afis.web.auth.protocol.admin;

import com.afis.web.model.DefaultCloudRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:新增用户请求参数(界面--->管理端)
 * @Author: lizheng
 * @Date: 2018/11/2 13:50
 */
@ApiModel("注册用户请求对象")
public class AdminUserFrontRequest extends DefaultCloudRequest {


    @ApiModelProperty(name = "userAccount", position = 0, required = true, dataType = "string", value = "登录账号")
    private String userAccount;
    @ApiModelProperty(name = "userName", position = 1, required = true, dataType = "string", value = "账号名称")
    private String userName;
    @ApiModelProperty(name = "password", position = 2, required = true, dataType = "string", value = "密码")
    private String password;
    @ApiModelProperty(name = "mobile", position = 3, required = false, dataType = "string", value = "联系手机")
    private String mobile;
    @ApiModelProperty(name = "clientType", position = 4, required = true, dataType = "string", value = "类型")
    private String clientType;
    @ApiModelProperty(name = "registerAppId", position = 5, required = true, dataType = "Long", value = "注册应用")
    private Long registerAppId;
    @ApiModelProperty(name = "remark", position = 6, required = false, dataType = "string", value = "备注")
    private String remark;
    @ApiModelProperty(name = "id", position = 7, required = false, dataType = "Long", value = "ID")
    private Long id;
    @ApiModelProperty(name = "operateAppId", position = 9, required = true, dataType = "Long", value = "操作应用")
    private Long operateAppId;

    public Long getOperateAppId() {
        return operateAppId;
    }

    public void setOperateAppId(Long operateAppId) {
        this.operateAppId = operateAppId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public Long getRegisterAppId() {
        return registerAppId;
    }

    public void setRegisterAppId(Long registerAppId) {
        this.registerAppId = registerAppId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AdminUserFrontRequest{" +
                "userAccount='" + userAccount + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", clientType='" + clientType + '\'' +
                ", registerAppId=" + registerAppId +
                ", remark='" + remark + '\'' +
                ", id=" + id +
                ", operateAppId=" + operateAppId +
                '}';
    }
}
