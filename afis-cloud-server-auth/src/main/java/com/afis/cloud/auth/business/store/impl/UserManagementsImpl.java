package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.afis.cloud.auth.business.store.UserManagements;
import com.afis.cloud.auth.config.KafkaConfig;
import com.afis.cloud.auth.model.protocol.AdminUserFrontRequest;
import com.afis.cloud.auth.model.protocol.AdminUserResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.dao.auth.ApplicationDAO;
import com.afis.cloud.entities.dao.auth.UserDAO;
import com.afis.cloud.entities.dao.impl.auth.ApplicationDAOImpl;
import com.afis.cloud.entities.dao.impl.auth.UserDAOImpl;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.entities.model.auth.UserExample;
import com.afis.cloud.entities.model.auth.UserSelective;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.utils.BizHelper;
import com.afis.cloud.utils.SessionUtil;
import com.afis.utils.MD5;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/1 17:57
 */
public class UserManagementsImpl extends AbstractManagementsImpl implements UserManagements {

	private UserDAO userDao = new UserDAOImpl(sqlMapClient);

	private ApplicationDAO applicationDAO = new ApplicationDAOImpl(sqlMapClient);

	private KafkaTemplate kafkaTemplate;

	private AuthCacheUtil authCacheUtil;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public UserManagementsImpl(KafkaTemplate kafkaTemplate, AuthCacheUtil authCacheUtil) {
		this.kafkaTemplate = kafkaTemplate;
		this.authCacheUtil = authCacheUtil;
	}

	@Override
	public PageResult<AdminUserResponse> getUsers(String functionUrl, Map<String, Object> map, int start, int limit)
			throws SQLException, CommonException {
		PageResult<AdminUserResponse> list = null;
		try {
			list = this.page(UserManagements.SQLMAP_NAMESPACE + ".getUsers", map, start, limit);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户列表查询");
		}
		return list;
	}

	@Override
	public User addUser(String functionUrl, AdminUserFrontRequest user) throws SQLException, CommonException {
		// 验证账号是否存在
		checkUserAccount(user, functionUrl);
		// 校验应用状态是否正常
		Application application = applicationDAO.selectByPrimaryKey(user.getRegisterAppId());
		if (CommonConstants.Status.INVALID.getKey().equals(application.getStatus())) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
							SessionUtil.getOperator(user.getUserDetails()),
							"注册用户" + user.getUserAccount() + "失败，注册应用状态无效"));
			CommonException exception = new CommonException(CommonException.ANY_DESC);
			exception.setDesc("注册应用状态无效");
			throw exception;
		}
		// 封装数据
		UserSelective selective = new UserSelective();
		// 校验字段长度
		checkParamLength(functionUrl, user, user.getUserAccount(), 20, "注册用户", "用户账号输入字段过长");
		checkParamLength(functionUrl, user, user.getUserName(), 60, "注册用户", "用户名称输入字段过长");
		checkParamLength(functionUrl, user, user.getPassword(), 20, "注册用户", "密码输入字段过长");
		checkParamLength(functionUrl, user, user.getRemark(), 200, "注册用户", "备注输入字段过长");
		selective.setUserAccount(user.getUserAccount());
		selective.setUserName(user.getUserName());
		selective.setPassword(MD5.code16(user.getUserAccount() + user.getPassword()));
		selective.setMobile(user.getMobile());
		selective.setClientType(user.getClientType());
		selective.setStatus(CommonConstants.OperatorMemberStatus.Valid.getKey());
		selective.setRemark(user.getRemark());
		selective.setRegisterAppId(user.getRegisterAppId());
		selective.setRegisterOperator(SessionUtil.getOperator(user.getUserDetails()));
		selective.setRegisterTime(new Date());
		selective.setOperateAppId(user.getOperateAppId());
		selective.setOperator(SessionUtil.getOperator(user.getUserDetails()));
		selective.setOperateTime(new Date());
		selective.setLastLoginIp(SessionUtil.getLoginIp(user.getUserDetails()));
		selective.setLastLoginTime(new Date());
		Long id = null;
		try {
			id = userDao.insert(selective);
		} catch (SQLException e) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
							SessionUtil.getOperator(user.getUserDetails()), "注册用户" + user.getUserAccount() + "失败"));
			BizHelper.handleDBException(e, "用户注册");
		}
		User userResponse = userDao.selectByPrimaryKey(id);
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
						SessionUtil.getOperator(user.getUserDetails()), "注册用户" + userResponse.getUserAccount() + "成功"));
		SessionUtil.addDebugLog(logger, "新增用户id:{}", id);
		return userResponse;
	}

	@Override
	public User editUser(String functionUrl, AdminUserFrontRequest user) throws SQLException, CommonException {

		// 验证账号是否存在
		User userModel = checkUserById(user);
		// 校验字段长度
		checkParamLength(functionUrl, user, user.getUserAccount(), 20, "编辑用户", "用户账号输入字段过长");
		checkParamLength(functionUrl, user, user.getUserName(), 60, "编辑用户", "用户名称输入字段过长");
		checkParamLength(functionUrl, user, user.getPassword(), 20, "编辑用户", "密码输入字段过长");
		checkParamLength(functionUrl, user, user.getRemark(), 200, "编辑用户", "备注输入字段过长");
		// 封装数据
		UserSelective selective = new UserSelective();
		selective.setId(user.getId());
		if (user.getUserName() != null) {
			selective.setUserName(user.getUserName());
		}
		if (user.getMobile() != null) {
			selective.setMobile(user.getMobile());
		} else {
			selective.setMobile("");
		}
		if (user.getRemark() != null) {
			selective.setRemark(user.getRemark());
		} else {
			selective.setRemark("");
		}
		if (user.getPassword() != null) {
			selective.setPassword(MD5.code16(userModel.getUserAccount() + user.getPassword()));
		}
		selective.setOperateAppId(user.getOperateAppId());
		selective.setOperator(SessionUtil.getOperator(user.getUserDetails()));
		selective.setOperateTime(new Date());
		try {
			userDao.updateByPrimaryKey(selective);
		} catch (SQLException e) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
							SessionUtil.getOperator(user.getUserDetails()), "用户编辑" + user.getUserAccount() + "失败"));
			BizHelper.handleDBException(e, "用户编辑");
		}
		User userResponse = userDao.selectByPrimaryKey(user.getId());
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
						SessionUtil.getOperator(user.getUserDetails()),
						"编辑用户 " + userResponse.getUserAccount() + " 成功"));
		return userResponse;
	}

	private void checkParamLength(String functionUrl, AdminUserFrontRequest user, String param, int i, String even,
			String desc) throws CommonException, SQLException {
		if (param != null) {
			if (param.getBytes().length > i) {
				kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
						createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
								SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
								SessionUtil.getOperator(user.getUserDetails()), even + user.getUserAccount() + "失败"));
				CommonException e = new CommonException(CommonException.ANY_DESC);
				e.setDesc(desc);
				throw e;
			}
		}
	}

	@Override
	public AdminUserResponse getUserById(String functionUrl, Long id) throws SQLException {
		return this.queryForObject(UserManagements.SQLMAP_NAMESPACE + ".getUsersById", id);
	}

	private User checkUserById(AdminUserFrontRequest user) throws SQLException, CommonException {
		User userById = userDao.selectByPrimaryKey(user.getId());
		checkAccountById(userById);
		return userById;
	}

	@Override
	public User frozenUser(String functionUrl, long id, AdminUserFrontRequest userParam)
			throws SQLException, CommonException {
		User user = userDao.selectByPrimaryKey(id);
		checkAccountById(user);
		if (!CommonConstants.OperatorMemberStatus.Valid.getKey().equals(user.getStatus())) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
							SessionUtil.getOperator(userParam.getUserDetails()),
							"冻结用户 " + user.getUserAccount() + " 失败"));
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
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
							SessionUtil.getOperator(userParam.getUserDetails()),
							"冻结用户 " + user.getUserAccount() + " 失败"));
			BizHelper.handleDBException(e, "用户冻结");
		}
		User userResponse = userDao.selectByPrimaryKey(user.getId());
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
						SessionUtil.getOperator(userParam.getUserDetails()),
						"冻结用户 " + userResponse.getUserAccount() + " 成功"));
		return userResponse;
	}

	private void checkAccountById(User user) throws CommonException {
		if (user == null) {
			CommonException exception = new CommonException(CommonException.NOT_EXISTS);
			exception.setDesc("用户账号");
			throw exception;
		}
	}

	@Override
	public User thawUser(String functionUrl, long id, AdminUserFrontRequest userParam)
			throws SQLException, CommonException {
		User user = userDao.selectByPrimaryKey(id);
		checkAccountById(user);
		if (!CommonConstants.OperatorMemberStatus.Frozen.getKey().equals(user.getStatus())) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
							SessionUtil.getOperator(userParam.getUserDetails()),
							"解冻用户 " + user.getUserAccount() + " 失败"));
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
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
							SessionUtil.getOperator(userParam.getUserDetails()),
							"解冻用户 " + user.getUserAccount() + " 失败"));
			BizHelper.handleDBException(e, "用户解冻");
		}
		User userResponse = userDao.selectByPrimaryKey(user.getId());
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
						SessionUtil.getOperator(userParam.getUserDetails()),
						"解冻用户 " + userResponse.getUserAccount() + " 成功"));
		return userResponse;
	}

	@Override
	public User cancelUser(String functionUrl, long id, AdminUserFrontRequest userParam)
			throws SQLException, CommonException {
		User user = userDao.selectByPrimaryKey(id);
		checkAccountById(user);
		if (CommonConstants.OperatorMemberStatus.Invalid.getKey().equals(user.getStatus())) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
							SessionUtil.getOperator(userParam.getUserDetails()),
							"注销用户 " + user.getUserAccount() + " 失败"));
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
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
							SessionUtil.getOperator(userParam.getUserDetails()),
							"注销用户 " + user.getUserAccount() + " 失败"));
			BizHelper.handleDBException(e, "用户注销");
		}
		User userResponse = userDao.selectByPrimaryKey(user.getId());
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
						SessionUtil.getOperator(userParam.getUserDetails()),
						"注销用户 " + userResponse.getUserAccount() + " 成功"));
		return userResponse;
	}

	@Override
	public User unlockUser(String functionUrl, long id, int failNum, AdminUserFrontRequest userParam)
			throws SQLException, CommonException {

		User user = userDao.selectByPrimaryKey(id);
		checkAccountById(user);
		// 判断用户方是否被锁定
		if (authCacheUtil.getFailNum(user.getUserAccount()) < failNum) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
							SessionUtil.getOperator(userParam.getUserDetails()),
							"解锁用户" + user.getUserAccount() + "失败"));
			CommonException exception = new CommonException(CommonException.ANY_DESC);
			exception.setDesc("用户未锁定，无需解锁");
			throw exception;
		} else {
			authCacheUtil.removeFailNum(user.getUserAccount());
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(userParam.getUserDetails()), userParam.getOperateAppId(),
							SessionUtil.getOperator(userParam.getUserDetails()),
							"解锁用户" + user.getUserAccount() + "成功"));
		}
		return user;
	}

	@Override
	public List<User> getUsersByUserName(String userAccount) throws SQLException, CommonException {
		List<User> list = null;
		UserExample example = new UserExample();
		example.createCriteria().andUserNameLike("%" + userAccount + "%");
		try {
			list = userDao.selectByExample(example);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户列表查询");
		}
		return list;
	}

	private void checkUserAccount(AdminUserFrontRequest user, String functionUrl) throws SQLException, CommonException {
		UserExample example = new UserExample();
		example.createCriteria().andUserAccountEqualTo(user.getUserAccount())
				.andClientTypeEqualTo(user.getClientType());
		List<User> list = userDao.selectByExample(example);
		if (list != null && list.size() > 0) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
							SessionUtil.getOperator(user.getUserDetails()), "注册用户" + user.getUserAccount() + "失败"));
			CommonException exception = new CommonException(CommonException.CANNOT_DUPLIATED);
			exception.setDesc("用户账号");
			throw exception;
		}
	}

}
