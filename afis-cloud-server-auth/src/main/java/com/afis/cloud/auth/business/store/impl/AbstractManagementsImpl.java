package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.afis.cloud.auth.model.kafka.OperateLogModel;
import com.afis.cloud.auth.util.AuthConstants;
import com.afis.cloud.business.impl.BusinessCore;
import com.afis.cloud.entities.model.auth.Function;

public class AbstractManagementsImpl extends BusinessCore {

	protected Logger logger = LoggerFactory.getLogger(getClass());


	private OperateLogModel createOperateLog(Function function, String status, String loginIp, Long operateAppId,
			Long operator, String remark) {

		OperateLogModel operateLogModel = new OperateLogModel();
		operateLogModel.setFunctionId(function.getId());
		operateLogModel.setLoginIp(loginIp);
		operateLogModel.setStatus(status);
		operateLogModel.setRemark(remark);
		operateLogModel.setOperateAppId(operateAppId);
		operateLogModel.setOperator(operator);
		operateLogModel.setOperateTime(new Date());
		logger.debug("operateLogModel:{}", operateLogModel);
		return operateLogModel;
	}

	
	/**
	 * 
	 * @param function
	 * @param loginIp			登录ip
	 * @param operateAppId		操作应用
	 * @param operator			操作员
	 * @param remark			备注
	 * @return
	 * @throws SQLException
	 */
	protected OperateLogModel createSuccessLog(Function function, String loginIp, Long operateAppId, Long operator,
			String remark) throws SQLException {
		return createOperateLog(function, AuthConstants.OperateStatus.SUCCESS.getKey(), loginIp, operateAppId, operator,
				remark);
	}

	/**
	 * 
	 * @param function
	 * @param loginIp			登录ip
	 * @param operateAppId		操作应用
	 * @param operator			操作员
	 * @param remark			备注
	 * @return
	 * @throws SQLException
	 */
	protected OperateLogModel createFailLog(Function function, String loginIp, Long operateAppId, Long operator,
			String remark) throws SQLException {
		return createOperateLog(function, AuthConstants.OperateStatus.FAIL.getKey(), loginIp, operateAppId, operator,
				remark);
	}
}
