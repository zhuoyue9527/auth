package com.afis.cloud.auth.model.protocol;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AdminOnlineUserFrontRequest extends DefaultRequest {


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
