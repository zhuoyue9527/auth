package com.afis.web.auth.protocol.admin;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/7 10:43
 */
public class AdminFunctionModel {

    private long id;

    private long url;

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

    public long getUrl() {
        return url;
    }

    public void setUrl(long url) {
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
        return "AdminFunctionModel{" +
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
