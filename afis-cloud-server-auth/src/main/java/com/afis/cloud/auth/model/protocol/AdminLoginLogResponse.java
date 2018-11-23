package com.afis.cloud.auth.model.protocol;

import com.afis.cloud.entities.model.auth.LoginLog;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/8 17:05
 */
public class AdminLoginLogResponse extends LoginLog {


    private String loginAppName;//操作应用名称

    private String userName;//用户名称

    private String userAccount;//用户账号

    public String getLoginAppName() {
        return loginAppName;
    }

    public void setLoginAppName(String loginAppName) {
        this.loginAppName = loginAppName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public String toString() {
        return "AdminLoginLogResponse{" +
                "loginAppName='" + loginAppName + '\'' +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                '}';
    }
}
