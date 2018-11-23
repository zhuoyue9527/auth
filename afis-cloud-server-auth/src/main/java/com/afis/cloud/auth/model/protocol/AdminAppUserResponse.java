package com.afis.cloud.auth.model.protocol;

import com.afis.cloud.entities.model.auth.AppUser;
import com.afis.cloud.entities.model.auth.User;
import io.swagger.annotations.ApiModel;


@ApiModel("用户授权授权响应对象")
public class AdminAppUserResponse extends AppUser {

    private String appName;
    private String userName;
    private String userAccount;
    private String mobile;
    private String operateName;
    private String remark;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AdminAppUserResponse{" +
                "appName='" + appName + '\'' +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", mobile='" + mobile + '\'' +
                ", operateName='" + operateName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
