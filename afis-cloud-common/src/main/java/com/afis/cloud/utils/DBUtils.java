/**
 * DBUtil.java created by 杨浩(yanghao) at 2009-12-8
 * SaCool
 * 网 址: http://www.zerg.org
 * SaCool. 版权所有.
 */
package com.afis.cloud.utils;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * 数据库连接类
 * <br><br>
 * DBUtil created by 杨浩(yanghao) at 2009-12-8 下午03:46:53
 *
 * @author 杨浩(yanghao)
 * @version 1.0.0
 *
 * @version <a href="http://www.sacool.com">SaCool</a><br>SaCool. &copy; 版权所有.
 */
public final class DBUtils
{
	/**
	 * 数据库配置文件默认名
	 */
	public final static String SQLMAP_CONFIG_NAME;
	private static Map<String, SqlMapClient> clientMap;

	static
	{
		SQLMAP_CONFIG_NAME = "SqlMapConfig.xml";
		clientMap = new HashMap<String, SqlMapClient>();
	}
	
	/**
	 * 根据指定数据库配置文件新建数据库连接(结果不缓存)
	 * <br><br>
	 * This method created by 杨浩(yanghao) at 2009-12-8 下午03:42:24
	 *
	 * @param sqlMapConfigName
	 * @return {@link SqlMapClient}
	 * 
	 * @see #getSqlMapClient()
	 * @see #getSqlMapClient(String)
	 */
	public static SqlMapClient newSqlMapClient(String sqlMapConfigName)
	{
		SqlMapClient sqlMapClient = null;
		Reader reader = null;
		try
		{
			reader = Resources.getResourceAsReader(sqlMapConfigName);
			if (reader == null){ throw new IOException("Loading SqlMapConfig:" + sqlMapConfigName + " faild."); }
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return sqlMapClient;
	}
	
	/**
	 * 获取已有数据库连接(缓存结果)
	 * <br>默认数据库连接配置文件名:{@link #SQLMAP_CONFIG_NAME}
	 * <br><br>
	 * This method created by 杨浩(yanghao) at 2009-12-8 下午03:48:39
	 *
	 * @return {@link SqlMapClient}
	 *
	 * @see #newSqlMapClient(String)
	 * @see #getSqlMapClient(String)
	 */
	public static SqlMapClient getSqlMapClient()
	{
		return getSqlMapClient(SQLMAP_CONFIG_NAME);
	}
	
	/**
	 * 获取已有数据库连接(缓存结果)
	 * <br><br>
	 * This method created by 杨浩(yanghao) at 2009-12-8 下午03:50:41
	 *
	 * @param sqlMapConfigName
	 * @return {@link SqlMapClient}
	 *
	 * @see #newSqlMapClient(String)
	 * @see #getSqlMapClient()
	 */
	synchronized public static SqlMapClient getSqlMapClient(String sqlMapConfigName)
	{
		SqlMapClient sqlMapClient = clientMap.get(sqlMapConfigName);
		if(sqlMapClient == null)
		{
			sqlMapClient = newSqlMapClient(sqlMapConfigName);
			clientMap.put(sqlMapConfigName, sqlMapClient);
		}
		return sqlMapClient;
	}
	
	private DBUtils(){}
}
