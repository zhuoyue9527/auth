package com.afis.cloud.auth.model;

import com.afis.cloud.entities.model.auth.Function;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/6 13:44
 */
public class FunctionListModel implements Serializable {

    private Long appId;

    private String groupName;

    private List<FunctionModel> functionList;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<FunctionModel> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<FunctionModel> functionList) {
        this.functionList = functionList;
    }

    @Override
    public String toString() {
        return "FunctionListModel{" +
                "appId=" + appId +
                ", groupName='" + groupName + '\'' +
                ", functionList=" + functionList +
                '}';
    }
}
