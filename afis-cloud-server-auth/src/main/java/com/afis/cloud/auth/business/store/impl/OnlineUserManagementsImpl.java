package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;

import com.afis.cloud.auth.business.store.OnlineUserManagements;
import com.afis.cloud.auth.config.KafkaConfig;
import com.afis.cloud.auth.model.protocol.AdminOnlineUserFrontRequest;
import com.afis.cloud.auth.model.protocol.AdminUserResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.dao.auth.OnlineUserDAO;
import com.afis.cloud.entities.dao.auth.UserDAO;
import com.afis.cloud.entities.dao.impl.auth.OnlineUserDAOImpl;
import com.afis.cloud.entities.dao.impl.auth.UserDAOImpl;
import com.afis.cloud.entities.model.auth.OnlineUserExample;
import com.afis.cloud.entities.model.auth.OnlineUserSelective;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.entities.model.auth.UserExample;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.utils.BizHelper;
import com.afis.cloud.utils.SessionUtil;

public class OnlineUserManagementsImpl extends AbstractManagementsImpl implements OnlineUserManagements {

	private OnlineUserDAO onlineUserDAO = new OnlineUserDAOImpl(sqlMapClient);

	private UserDAO userDAO = new UserDAOImpl(sqlMapClient);

	private KafkaTemplate kafkaTemplate;

	private AuthCacheUtil authCacheUtil;

	public OnlineUserManagementsImpl() {
	}

	public OnlineUserManagementsImpl(KafkaTemplate kafkaTemplate, AuthCacheUtil authCacheUtil) {
		this.kafkaTemplate = kafkaTemplate;
		this.authCacheUtil = authCacheUtil;
	}

	@Override
	public void deleteOnlineUserByLoginKey(String loginKey) throws SQLException {
		onlineUserDAO.deleteByPrimaryKey(loginKey);
	}

	@Override
	public void deleteOnlineUserByUserId(long userId) throws SQLException {
		OnlineUserExample example = new OnlineUserExample();
		example.createCriteria().andUserIdEqualTo(userId);
		onlineUserDAO.deleteByExample(example);
	}

	@Override
	public void insertOnlineUser(String loginKey, long userId) throws SQLException {
		OnlineUserSelective selective = new OnlineUserSelective();
		selective.setLoginKey(loginKey);
		selective.setUserId(userId);
		selective.setLoginTime(new Date());
		onlineUserDAO.insert(selective);
	}

	@Override
	public PageResult<AdminUserResponse> getOnlineUsers(Map<String, Object> map, int start, int limit)
			throws SQLException, CommonException {
		PageResult<AdminUserResponse> list = null;
		try {
			list = this.page(OnlineUserManagements.SQLMAP_NAMESPACE + ".getOnlineUsers", map, start, limit);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "在线用户列表查询");
		}
		return list;
	}

	@Override
	public void removeOnlineUser(String functionUrl, AdminOnlineUserFrontRequest param)
			throws SQLException, CommonException {

		UserExample example = new UserExample();
		example.createCriteria().andUserAccountEqualTo(param.getUserAccount());
		List<User> users = userDAO.selectByExample(example);
		User user = (users == null) ? null : users.get(0);
		if (user != null) {
			if (CommonConstants.ClientType.MANAGER.getKey().equals(user.getClientType())) {
				authCacheUtil.removeOnlineUserWithLoginKeyFromRedis(param.getLoginKey(), param.getUserAccount(), true);
			} else if (CommonConstants.ClientType.TRADER.getKey().equals(user.getClientType())) {
				authCacheUtil.removeOnlineUserWithLoginKeyFromRedis(param.getLoginKey(), param.getUserAccount(), false);
			}
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(param.getUserDetails()), user.getOperateAppId(),
							SessionUtil.getOperator(param.getUserDetails()), "踢出用户" + param.getUserAccount() + "成功"));
		} else {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(param.getUserDetails()), user.getOperateAppId(),
							SessionUtil.getOperator(param.getUserDetails()), "踢出用户" + param.getUserAccount() + "失败"));
			CommonException exception = new CommonException(CommonException.ANY_DESC);
			exception.setDesc("踢出用户ID不存在");
			throw exception;
		}
	}
}
