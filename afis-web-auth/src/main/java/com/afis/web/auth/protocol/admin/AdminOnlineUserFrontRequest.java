package com.afis.web.auth.protocol.admin;

import com.afis.web.model.DefaultCloudRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description:新增用户请求参数(界面--->管理端)
 * @Author: lizheng
 * @Date: 2018/11/2 13:50
 */
@ApiModel("在线注册用户请求对象")
public class AdminOnlineUserFrontRequest extends DefaultCloudRequest {

    @ApiModelProperty(name = "userAccount", position = 0, required = true, dataType = "String", value = "用户账号")
    private String userAccount;
    @ApiModelProperty(name = "loginKey", position = 1, required = true, dataType = "String", value = "在线ID")
    private String loginKey;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    @Override
    public String toString() {
        return "AdminOnlineUserFrontRequest{" +
                "userAccount='" + userAccount + '\'' +
                ", loginKey='" + loginKey + '\'' +
                '}';
    }
}
