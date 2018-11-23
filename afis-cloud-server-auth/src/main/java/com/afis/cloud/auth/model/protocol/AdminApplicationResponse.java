package com.afis.cloud.auth.model.protocol;

import com.afis.cloud.auth.model.AppFunctionModel;
import com.afis.cloud.entities.model.auth.AppFunction;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.entities.model.auth.Function;
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
public class AdminApplicationResponse extends Application {

    private String operateAppName;
    private String operateName;
    private List<Function> functionList;


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

    public List<Function> getFunctionList() {
        return functionList;
    }

    public void setFunctionList(List<Function> functionList) {
        this.functionList = functionList;
    }

    @Override
    public String toString() {
        return "AdminApplicationResponse{" +
                ", operateAppName=" + operateAppName +
                ", operateName=" + operateName +
                ", functionList=" + functionList +
                '}';
    }
}
