package com.afis.cloud.db;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * This class was generated by www.sacool.com iBATIS builder.
 * Turn page a result.
 *
 * @sacoolbuildergenerated 2010-03-02 12:52:22
 * @author sacool ibatis builder Team
 */
public class TurnPage {
    /**
     * This field was generated by www.sacool.com iBATIS builder.
     * The sqlMapClient.
     *
     * @sacoolbuildergenerated 2010-03-02 12:52:22
     * @author sacool ibatis builder Team
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * The construction for TurnPage
     * @param sqlMapClient
     *
     * @sacoolbuildergenerated 2010-03-02 12:52:22
     * @author sacool ibatis builder Team
     */
    public TurnPage(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * The method to TurnPage
     * @param sqlId
     * @param countSqlId
     * @param parameter
     * @param start
     * @param limit
     *
     * @sacoolbuildergenerated 2010-03-02 12:52:22
     * @author sacool ibatis builder Team
     */
    public <T> PageResult<T> selectByExampleWidthPageResult(String sqlId, String countSqlId, Object parameter, int start, int limit) throws SQLException {
    	@SuppressWarnings("unchecked")
		List<T> result = this.sqlMapClient.queryForList(sqlId, parameter, start, limit);
        Integer totalSize = (Integer)this.sqlMapClient.queryForObject(countSqlId, parameter);
        return new PageResult<T>(start, limit, totalSize, result);
    }

    /**
     * This method was generated by www.sacool.com iBATIS builder.
     * The method to TurnPage
     * @param sqlId
     * @param parameter
     * @param start
     * @param limit
     *
     * @sacoolbuildergenerated 2010-03-02 12:52:22
     * @author sacool ibatis builder Team
     */
    public <T> PageResult<T> selectByExampleWidthPageResult(String sqlId, Object parameter, int start, int limit) throws SQLException {
        return this.selectByExampleWidthPageResult(sqlId, sqlId + "_count", parameter, start, limit);
    }
}