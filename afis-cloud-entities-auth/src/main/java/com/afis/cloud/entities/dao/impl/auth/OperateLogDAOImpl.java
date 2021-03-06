package com.afis.cloud.entities.dao.impl.auth;

import com.afis.cloud.db.PageResult;
import com.afis.cloud.db.TurnPage;
import com.afis.cloud.entities.dao.auth.OperateLogDAO;
import com.afis.cloud.entities.model.auth.OperateLog;
import com.afis.cloud.entities.model.auth.OperateLogExample;
import com.afis.cloud.entities.model.auth.OperateLogSelective;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperateLogDAOImpl implements OperateLogDAO {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * <br>This field corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    protected SqlMapClient sqlMapClient;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    public OperateLogDAOImpl(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int countByExample(OperateLogExample example) throws SQLException {
        return (Integer)sqlMapClient.queryForObject("T_A_OPERATE_LOG.sacoolbuildergenerated_countByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int deleteByExample(OperateLogExample example) throws SQLException {
        return sqlMapClient.delete("T_A_OPERATE_LOG.sacoolbuildergenerated_deleteByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int deleteByPrimaryKey(long id) throws SQLException {
        return sqlMapClient.delete("T_A_OPERATE_LOG.sacoolbuildergenerated_deleteByPrimaryKey", id);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public long insert(OperateLog record) throws SQLException {
        Object newKey = sqlMapClient.insert("T_A_OPERATE_LOG.sacoolbuildergenerated_insert", record);
        return ((Long) newKey).longValue();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public long insert(OperateLogSelective record) throws SQLException {
        Object newKey = sqlMapClient.insert("T_A_OPERATE_LOG.sacoolbuildergenerated_insertSelective", record);
        return ((Long)newKey).longValue();
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int selectByExample(OperateLogExample example, RowHandler<OperateLog> rowHandler) throws SQLException {
        return sqlMapClient.queryWithRowHandler("T_A_OPERATE_LOG.sacoolbuildergenerated_selectByExample", example, rowHandler);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<OperateLog> selectByExample(OperateLogExample example) throws SQLException {
        return sqlMapClient.queryForList("T_A_OPERATE_LOG.sacoolbuildergenerated_selectByExample", example);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public PageResult<OperateLog> selectByExample(OperateLogExample example, int start, int limit) throws SQLException {
        return new TurnPage(sqlMapClient).selectByExampleWidthPageResult("T_A_OPERATE_LOG.sacoolbuildergenerated_selectByExample", "T_A_OPERATE_LOG.sacoolbuildergenerated_countByExample", example, start, limit);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public OperateLog selectByPrimaryKey(long id) throws SQLException {
        return (OperateLog)sqlMapClient.queryForObject("T_A_OPERATE_LOG.sacoolbuildergenerated_selectByPrimaryKey", id);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int updateByExample(OperateLogSelective record, OperateLogExample example) throws SQLException {
        Map<String, Object> parms = new HashMap<String, Object>();
        parms.put("record", record);
        parms.put("oredCriteria", example == null ? null : example.getOredCriteria());
        return sqlMapClient.update("T_A_OPERATE_LOG.sacoolbuildergenerated_updateByExampleSelective", parms);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public int updateByExample(OperateLog record, OperateLogExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("T_A_OPERATE_LOG.sacoolbuildergenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    public int updateByPrimaryKey(OperateLogSelective record) throws SQLException {
        return sqlMapClient.update("T_A_OPERATE_LOG.sacoolbuildergenerated_updateByPrimaryKeySelective", record);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * <br>This method corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Override
    @Deprecated
    public int updateByPrimaryKey(OperateLog record) throws SQLException {
        int rows = sqlMapClient.update("T_A_OPERATE_LOG.sacoolbuildergenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by www.sacool.com iBATIS builder.
     * <br>This class corresponds to the database table T_A_OPERATE_LOG
     *
     * @sacoolbuildergenerated 2018-10-31 11:01:44
     * @author sacool ibatis builder Team
     */
    @Deprecated
    static class UpdateByExampleParms extends OperateLogExample {
        private Object record;

        public UpdateByExampleParms(Object record, OperateLogExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}