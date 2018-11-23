package com.afis.cloud.business.impl;

import com.afis.cloud.db.PageResult;
import com.afis.cloud.db.TurnPage;
import com.afis.cloud.utils.DBUtils;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.ibatis.sqlmap.engine.execution.BatchException;

import java.sql.SQLException;
import java.util.List;

/**
 * 业务核心类
 * 
 * @className: BusinessCore
 * @Company: Afis
 * @author zhuzhenghua
 * @date 2017年6月8日 下午1:27:15
 * @version
 */
public class BusinessCore {
	protected SqlMapClient sqlMapClient;
	private TurnPage turnPage;

	public BusinessCore() {
		this.sqlMapClient = DBUtils.getSqlMapClient();
		this.turnPage = new TurnPage(this.sqlMapClient);
	}

	protected int delete(String sqlId) throws SQLException {
		return this.sqlMapClient.delete(sqlId);
	}

	protected int delete(String sqlId, Object parameters) throws SQLException {
		return this.sqlMapClient.delete(sqlId, parameters);
	}
	
	protected int update(String sqlId) throws SQLException {
		return this.sqlMapClient.update(sqlId);
	}

	protected int update(String sqlId, Object parameters) throws SQLException {
		return this.sqlMapClient.update(sqlId, parameters);
	}

	protected int executeBatch() throws SQLException {
		return this.sqlMapClient.executeBatch();
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> executeBatchDetailed() throws SQLException, BatchException {
		return this.sqlMapClient.executeBatchDetailed();
	}

	@SuppressWarnings("unchecked")
	protected <T extends Object> T insert(String sqlId) throws SQLException {
		return (T) this.sqlMapClient.insert(sqlId);
	}

	@SuppressWarnings("unchecked")
	protected <T extends Object> T insert(String sqlId, Object parameters) throws SQLException {
		return (T) this.sqlMapClient.insert(sqlId, parameters);
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> queryForList(String sqlId) throws SQLException {
		return this.sqlMapClient.queryForList(sqlId);
	}

	@SuppressWarnings("unchecked")
	protected <T> List<T> queryForList(String sqlId, Object parameters) throws SQLException {
		return this.sqlMapClient.queryForList(sqlId, parameters);
	}

	@SuppressWarnings("unchecked")
	protected <T extends Object> T queryForObject(String sqlId) throws SQLException {
		return (T) this.sqlMapClient.queryForObject(sqlId);
	}

	@SuppressWarnings("unchecked")
	protected <T extends Object> T queryForObject(String sqlId, Object parameters) throws SQLException {
		return (T) this.sqlMapClient.queryForObject(sqlId, parameters);
	}

	protected int queryWithRowHandler(String sqlId, RowHandler<?> rowHandler) throws SQLException {
		return this.sqlMapClient.queryWithRowHandler(sqlId, rowHandler);
	}

	protected int queryWithRowHandler(String sqlId, Object parameters, RowHandler<?> rowHandler) throws SQLException {
		return this.sqlMapClient.queryWithRowHandler(sqlId, parameters, rowHandler);
	}

	protected <T extends Object> PageResult<T> page(String sqlId, Object parameter, int start, int limit)
			throws SQLException {
		return this.turnPage.selectByExampleWidthPageResult(sqlId, parameter, start, limit);
	}

	protected <T extends Object> PageResult<T> page(String sqlId, String countSqlId, Object parameter, int start,
			int limit) throws SQLException {
		return this.turnPage.selectByExampleWidthPageResult(sqlId, countSqlId, parameter, start, limit);
	}
}
