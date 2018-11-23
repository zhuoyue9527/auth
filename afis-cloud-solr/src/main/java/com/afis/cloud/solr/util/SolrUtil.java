package com.afis.cloud.solr.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.afis.cloud.db.PageResult;
import com.afis.cloud.solr.entity.AfisDynamicAbstractEntity;

@Component
public class SolrUtil {
	@Autowired
	private SolrClient solrClient;

	private static final String _VERSION_ = "_version_";

	private static final int _SOLR_PAGE_START_DEFAULT_ = 0;// 默认的第一页

	private static final int _SOLR_PAGE_ROWS_DEFAULT_ = 10;// 默认显示的条数

	public static final String _SOLR_SORT_ASC_ = "asc";// 升序

	public static final String _SOLR_SORT_DESC_ = "desc";// 降序

	private static final String[] _SOLR_SORT_RANGE_ = { _SOLR_SORT_ASC_, _SOLR_SORT_DESC_ };

	private static final String _SOLR_QUERY_DEFAULT_ = "*:*";// 默认查询条件

	/**
	 * 添加到solr搜索引擎
	 * 
	 * @param map
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void addDocument(Map<String, Object> map) throws SolrServerException, IOException {
		SolrInputDocument doc = new SolrInputDocument();
		for (String key : map.keySet()) {
			doc.addField(key, map.get(key));
		}
		solrClient.add(doc);
		solrClient.commit();
	}

	/**
	 * 批量增加信息到solr搜索引擎
	 * 
	 * @param list
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void addDocument(List<Map<String, Object>> list) throws SolrServerException, IOException {
		if (list == null || list.size() == 0) {
			return;
		}
		SolrInputDocument doc;
		for (Map<String, Object> map : list) {
			doc = new SolrInputDocument();
			for (String key : map.keySet()) {
				doc.addField(key, map.get(key));
			}
			solrClient.add(doc);
		}
		solrClient.commit();
	}

	/**
	 * 删除solr搜索引擎上某条记录
	 * 
	 * @param id
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void delDocument(String id) throws SolrServerException, IOException {
		solrClient.deleteById(id);
		solrClient.commit();
	}

	/**
	 * 批量删除solr上数据
	 * 
	 * @param ids
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void delDocument(List<String> ids) throws SolrServerException, IOException {
		for (String id : ids) {
			solrClient.deleteById(id);
		}
		solrClient.commit();
	}

	/**
	 * 根据id获取solr搜索引擎上的某条记录
	 * 
	 * @param id
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public SolrDocument getDocument(String id) throws SolrServerException, IOException {
		return solrClient.getById(id);
	}

	/**
	 * 根据id更新solr搜索引擎上的某条记录
	 * 
	 * @param id
	 * @param map
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public void updateDocument(String id, Map<String, Object> map) throws SolrServerException, IOException {
		SolrDocument doc = this.getDocument(id);
		if (doc != null) {
			Map<String, Object> valueMap = doc.getFieldValueMap();
			SolrInputDocument input = new SolrInputDocument();
			for (String key : valueMap.keySet()) {
				if (!_VERSION_.equals(key)) {
					input.addField(key, valueMap.get(key));
				}
			}
			for (String key : map.keySet()) {
				input.remove(key);
				input.addField(key, map.get(key));
			}
			solrClient.add(input);
			solrClient.commit();
		}
	}

	/**
	 * 根据条件过滤(这种没办法将动态列加入到对象中，T必须是序列化的类，并且在字段上加了@Field注解)
	 * 
	 * @param singeMap
	 *            字段单个值过滤
	 * @param multiMap
	 *            字段多个值过滤
	 * @param sortMap
	 *            排序map
	 * @param start
	 *            第几页
	 * @param limit
	 *            每页条数
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	public <T> PageResult<T> queryDocument(Map<String, Object[]> multiMap, Map<String, Object[]> rangeMap,
			Map<String, String> sortMap, Integer start, Integer limit, Class<T> typeDomain)
			throws SolrServerException, IOException {
		QueryResponse response = queryDocument(multiMap, rangeMap, sortMap, start, limit);
		PageResult<T> result = new PageResult<T>();
		result.setLimit(limit == null ? _SOLR_PAGE_ROWS_DEFAULT_ : limit);
		result.setStart(start == null ? _SOLR_PAGE_START_DEFAULT_ : start);
		result.setTotalSize((int) response.getResults().getNumFound());
		result.setResult(response.getBeans(typeDomain));
		return result;
	}

	public <T extends AfisDynamicAbstractEntity> PageResult<T> queryDocumentAndReturnDynamicModel(
			Map<String, Object[]> multiMap, Map<String, Object[]> rangeMap, Map<String, String> sortMap, Integer start,
			Integer limit, Class<T> typeDomain) throws SolrServerException, IOException {
		QueryResponse response = queryDocument(multiMap, rangeMap, sortMap, start, limit);
		PageResult<T> result = new PageResult<T>();
		result.setLimit(limit == null ? _SOLR_PAGE_ROWS_DEFAULT_ : limit);
		result.setStart(start == null ? _SOLR_PAGE_START_DEFAULT_ : start);
		result.setTotalSize((int) response.getResults().getNumFound());
		result.setResult(this.packT(typeDomain, response.getResults()));
		return result;
	}

	private <T extends AfisDynamicAbstractEntity> List<T> packT(Class<T> type, SolrDocumentList list) {
		List<T> result = new ArrayList<T>();
		for (SolrDocument doc : list) {
			Collection<String> fields = doc.getFieldNames();
			try {
				T t = type.newInstance();
				for (String field : fields) {
					try {
						Field tField = type.getDeclaredField(field);
						tField.setAccessible(true);
						tField.set(t, doc.getFieldValue(field));
					} catch (Exception e) {
						if (!_VERSION_.equals(field)) {
							t.getMap().put(field, doc.getFieldValue(field));
						}
					}
				}
				result.add(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 通用做法(可以处理动态列，自己对QueryResponse进行处理)
	 * 
	 * @param singeMap
	 * @param multiMap
	 * @param rangeMap
	 * @param sortMap
	 * @param start
	 * @param limit
	 * @return
	 * @throws SolrServerException
	 * @throws IOException
	 */
	private QueryResponse queryDocument(Map<String, Object[]> multiMap, Map<String, Object[]> rangeMap,
			Map<String, String> sortMap, Integer start, Integer limit) throws SolrServerException, IOException {
		start = start == null ? _SOLR_PAGE_START_DEFAULT_ : start;
		limit = limit == null ? _SOLR_PAGE_ROWS_DEFAULT_ : limit;
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer queryBuffer;
		if (multiMap != null && !multiMap.isEmpty()) {
			for (String key : multiMap.keySet()) {
				queryBuffer = new StringBuffer();
				Object[] object = multiMap.get(key);
				for (int i = 0; i < object.length; i++) {
					if (i != 0) {
						queryBuffer.append(" OR ");
					}
					queryBuffer.append(key).append(":").append(object[i].toString().replace(":", "\\:"));
				}
				solrQuery.addFilterQuery(queryBuffer.toString());
			}
		}

		if (rangeMap != null && !rangeMap.isEmpty()) {
			for (String key : rangeMap.keySet()) {
				queryBuffer = new StringBuffer();
				Object[] range = rangeMap.get(key);
				queryBuffer.append(key).append(":[").append(range[0] == null ? "*" : range[0]).append(" TO ")
						.append(range[1] == null ? "*" : range[1]).append("]");
				solrQuery.addFilterQuery(queryBuffer.toString());
			}
		}

		solrQuery.setQuery(_SOLR_QUERY_DEFAULT_);
		solrQuery.setStart(start);// 从第几条开始
		solrQuery.setRows(limit);// 每页显示多少条
		// 排序
		if (sortMap != null) {
			List<SortClause> sorts = new ArrayList<SortClause>();
			SortClause sort;
			for (String key : sortMap.keySet()) {
				sort = new SortClause(key, sortMap.get(key) == null ? _SOLR_SORT_ASC_ : (Arrays.asList(_SOLR_SORT_RANGE_).contains(sortMap.get(key)) ? sortMap.get(key) : _SOLR_SORT_ASC_));
				sorts.add(sort);
			}
			solrQuery.setSorts(sorts);// 排序
		}
		return solrClient.query(solrQuery);
	}
}
