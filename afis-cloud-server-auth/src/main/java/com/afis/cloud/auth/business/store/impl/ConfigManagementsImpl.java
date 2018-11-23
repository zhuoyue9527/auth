package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.List;

import com.afis.cloud.auth.business.store.ConfigManagements;
import com.afis.cloud.auth.model.protocol.AdminConfigFrontRequest;
import com.afis.cloud.business.impl.BusinessCore;
import com.afis.cloud.entities.dao.auth.TableConfigDAO;
import com.afis.cloud.entities.dao.impl.auth.TableConfigDAOImpl;
import com.afis.cloud.entities.model.auth.TableConfig;
import com.afis.cloud.entities.model.auth.TableConfigExample;
import com.afis.cloud.entities.model.auth.TableConfigSelective;
import com.afis.cloud.exception.CommonException;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/1 17:57
 */
public class ConfigManagementsImpl extends BusinessCore implements ConfigManagements {

	private TableConfigDAO tableConfigDao = new TableConfigDAOImpl(sqlMapClient);

	@Override
	public TableConfig getTableConfig(Long operateId, Long appId, String uid) throws SQLException {
		TableConfigExample example = new TableConfigExample();
		example.createCriteria().andAppIdEqualTo(appId).andObjIdEqualTo(uid).andOperatorEqualTo(operateId);
		List<TableConfig> list = tableConfigDao.selectByExample(example);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	@Override
	public TableConfig insertOrUpdateTableConfig(AdminConfigFrontRequest configParam)
			throws SQLException, CommonException {
		TableConfigExample example = new TableConfigExample();
		example.createCriteria().andAppIdEqualTo(configParam.getAppId()).andObjIdEqualTo(configParam.getUid())
				.andOperatorEqualTo(configParam.getUserDetails().getId());

		TableConfigSelective selective = new TableConfigSelective();
		selective.setAppId(configParam.getAppId());
		selective.setColumns(configParam.getColumns());
		selective.setObjId(configParam.getUid());
		selective.setOperator(configParam.getUserDetails().getId());

		if (tableConfigDao.updateByExample(selective, example) < 1) {
			tableConfigDao.insert(selective);
		}

		return tableConfigDao.selectByExample(example).get(0);
	}

}
