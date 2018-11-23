package com.afis.cloud.entities.dao.impl.auth;

import com.afis.cloud.db.PageResult;
import com.afis.cloud.db.TurnPage;
import com.afis.cloud.entities.dao.auth.ApplicationDAO;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.entities.model.auth.ApplicationExample;
import com.afis.cloud.entities.model.auth.ApplicationSelective;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationDAOImpl implements ApplicationDAO {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    protected SqlMapClient sqlMapClient;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public ApplicationDAOImpl(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int countByExample(ApplicationExample example) throws SQLException {
        return (Integer)sqlMapClient.queryForObject("T_A_APPLICATION.sacoolbuildergenerated_countByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int deleteByExample(ApplicationExample example) throws SQLException {
        return sqlMapClient.delete("T_A_APPLICATION.sacoolbuildergenerated_deleteByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int deleteByPrimaryKey(long id) throws SQLException {
        return sqlMapClient.delete("T_A_APPLICATION.sacoolbuildergenerated_deleteByPrimaryKey", id);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public long insert(Application record) throws SQLException {
        Object newKey = sqlMapClient.insert("T_A_APPLICATION.sacoolbuildergenerated_insert", record);
        return ((Long) newKey).longValue();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public long insert(ApplicationSelective record) throws SQLException {
        Object newKey = sqlMapClient.insert("T_A_APPLICATION.sacoolbuildergenerated_insertSelective", record);
        return ((Long)newKey).longValue();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int selectByExample(ApplicationExample example, RowHandler<Application> rowHandler) throws SQLException {
        return sqlMapClient.queryWithRowHandler("T_A_APPLICATION.sacoolbuildergenerated_selectByExample", example, rowHandler);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Application> selectByExample(ApplicationExample example) throws SQLException {
        return sqlMapClient.queryForList("T_A_APPLICATION.sacoolbuildergenerated_selectByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public PageResult<Application> selectByExample(ApplicationExample example, int start, int limit) throws SQLException {
        return new TurnPage(sqlMapClient).selectByExampleWidthPageResult("T_A_APPLICATION.sacoolbuildergenerated_selectByExample", "T_A_APPLICATION.sacoolbuildergenerated_countByExample", example, start, limit);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public Application selectByPrimaryKey(long id) throws SQLException {
        return (Application)sqlMapClient.queryForObject("T_A_APPLICATION.sacoolbuildergenerated_selectByPrimaryKey", id);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int updateByExample(ApplicationSelective record, ApplicationExample example) throws SQLException {
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("record", record);
        parms.put("oredCriteria", example == null ? null : example.getOredCriteria());
        return sqlMapClient.update("T_A_APPLICATION.sacoolbuildergenerated_updateByExampleSelective", parms);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public int updateByExample(Application record, ApplicationExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("T_A_APPLICATION.sacoolbuildergenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int updateByPrimaryKey(ApplicationSelective record) throws SQLException {
        return sqlMapClient.update("T_A_APPLICATION.sacoolbuildergenerated_updateByPrimaryKeySelective", record);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public int updateByPrimaryKey(Application record) throws SQLException {
        int rows = sqlMapClient.update("T_A_APPLICATION.sacoolbuildergenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by www.sacool.com iBATIS builder.
     * <br>This class corresponds to the database table T_A_APPLICATION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Deprecated
    static class UpdateByExampleParms extends ApplicationExample {
        private Object record;

        public UpdateByExampleParms(Object record, ApplicationExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}