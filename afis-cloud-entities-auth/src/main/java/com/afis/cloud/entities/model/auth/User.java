package com.afis.cloud.entities.model.auth;

import java.util.Date;

public class User {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.ID
     * <br>The field is the table primary key.
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private long id;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.USER_ACCOUNT
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String userAccount;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.USER_NAME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String userName;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.PASSWORD
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String password;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.MOBILE
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String mobile;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.CLIENT_TYPE
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String clientType;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.STATUS
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String status;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.REMARK
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String remark;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.REGISTER_APP_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private long registerAppId;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.REGISTER_OPERATOR
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private Long registerOperator;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.REGISTER_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private Date registerTime;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.OPERATE_APP_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private Long operateAppId;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.OPERATOR
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private Long operator;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.OPERATE_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private Date operateTime;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.LAST_LOGIN_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private Date lastLoginTime;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_USER.LAST_LOGIN_IP
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String lastLoginIp;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>Copy method of T_A_USER
     * @param user
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void copy(User user) {
        user.id = this.id;
        user.userAccount = this.userAccount;
        user.userName = this.userName;
        user.password = this.password;
        user.mobile = this.mobile;
        user.clientType = this.clientType;
        user.status = this.status;
        user.remark = this.remark;
        user.registerAppId = this.registerAppId;
        user.registerOperator = this.registerOperator;
        user.registerTime = this.registerTime;
        user.operateAppId = this.operateAppId;
        user.operator = this.operator;
        user.operateTime = this.operateTime;
        user.lastLoginTime = this.lastLoginTime;
        user.lastLoginIp = this.lastLoginIp;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.ID
     *
     * @return the value of T_A_USER.ID Primary Key
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public long getId() {
        return id;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.ID
     *
     * @param id the value for T_A_USER.ID Primary Key
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.USER_ACCOUNT
     *
     * @return the value of T_A_USER.USER_ACCOUNT
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.USER_ACCOUNT
     *
     * @param userAccount the value for T_A_USER.USER_ACCOUNT
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.USER_NAME
     *
     * @return the value of T_A_USER.USER_NAME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getUserName() {
        return userName;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.USER_NAME
     *
     * @param userName the value for T_A_USER.USER_NAME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.PASSWORD
     *
     * @return the value of T_A_USER.PASSWORD
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.PASSWORD
     *
     * @param password the value for T_A_USER.PASSWORD
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.MOBILE
     *
     * @return the value of T_A_USER.MOBILE
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.MOBILE
     *
     * @param mobile the value for T_A_USER.MOBILE
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.CLIENT_TYPE
     *
     * @return the value of T_A_USER.CLIENT_TYPE
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getClientType() {
        return clientType;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.CLIENT_TYPE
     *
     * @param clientType the value for T_A_USER.CLIENT_TYPE
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.STATUS
     *
     * @return the value of T_A_USER.STATUS
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.STATUS
     *
     * @param status the value for T_A_USER.STATUS
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.REMARK
     *
     * @return the value of T_A_USER.REMARK
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.REMARK
     *
     * @param remark the value for T_A_USER.REMARK
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.REGISTER_APP_ID
     *
     * @return the value of T_A_USER.REGISTER_APP_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public long getRegisterAppId() {
        return registerAppId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.REGISTER_APP_ID
     *
     * @param registerAppId the value for T_A_USER.REGISTER_APP_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setRegisterAppId(long registerAppId) {
        this.registerAppId = registerAppId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.REGISTER_OPERATOR
     *
     * @return the value of T_A_USER.REGISTER_OPERATOR
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public Long getRegisterOperator() {
        return registerOperator;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.REGISTER_OPERATOR
     *
     * @param registerOperator the value for T_A_USER.REGISTER_OPERATOR
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setRegisterOperator(Long registerOperator) {
        this.registerOperator = registerOperator;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.REGISTER_TIME
     *
     * @return the value of T_A_USER.REGISTER_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public Date getRegisterTime() {
        return registerTime;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.REGISTER_TIME
     *
     * @param registerTime the value for T_A_USER.REGISTER_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.OPERATE_APP_ID
     *
     * @return the value of T_A_USER.OPERATE_APP_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public Long getOperateAppId() {
        return operateAppId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.OPERATE_APP_ID
     *
     * @param operateAppId the value for T_A_USER.OPERATE_APP_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setOperateAppId(Long operateAppId) {
        this.operateAppId = operateAppId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.OPERATOR
     *
     * @return the value of T_A_USER.OPERATOR
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public Long getOperator() {
        return operator;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.OPERATOR
     *
     * @param operator the value for T_A_USER.OPERATOR
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setOperator(Long operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.OPERATE_TIME
     *
     * @return the value of T_A_USER.OPERATE_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public Date getOperateTime() {
        return operateTime;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.OPERATE_TIME
     *
     * @param operateTime the value for T_A_USER.OPERATE_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.LAST_LOGIN_TIME
     *
     * @return the value of T_A_USER.LAST_LOGIN_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.LAST_LOGIN_TIME
     *
     * @param lastLoginTime the value for T_A_USER.LAST_LOGIN_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_USER.LAST_LOGIN_IP
     *
     * @return the value of T_A_USER.LAST_LOGIN_IP
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_USER.LAST_LOGIN_IP
     *
     * @param lastLoginIp the value for T_A_USER.LAST_LOGIN_IP
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>toString
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("id:");
        sb.append(this.id);
        sb.append(", userAccount:");
        sb.append(this.userAccount);
        sb.append(", userName:");
        sb.append(this.userName);
        sb.append(", password:");
        sb.append(this.password);
        sb.append(", mobile:");
        sb.append(this.mobile);
        sb.append(", clientType:");
        sb.append(this.clientType);
        sb.append(", status:");
        sb.append(this.status);
        sb.append(", remark:");
        sb.append(this.remark);
        sb.append(", registerAppId:");
        sb.append(this.registerAppId);
        sb.append(", registerOperator:");
        sb.append(this.registerOperator);
        sb.append(", registerTime:");
        sb.append(this.registerTime);
        sb.append(", operateAppId:");
        sb.append(this.operateAppId);
        sb.append(", operator:");
        sb.append(this.operator);
        sb.append(", operateTime:");
        sb.append(this.operateTime);
        sb.append(", lastLoginTime:");
        sb.append(this.lastLoginTime);
        sb.append(", lastLoginIp:");
        sb.append(this.lastLoginIp);
        sb.append("}");
        return sb.toString();
    }
}