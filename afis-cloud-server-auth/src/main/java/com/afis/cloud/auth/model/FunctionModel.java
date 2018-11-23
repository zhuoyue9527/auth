package com.afis.cloud.auth.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/6 13:44
 */
public class FunctionModel  implements Serializable {

    private long id;

    private String url;

    private String name;

    private String type;

    private String remark;

    private long orderNo;

    private String groupName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "FunctionModel{" +
                "id=" + id +
                ", url=" + url +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", remark='" + remark + '\'' +
                ", orderNo=" + orderNo +
                ", groupName='" + groupName + '\'' +
                '}';
    }

}
