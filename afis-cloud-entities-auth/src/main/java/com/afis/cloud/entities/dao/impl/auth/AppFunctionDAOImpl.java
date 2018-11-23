package com.afis.cloud.entities.dao.impl.auth;

import com.afis.cloud.db.PageResult;
import com.afis.cloud.db.TurnPage;
import com.afis.cloud.entities.dao.auth.AppFunctionDAO;
import com.afis.cloud.entities.model.auth.AppFunction;
import com.afis.cloud.entities.model.auth.AppFunctionExample;
import com.afis.cloud.entities.model.auth.AppFunctionSelective;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppFunctionDAOImpl implements AppFunctionDAO {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    protected SqlMapClient sqlMapClient;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public AppFunctionDAOImpl(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int countByExample(AppFunctionExample example) throws SQLException {
        return (Integer)sqlMapClient.queryForObject("T_A_APP_FUNCTION.sacoolbuildergenerated_countByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int deleteByExample(AppFunctionExample example) throws SQLException {
        return sqlMapClient.delete("T_A_APP_FUNCTION.sacoolbuildergenerated_deleteByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int deleteByPrimaryKey(long id) throws SQLException {
        return sqlMapClient.delete("T_A_APP_FUNCTION.sacoolbuildergenerated_deleteByPrimaryKey", id);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public long insert(AppFunction record) throws SQLException {
        Object newKey = sqlMapClient.insert("T_A_APP_FUNCTION.sacoolbuildergenerated_insert", record);
        return ((Long) newKey).longValue();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public long insert(AppFunctionSelective record) throws SQLException {
        Object newKey = sqlMapClient.insert("T_A_APP_FUNCTION.sacoolbuildergenerated_insertSelective", record);
        return ((Long)newKey).longValue();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int selectByExample(AppFunctionExample example, RowHandler<AppFunction> rowHandler) throws SQLException {
        return sqlMapClient.queryWithRowHandler("T_A_APP_FUNCTION.sacoolbuildergenerated_selectByExample", example, rowHandler);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<AppFunction> selectByExample(AppFunctionExample example) throws SQLException {
        return sqlMapClient.queryForList("T_A_APP_FUNCTION.sacoolbuildergenerated_selectByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public PageResult<AppFunction> selectByExample(AppFunctionExample example, int start, int limit) throws SQLException {
        return new TurnPage(sqlMapClient).selectByExampleWidthPageResult("T_A_APP_FUNCTION.sacoolbuildergenerated_selectByExample", "T_A_APP_FUNCTION.sacoolbuildergenerated_countByExample", example, start, limit);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public AppFunction selectByPrimaryKey(long id) throws SQLException {
        return (AppFunction)sqlMapClient.queryForObject("T_A_APP_FUNCTION.sacoolbuildergenerated_selectByPrimaryKey", id);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int updateByExample(AppFunctionSelective record, AppFunctionExample example) throws SQLException {
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("record", record);
        parms.put("oredCriteria", example == null ? null : example.getOredCriteria());
        return sqlMapClient.update("T_A_APP_FUNCTION.sacoolbuildergenerated_updateByExampleSelective", parms);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public int updateByExample(AppFunction record, AppFunctionExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("T_A_APP_FUNCTION.sacoolbuildergenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int updateByPrimaryKey(AppFunctionSelective record) throws SQLException {
        return sqlMapClient.update("T_A_APP_FUNCTION.sacoolbuildergenerated_updateByPrimaryKeySelective", record);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public int updateByPrimaryKey(AppFunction record) throws SQLException {
        int rows = sqlMapClient.update("T_A_APP_FUNCTION.sacoolbuildergenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by www.sacool.com iBATIS builder.
     * <br>This class corresponds to the database table T_A_APP_FUNCTION
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Deprecated
    static class UpdateByExampleParms extends AppFunctionExample {
        private Object record;

        public UpdateByExampleParms(Object record, AppFunctionExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}