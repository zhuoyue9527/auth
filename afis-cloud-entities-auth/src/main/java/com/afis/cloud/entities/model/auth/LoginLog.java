package com.afis.cloud.entities.model.auth;

import java.util.Date;

public class LoginLog {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_LOGIN_LOG.ID
     * <br>The field is the table primary key.
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private long id;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_LOGIN_LOG.USER_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private long userId;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_LOGIN_LOG.LOGIN_APP
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private long loginApp;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_LOGIN_LOG.LOGIN_IP
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String loginIp;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_LOGIN_LOG.STATUS
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String status;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_LOGIN_LOG.SYSTEM_INFO
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String systemInfo;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_LOGIN_LOG.BROWSER_INFO
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String browserInfo;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_LOGIN_LOG.REMARK
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String remark;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_LOGIN_LOG.LOGIN_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private Date loginTime;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>Copy method of T_A_LOGIN_LOG
     * @param loginLog
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void copy(LoginLog loginLog) {
        loginLog.id = this.id;
        loginLog.userId = this.userId;
        loginLog.loginApp = this.loginApp;
        loginLog.loginIp = this.loginIp;
        loginLog.status = this.status;
        loginLog.systemInfo = this.systemInfo;
        loginLog.browserInfo = this.browserInfo;
        loginLog.remark = this.remark;
        loginLog.loginTime = this.loginTime;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_LOGIN_LOG.ID
     *
     * @return the value of T_A_LOGIN_LOG.ID Primary Key
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public long getId() {
        return id;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_LOGIN_LOG.ID
     *
     * @param id the value for T_A_LOGIN_LOG.ID Primary Key
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_LOGIN_LOG.USER_ID
     *
     * @return the value of T_A_LOGIN_LOG.USER_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public long getUserId() {
        return userId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_LOGIN_LOG.USER_ID
     *
     * @param userId the value for T_A_LOGIN_LOG.USER_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_LOGIN_LOG.LOGIN_APP
     *
     * @return the value of T_A_LOGIN_LOG.LOGIN_APP
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public long getLoginApp() {
        return loginApp;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_LOGIN_LOG.LOGIN_APP
     *
     * @param loginApp the value for T_A_LOGIN_LOG.LOGIN_APP
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setLoginApp(long loginApp) {
        this.loginApp = loginApp;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_LOGIN_LOG.LOGIN_IP
     *
     * @return the value of T_A_LOGIN_LOG.LOGIN_IP
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_LOGIN_LOG.LOGIN_IP
     *
     * @param loginIp the value for T_A_LOGIN_LOG.LOGIN_IP
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_LOGIN_LOG.STATUS
     *
     * @return the value of T_A_LOGIN_LOG.STATUS
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_LOGIN_LOG.STATUS
     *
     * @param status the value for T_A_LOGIN_LOG.STATUS
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_LOGIN_LOG.SYSTEM_INFO
     *
     * @return the value of T_A_LOGIN_LOG.SYSTEM_INFO
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getSystemInfo() {
        return systemInfo;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_LOGIN_LOG.SYSTEM_INFO
     *
     * @param systemInfo the value for T_A_LOGIN_LOG.SYSTEM_INFO
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setSystemInfo(String systemInfo) {
        this.systemInfo = systemInfo;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_LOGIN_LOG.BROWSER_INFO
     *
     * @return the value of T_A_LOGIN_LOG.BROWSER_INFO
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getBrowserInfo() {
        return browserInfo;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_LOGIN_LOG.BROWSER_INFO
     *
     * @param browserInfo the value for T_A_LOGIN_LOG.BROWSER_INFO
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_LOGIN_LOG.REMARK
     *
     * @return the value of T_A_LOGIN_LOG.REMARK
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_LOGIN_LOG.REMARK
     *
     * @param remark the value for T_A_LOGIN_LOG.REMARK
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_LOGIN_LOG.LOGIN_TIME
     *
     * @return the value of T_A_LOGIN_LOG.LOGIN_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_LOGIN_LOG.LOGIN_TIME
     *
     * @param loginTime the value for T_A_LOGIN_LOG.LOGIN_TIME
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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
        sb.append(", userId:");
        sb.append(this.userId);
        sb.append(", loginApp:");
        sb.append(this.loginApp);
        sb.append(", loginIp:");
        sb.append(this.loginIp);
        sb.append(", status:");
        sb.append(this.status);
        sb.append(", systemInfo:");
        sb.append(this.systemInfo);
        sb.append(", browserInfo:");
        sb.append(this.browserInfo);
        sb.append(", remark:");
        sb.append(this.remark);
        sb.append(", loginTime:");
        sb.append(this.loginTime);
        sb.append("}");
        return sb.toString();
    }
}