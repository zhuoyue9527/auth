package com.afis.cloud.auth.model.protocol;

import com.afis.cloud.entities.model.auth.OperateLog;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/8 17:05
 */
public class AdminOperateLogResponse extends OperateLog {


    private String operateAppName;//操作应用名称

    private String functionName;//功能名称

    private String operateName;//操作员名称

    public String getOperateAppName() {
        return operateAppName;
    }

    public void setOperateAppName(String operateAppName) {
        this.operateAppName = operateAppName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    @Override
    public String toString() {
        return "AdminOperateLogResponse{" +
                "operateAppName='" + operateAppName + '\'' +
                ", functionName='" + functionName + '\'' +
                ", operateName='" + operateName + '\'' +
                '}';
    }
}
