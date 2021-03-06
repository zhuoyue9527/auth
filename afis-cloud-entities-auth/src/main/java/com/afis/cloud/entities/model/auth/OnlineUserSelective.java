package com.afis.cloud.entities.model.auth;

import java.util.Date;

public class OnlineUserSelective extends OnlineUser {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_ONLINE_USER.LOGIN_KEY
     * <br>The field is the table primary key.
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    private boolean loginKey$seted;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_ONLINE_USER.USER_ID
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    private boolean userId$seted;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_ONLINE_USER.LOGIN_TIME
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    private boolean loginTime$seted;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>Copy method of T_A_ONLINE_USER
     * @param onlineUserSelective
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public void copy(OnlineUserSelective onlineUserSelective) {
        super.copy(onlineUserSelective);
        onlineUserSelective.loginKey$seted = this.loginKey$seted;
        onlineUserSelective.userId$seted = this.userId$seted;
        onlineUserSelective.loginTime$seted = this.loginTime$seted;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_ONLINE_USER.LOGIN_KEY
     *
     * @return the value of T_A_ONLINE_USER.LOGIN_KEY Primary Key
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public boolean isLoginKey$seted() {
        return loginKey$seted;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_ONLINE_USER.LOGIN_KEY
     *
     * @param loginKey the value for T_A_ONLINE_USER.LOGIN_KEY Primary Key
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public void setLoginKey(String loginKey) {
        this.loginKey$seted = true;
        super.setLoginKey(loginKey);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_ONLINE_USER.USER_ID
     *
     * @return the value of T_A_ONLINE_USER.USER_ID
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public boolean isUserId$seted() {
        return userId$seted;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_ONLINE_USER.USER_ID
     *
     * @param userId the value for T_A_ONLINE_USER.USER_ID
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public void setUserId(long userId) {
        this.userId$seted = true;
        super.setUserId(userId);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_ONLINE_USER.LOGIN_TIME
     *
     * @return the value of T_A_ONLINE_USER.LOGIN_TIME
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public boolean isLoginTime$seted() {
        return loginTime$seted;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_ONLINE_USER.LOGIN_TIME
     *
     * @param loginTime the value for T_A_ONLINE_USER.LOGIN_TIME
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime$seted = true;
        super.setLoginTime(loginTime);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>toString
     *
     * @sacoolbuildergenerated 2018-11-22 11:14:33
     * @author sacool ibatis builder Team
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString().replaceAll("[}]$", ""));
        sb.append("}");
        return sb.toString();
    }
}