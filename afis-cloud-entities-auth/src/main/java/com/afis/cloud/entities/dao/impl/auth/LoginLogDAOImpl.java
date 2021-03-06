package com.afis.cloud.entities.dao.impl.auth;

import com.afis.cloud.db.PageResult;
import com.afis.cloud.db.TurnPage;
import com.afis.cloud.entities.dao.auth.LoginLogDAO;
import com.afis.cloud.entities.model.auth.LoginLog;
import com.afis.cloud.entities.model.auth.LoginLogExample;
import com.afis.cloud.entities.model.auth.LoginLogSelective;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginLogDAOImpl implements LoginLogDAO {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    protected SqlMapClient sqlMapClient;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public LoginLogDAOImpl(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int countByExample(LoginLogExample example) throws SQLException {
        return (Integer)sqlMapClient.queryForObject("T_A_LOGIN_LOG.sacoolbuildergenerated_countByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int deleteByExample(LoginLogExample example) throws SQLException {
        return sqlMapClient.delete("T_A_LOGIN_LOG.sacoolbuildergenerated_deleteByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int deleteByPrimaryKey(long id) throws SQLException {
        return sqlMapClient.delete("T_A_LOGIN_LOG.sacoolbuildergenerated_deleteByPrimaryKey", id);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public long insert(LoginLog record) throws SQLException {
        Object newKey = sqlMapClient.insert("T_A_LOGIN_LOG.sacoolbuildergenerated_insert", record);
        return ((Long) newKey).longValue();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public long insert(LoginLogSelective record) throws SQLException {
        Object newKey = sqlMapClient.insert("T_A_LOGIN_LOG.sacoolbuildergenerated_insertSelective", record);
        return ((Long)newKey).longValue();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int selectByExample(LoginLogExample example, RowHandler<LoginLog> rowHandler) throws SQLException {
        return sqlMapClient.queryWithRowHandler("T_A_LOGIN_LOG.sacoolbuildergenerated_selectByExample", example, rowHandler);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<LoginLog> selectByExample(LoginLogExample example) throws SQLException {
        return sqlMapClient.queryForList("T_A_LOGIN_LOG.sacoolbuildergenerated_selectByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public PageResult<LoginLog> selectByExample(LoginLogExample example, int start, int limit) throws SQLException {
        return new TurnPage(sqlMapClient).selectByExampleWidthPageResult("T_A_LOGIN_LOG.sacoolbuildergenerated_selectByExample", "T_A_LOGIN_LOG.sacoolbuildergenerated_countByExample", example, start, limit);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public LoginLog selectByPrimaryKey(long id) throws SQLException {
        return (LoginLog)sqlMapClient.queryForObject("T_A_LOGIN_LOG.sacoolbuildergenerated_selectByPrimaryKey", id);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int updateByExample(LoginLogSelective record, LoginLogExample example) throws SQLException {
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("record", record);
        parms.put("oredCriteria", example == null ? null : example.getOredCriteria());
        return sqlMapClient.update("T_A_LOGIN_LOG.sacoolbuildergenerated_updateByExampleSelective", parms);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public int updateByExample(LoginLog record, LoginLogExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("T_A_LOGIN_LOG.sacoolbuildergenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int updateByPrimaryKey(LoginLogSelective record) throws SQLException {
        return sqlMapClient.update("T_A_LOGIN_LOG.sacoolbuildergenerated_updateByPrimaryKeySelective", record);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public int updateByPrimaryKey(LoginLog record) throws SQLException {
        int rows = sqlMapClient.update("T_A_LOGIN_LOG.sacoolbuildergenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by www.sacool.com iBATIS builder.
     * <br>This class corresponds to the database table T_A_LOGIN_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Deprecated
    static class UpdateByExampleParms extends LoginLogExample {
        private Object record;

        public UpdateByExampleParms(Object record, LoginLogExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}