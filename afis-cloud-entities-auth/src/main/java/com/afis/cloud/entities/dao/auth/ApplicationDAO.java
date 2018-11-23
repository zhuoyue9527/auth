package com.afis.cloud.entities.dao.auth;

import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.entities.model.auth.ApplicationExample;
import com.afis.cloud.entities.model.auth.ApplicationSelective;
import com.ibatis.sqlmap.client.event.RowHandler;
import java.sql.SQLException;
import java.util.List;

public interface ApplicationDAO {
    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    int countByExample(ApplicationExample example) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    int deleteByExample(ApplicationExample example) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    int deleteByPrimaryKey(long id) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Deprecated
    long insert(Application record) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    long insert(ApplicationSelective record) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    int selectByExample(ApplicationExample example, RowHandler<Application> rowHandler) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    List<Application> selectByExample(ApplicationExample example) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    PageResult<Application> selectByExample(ApplicationExample example, int start, int limit) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    Application selectByPrimaryKey(long id) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    int updateByExample(ApplicationSelective record, ApplicationExample example) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Deprecated
    int updateByExample(Application record, ApplicationExample example) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    int updateByPrimaryKey(ApplicationSelective record) throws SQLException;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Deprecated
    int updateByPrimaryKey(Application record) throws SQLException;
}