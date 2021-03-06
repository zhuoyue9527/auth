package com.afis.cloud.entities.model.auth;

public class TableConfig {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_TABLE_CONFIG.ID
     * <br>The field is the table primary key.
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private long id;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_TABLE_CONFIG.APP_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private long appId;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_TABLE_CONFIG.OPERATOR
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private long operator;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_TABLE_CONFIG.OBJ_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String objId;

    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database column T_A_TABLE_CONFIG.COLUMNS
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    private String columns;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>Copy method of T_A_TABLE_CONFIG
     * @param tableConfig
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void copy(TableConfig tableConfig) {
        tableConfig.id = this.id;
        tableConfig.appId = this.appId;
        tableConfig.operator = this.operator;
        tableConfig.objId = this.objId;
        tableConfig.columns = this.columns;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_TABLE_CONFIG.ID
     *
     * @return the value of T_A_TABLE_CONFIG.ID Primary Key
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public long getId() {
        return id;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_TABLE_CONFIG.ID
     *
     * @param id the value for T_A_TABLE_CONFIG.ID Primary Key
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_TABLE_CONFIG.APP_ID
     *
     * @return the value of T_A_TABLE_CONFIG.APP_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public long getAppId() {
        return appId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_TABLE_CONFIG.APP_ID
     *
     * @param appId the value for T_A_TABLE_CONFIG.APP_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setAppId(long appId) {
        this.appId = appId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_TABLE_CONFIG.OPERATOR
     *
     * @return the value of T_A_TABLE_CONFIG.OPERATOR
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public long getOperator() {
        return operator;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_TABLE_CONFIG.OPERATOR
     *
     * @param operator the value for T_A_TABLE_CONFIG.OPERATOR
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setOperator(long operator) {
        this.operator = operator;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_TABLE_CONFIG.OBJ_ID
     *
     * @return the value of T_A_TABLE_CONFIG.OBJ_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getObjId() {
        return objId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_TABLE_CONFIG.OBJ_ID
     *
     * @param objId the value for T_A_TABLE_CONFIG.OBJ_ID
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setObjId(String objId) {
        this.objId = objId;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method returns the value of the database column T_A_TABLE_CONFIG.COLUMNS
     *
     * @return the value of T_A_TABLE_CONFIG.COLUMNS
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public String getColumns() {
        return columns;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method sets the value of the database column T_A_TABLE_CONFIG.COLUMNS
     *
     * @param columns the value for T_A_TABLE_CONFIG.COLUMNS
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public void setColumns(String columns) {
        this.columns = columns;
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
        sb.append(", appId:");
        sb.append(this.appId);
        sb.append(", operator:");
        sb.append(this.operator);
        sb.append(", objId:");
        sb.append(this.objId);
        sb.append(", columns:");
        sb.append(this.columns);
        sb.append("}");
        return sb.toString();
    }
}