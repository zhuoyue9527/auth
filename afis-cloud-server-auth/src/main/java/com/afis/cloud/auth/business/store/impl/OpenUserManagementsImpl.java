package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;

import com.afis.cloud.auth.business.store.OpenUserManagements;
import com.afis.cloud.auth.config.KafkaConfig;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.protocol.open.request.AbstractInterfaceRequest;
import com.afis.cloud.auth.model.protocol.open.request.UserRequest;
import com.afis.cloud.auth.model.protocol.open.response.UserResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.entities.dao.auth.AppUserDAO;
import com.afis.cloud.entities.dao.auth.UserDAO;
import com.afis.cloud.entities.dao.impl.auth.AppUserDAOImpl;
import com.afis.cloud.entities.dao.impl.auth.UserDAOImpl;
import com.afis.cloud.entities.model.auth.AppUser;
import com.afis.cloud.entities.model.auth.AppUserExample;
import com.afis.cloud.entities.model.auth.AppUserSelective;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.entities.model.auth.UserSelective;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.utils.BizHelper;
import com.afis.utils.MD5;

public class OpenUserManagementsImpl extends AbstractManagementsImpl implements OpenUserManagements {

	private UserDAO userDao = new UserDAOImpl(sqlMapClient);

	private AppUserDAO appUserDao = new AppUserDAOImpl(sqlMapClient);

	private KafkaTemplate kafkaTemplate;

	private AuthCacheUtil authCacheUtil;

	public OpenUserManagementsImpl(KafkaTemplate kafkaTemplate, AuthCacheUtil authCacheUtil) {
		this.kafkaTemplate = kafkaTemplate;
		this.authCacheUtil = authCacheUtil;
	}

	@Override
	public User addUser(String functionUrl, UserRequest userRequest)
			throws SQLException, AuthenticationException, CommonException {
		try {
			ApplicationModel application = authCacheUtil.getApplicationFromRedis(userRequest.getAppCode());
			long userId = addUserOnly(userRequest, application.getId());
			addAppUser(userId, application.getId());
			// 记录操作日志
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"添加用户" + userRequest.getUserName() + "成功"));
			return userDao.selectByPrimaryKey(userId);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户账号");
		}
		return null;
	}

	private boolean checkAppUserIsEmpty(long userId, long appId) throws SQLException {
		AppUserExample example = new AppUserExample();
		example.createCriteria().andUserIdEqualTo(userId).andAppIdEqualTo(appId);
		List<AppUser> list = appUserDao.selectByExample(example);
		return list == null || list.isEmpty() ? Boolean.TRUE : Boolean.FALSE;
	}

	@Override
	public User patchUser(String functionUrl, UserRequest userRequest, Long id)
			throws SQLException, CommonException, AuthenticationException {
		User user = userDao.selectByPrimaryKey(id);
		checkAccountById(user);
		// 判断获取的id是否是调用应用授权的用户信息
		ApplicationModel application = authCacheUtil.getApplicationFromRedis(userRequest.getAppCode());
		if (checkAppUserIsEmpty(user.getId(), application.getId())) {
			throw new CommonException(CommonException.ANY_DESC, "该用户不是应用的授权用户，不能修改");
		}
		try {
			UserSelective selective = new UserSelective();
			selective.setId(id);
			if (!"".equals(userRequest.getPassword()) && userRequest.getPassword() != null) {
				selective.setPassword(
						MD5.code16(userRequest.getUserAccount().trim() + userRequest.getPassword().trim()));
			}
			selective.setMobile(userRequest.getMobile());
			selective.setRemark(userRequest.getRemark());
			userDao.updateByPrimaryKey(selective);
			// 记录操作日志
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"用户" + userRequest.getUserName() + "编辑成功"));
			return user;
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户账号");
		}
		return null;
	}

	@Override
	public UserResponse getUser(String token, String appCode, Long id)
			throws SQLException, CommonException, AuthenticationException {
		// 判断获取的id是否是调用应用授权的用户信息
		if (checkAppUserIsEmpty(authCacheUtil.getToken(token, appCode).getUserId(),
				authCacheUtil.getApplicationFromRedis(appCode).getId())) {
			throw new CommonException(CommonException.ANY_DESC, "该用户不是应用的授权用户，不能修改");
		}
		try {
			return this.queryForObject(OpenUserManagements.SQLMAP_NAMESPACE + ".getUserById", id);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户账号");
		}
		return null;
	}

	@Override
	public User cancelUser(String functionUrl, Long id, AbstractInterfaceRequest userRequest)
			throws SQLException, CommonException, AuthenticationException {
		User user = userDao.selectByPrimaryKey(id);
		checkAccountById(user);
		// 判断获取的id是否是调用应用授权的用户信息
		if (checkAppUserIsEmpty(authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
				authCacheUtil.getApplicationFromRedis(userRequest.getAppCode()).getId())) {
			throw new CommonException(CommonException.ANY_DESC, "该用户不是应用的授权用户，不能注销");
		}
		ApplicationModel application = authCacheUtil.getApplicationFromRedis(userRequest.getAppCode());
		if (CommonConstants.OperatorMemberStatus.Invalid.getKey().equals(user.getStatus())) {
			// 记录操作日志
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"用户" + user.getUserAccount() + "注销失败"));
			CommonException exception = new CommonException(CommonException.LOGOUT_FAIL);
			exception.setDesc("用户账号");
			throw exception;
		}
		UserSelective selective = new UserSelective();
		selective.setId(id);
		selective.setStatus(CommonConstants.OperatorMemberStatus.Invalid.getKey());
		try {
			userDao.updateByPrimaryKey(selective);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户注销");
		}
		// 记录操作日志
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
						authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
						"用户" + user.getUserAccount() + "注销成功"));
		return user;
	}

	@Override
	public User frozenUser(String functionUrl, Long id, AbstractInterfaceRequest userRequest)
			throws SQLException, CommonException, AuthenticationException {
		User user = userDao.selectByPrimaryKey(id);
		checkAccountById(user);
		// 判断获取的id是否是调用应用授权的用户信息
		if (checkAppUserIsEmpty(authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
				authCacheUtil.getApplicationFromRedis(userRequest.getAppCode()).getId())) {
			throw new CommonException(CommonException.ANY_DESC, "该用户不是应用的授权用户，不能冻结");
		}

		ApplicationModel application = authCacheUtil.getApplicationFromRedis(userRequest.getAppCode());
		if (!CommonConstants.OperatorMemberStatus.Valid.getKey().equals(user.getStatus())) {
			// 记录操作日志
			kafkaTemplate
					.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null,
									application.getOperateAppId(), authCacheUtil
											.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
									"用户" + user.getUserAccount() + "冻结失败"));
			CommonException exception = new CommonException(CommonException.OBJ_ONLY_STATUS_CAN_DO_SOMETHING);
			exception.setDesc("正常", "用户账号", "冻结");
			throw exception;
		}
		UserSelective selective = new UserSelective();
		selective.setId(id);
		selective.setStatus(CommonConstants.OperatorMemberStatus.Frozen.getKey());
		try {
			userDao.updateByPrimaryKey(selective);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户冻结");
		}
		// 记录操作日志
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null,
						application.getOperateAppId(),
						authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
						"用户" + user.getUserAccount() + "冻结成功"));
		return user;
	}

	@Override
	public User thawUser(String functionUrl, Long id, AbstractInterfaceRequest userRequest)
			throws SQLException, CommonException, AuthenticationException {
		User user = userDao.selectByPrimaryKey(id);
		checkAccountById(user);
		// 判断获取的id是否是调用应用授权的用户信息
		if (checkAppUserIsEmpty(authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
				authCacheUtil.getApplicationFromRedis(userRequest.getAppCode()).getId())) {
			throw new CommonException(CommonException.ANY_DESC, "该用户不是应用的授权用户，不能解冻");
		}
		ApplicationModel application = authCacheUtil.getApplicationFromRedis(userRequest.getAppCode());
		if (!CommonConstants.OperatorMemberStatus.Frozen.getKey().equals(user.getStatus())) {
			// 记录操作日志
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"用户" + user.getUserAccount() + "解冻失败"));
			CommonException exception = new CommonException(CommonException.OBJ_ONLY_STATUS_CAN_DO_SOMETHING);
			exception.setDesc("冻结", "用户账号", "解冻");
			throw exception;
		}
		UserSelective selective = new UserSelective();
		selective.setId(id);
		selective.setStatus(CommonConstants.OperatorMemberStatus.Valid.getKey());
		try {
			userDao.updateByPrimaryKey(selective);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户解冻");
		}
		// 记录操作日志
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
						authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
						"用户" + user.getUserAccount() + "解冻成功"));
		return user;
	}

	@Override
	public User unlockUser(String functionUrl, Long id, int failNum, AbstractInterfaceRequest userRequest)
			throws SQLException, CommonException, AuthenticationException {
		User user = userDao.selectByPrimaryKey(id);
		checkAccountById(user);
		// 判断获取的id是否是调用应用授权的用户信息
		if (checkAppUserIsEmpty(authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
				authCacheUtil.getApplicationFromRedis(userRequest.getAppCode()).getId())) {
			throw new CommonException(CommonException.ANY_DESC, "该用户不是应用的授权用户，不能解冻");
		}
		ApplicationModel application = authCacheUtil.getApplicationFromRedis(userRequest.getAppCode());
		// 判断用户方是否被锁定
		if (authCacheUtil.getFailNum(user.getUserAccount()) < failNum) {
			// 记录操作日志
			kafkaTemplate
					.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null,
									application.getOperateAppId(), authCacheUtil
											.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
									"用户" + user.getUserAccount() + "解锁失败"));
			CommonException exception = new CommonException(CommonException.ANY_DESC);
			exception.setDesc("用户未锁定，无需解锁");
			throw exception;
		} else {
			authCacheUtil.removeFailNum(user.getUserAccount());
			// 记录操作日志
			kafkaTemplate
					.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null,
									application.getOperateAppId(), authCacheUtil
											.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
									"用户" + user.getUserAccount() + "解锁成功"));
		}
		return user;
	}

	private long addUserOnly(UserRequest request, long registerAppId) throws SQLException {
		UserSelective selective = new UserSelective();
		selective.setUserAccount(request.getUserAccount().trim());
		selective.setUserName(request.getUserName());
		selective.setPassword(MD5.code16(request.getUserAccount().trim() + request.getPassword().trim()));
		selective.setClientType(CommonConstants.ClientType.TRADER.getKey());
		selective.setMobile(request.getMobile());
		selective.setStatus(CommonConstants.OperatorMemberStatus.Valid.getKey());
		selective.setRemark(request.getRemark());
		selective.setRegisterAppId(registerAppId);
		selective.setRegisterTime(new Date());
		return userDao.insert(selective);
	}

	private void addAppUser(long userId, long appId) throws SQLException {
		AppUserSelective selective = new AppUserSelective();
		selective.setAppId(appId);
		selective.setUserId(userId);
		selective.setWarrantPermit(CommonConstants.TrueOrFalse.TRUE.getKey());// 是否允许授权
		selective.setWarrant(CommonConstants.TrueOrFalse.TRUE.getKey());// 是否已授权
		appUserDao.insert(selective);
	}

	private void checkAccountById(User user) throws CommonException {
		if (user == null) {
			CommonException exception = new CommonException(CommonException.NOT_EXISTS);
			exception.setDesc("用户账号");
			throw exception;
		}
	}
}
