package com.afis.cloud.auth.model.protocol;

import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.entities.model.auth.Function;
import com.afis.cloud.entities.model.auth.User;
import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @Description:应用授权添加请求参数
 * @Author: lizheng
 * @Date: 2018/11/2 13:50
 */
@ApiModel("应用授权添加请求对象")
public class AdminUserResponse extends User {

    private String operateAppName;
    private String operateName;
    private String registeAppName;
    private String registeOperateName;
    private String loginKey;
    private String loginTime;

    public String getOperateAppName() {
        return operateAppName;
    }

    public void setOperateAppName(String operateAppName) {
        this.operateAppName = operateAppName;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getRegisteAppName() {
        return registeAppName;
    }

    public void setRegisteAppName(String registeAppName) {
        this.registeAppName = registeAppName;
    }

    public String getRegisteOperateName() {
        return registeOperateName;
    }

    public void setRegisteOperateName(String registeOperateName) {
        this.registeOperateName = registeOperateName;
    }

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "AdminUserResponse{" +
                "operateAppName='" + operateAppName + '\'' +
                ", operateName='" + operateName + '\'' +
                ", registeAppName='" + registeAppName + '\'' +
                ", registeOperateName='" + registeOperateName + '\'' +
                ", loginKey='" + loginKey + '\'' +
                ", loginTime='" + loginTime + '\'' +
                '}';
    }
}
