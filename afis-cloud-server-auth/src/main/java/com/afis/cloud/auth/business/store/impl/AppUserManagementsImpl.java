package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;

import com.afis.cloud.auth.business.store.AppUserManagements;
import com.afis.cloud.auth.config.KafkaConfig;
import com.afis.cloud.auth.model.protocol.AdminAppUserResponse;
import com.afis.cloud.auth.model.protocol.AdminUserAppFrontRequest;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.dao.auth.AppUserDAO;
import com.afis.cloud.entities.dao.auth.ApplicationDAO;
import com.afis.cloud.entities.dao.auth.FunctionDAO;
import com.afis.cloud.entities.dao.impl.auth.AppUserDAOImpl;
import com.afis.cloud.entities.dao.impl.auth.ApplicationDAOImpl;
import com.afis.cloud.entities.dao.impl.auth.FunctionDAOImpl;
import com.afis.cloud.entities.model.auth.AppUser;
import com.afis.cloud.entities.model.auth.AppUserExample;
import com.afis.cloud.entities.model.auth.AppUserSelective;
import com.afis.cloud.entities.model.auth.Application;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.utils.BizHelper;
import com.afis.cloud.utils.SessionUtil;
import com.afis.cloud.utils.ValidateScreenUtil;
import com.afis.utils.MD5;
import com.afis.web.modal.UserDetails;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/1 17:57
 */
public class AppUserManagementsImpl extends AbstractManagementsImpl implements AppUserManagements {

	private AppUserDAO appUserDao = new AppUserDAOImpl(sqlMapClient);

	private FunctionDAO functionDAO = new FunctionDAOImpl(sqlMapClient);

	private ApplicationDAO applicationDAO = new ApplicationDAOImpl(sqlMapClient);

	private KafkaTemplate kafkaTemplate;

	private AuthCacheUtil authCacheUtil;

	public AppUserManagementsImpl(KafkaTemplate kafkaTemplate, AuthCacheUtil authCacheUtil) {
		this.kafkaTemplate = kafkaTemplate;
		this.authCacheUtil = authCacheUtil;
	}

	@Override
	public PageResult<AdminAppUserResponse> getAppUserPermissons(Map<String, Object> map, int start, int limit)
			throws SQLException {
		PageResult<AdminAppUserResponse> list = this.page(AppUserManagements.SQLMAP_NAMESPACE + ".getAppUsers", map,
				start, limit);
		return list;
	}

	@Override
	public List<Application> getUserApps(Long userId) throws SQLException {
		List<Application> list = this.queryForList(AppUserManagements.SQLMAP_NAMESPACE + ".getUserApps", userId);
		return list;
	}

	@Override
	public void addAppUserPermissons(String functionUrl, AdminUserAppFrontRequest user)
			throws SQLException, CommonException {
		// 用户授权的应用是否存在
		if (user.getAppIdList() != null && user.getAppIdList().size() > 0) {
			for (int i = 0; i < user.getAppIdList().size(); i++) {
				Long appId = user.getAppIdList().get(i);
				Application application = applicationDAO.selectByPrimaryKey(appId);
				if (application == null) {
					kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
									SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
									SessionUtil.getOperator(user.getUserDetails()),
									user.getUserId() + "用户授权权限添加失败，应用不存在"));
					CommonException exception = new CommonException(CommonException.NOT_EXISTS);
					exception.setDesc("应用ID" + appId);
					throw exception;
				} else {
					// 封装数据
					AppUserSelective selective = new AppUserSelective();
					selective.setUserId(user.getUserId());
					selective.setAppId(appId);
					selective.setWarrant(CommonConstants.TrueOrFalse.FALSE.getKey());
					selective.setWarrantPermit(CommonConstants.TrueOrFalse.TRUE.getKey());
					selective.setOperator(SessionUtil.getOperator(user.getUserDetails()));
					selective.setOperateAppId(user.getOperateAppId());
					selective.setOperateTime(new Date());
					try {
						appUserDao.insert(selective);
					} catch (SQLException e) {
						BizHelper.handleDBException(e, "用户授权权限添加");
					}
					kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
									SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
									SessionUtil.getOperator(user.getUserDetails()),
									user.getUserId() + "用户授权权限" + application.getAppName() + "添加成功"));
				}
			}
		}
	}

	@Override
	public void deleteAppUserPermissons(String functionUrl, Long id, AdminUserAppFrontRequest user)
			throws SQLException, CommonException {
		AppUser appUser = appUserDao.selectByPrimaryKey(id);
		ValidateScreenUtil.validateObject(appUser, "该授权信息ID");
		// 校验授权权限是否已删除
		if (CommonConstants.TrueOrFalse.FALSE.getKey().equals(appUser.getWarrantPermit())) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
							SessionUtil.getOperator(user.getUserDetails()), "该授权权限已删除，无需再次操作"));
			CommonException exception = new CommonException(CommonException.ANY_DESC);
			exception.setDesc("该授权权限已删除，无需再次操作");
			throw exception;
		}
		AppUserSelective selective = new AppUserSelective();
		selective.setId(id);
		selective.setWarrant(CommonConstants.TrueOrFalse.FALSE.getKey());
		selective.setWarrantPermit(CommonConstants.TrueOrFalse.FALSE.getKey());
		try {
			appUserDao.updateByPrimaryKey(selective);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户授权权限ID");
		}
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
						SessionUtil.getOperator(user.getUserDetails()), "用户授权权限删除成功"));
	}

	@Override
	public void appUserWarrant(Long userId, String functionUrl, AdminUserAppFrontRequest user)
			throws SQLException, CommonException, AuthenticationException {
		// 用户授权应用是否存在
		if (user.getAppIdList() != null && user.getAppIdList().size() > 0) {
			for (int i = 0; i < user.getAppIdList().size(); i++) {
				Long appId = user.getAppIdList().get(i);
				AppUserExample example = new AppUserExample();
				example.createCriteria().andAppIdEqualTo(appId).andUserIdEqualTo(userId);
				List<AppUser> list = appUserDao.selectByExample(example);
				AppUser appUser = (list == null ? null : list.get(0));
				if (appUser == null || CommonConstants.TrueOrFalse.FALSE.getKey().equals(appUser.getWarrantPermit())) {
					kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
									SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
									SessionUtil.getOperator(user.getUserDetails()), "用户的应用授权权限不存在或为不允许授权"));
					throw new AuthenticationException(AuthenticationException.AUTH_USER_AUTHORIZE_PERMIT);
				}
				AppUserSelective appUserSelective = new AppUserSelective();
				if (user.getAppPassword() != null) {
					appUserSelective.setAppPassword(user.getAppPassword());
				}
				appUserSelective.setWarrant(CommonConstants.TrueOrFalse.TRUE.getKey());
				AppUserExample appUserExample = new AppUserExample();
				appUserExample.createCriteria().andIdEqualTo(appUser.getId())
						.andWarrantPermitEqualTo(CommonConstants.TrueOrFalse.TRUE.getKey())
						.andWarrantEqualTo(CommonConstants.TrueOrFalse.FALSE.getKey());
				int count = 0;
				try {
					count = appUserDao.updateByExample(appUserSelective, example);
				} catch (SQLException e) {
					BizHelper.handleDBException(e, "用户列表查询");
				}
				if (count < 1) {
					kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
							createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
									SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
									SessionUtil.getOperator(user.getUserDetails()), "用户已经授权，不需要重复操作"));
					throw new AuthenticationException(AuthenticationException.AUTH_USER_AUTHORIZE_HAS_PERMIT);
				}
				kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
						createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
								SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
								SessionUtil.getOperator(user.getUserDetails()), "用户授权成功"));

			}
		}
	}

	@Override
	public AppUser editAppWarrantUserById(String functionUrl, Long id, AdminUserAppFrontRequest user)
			throws SQLException, CommonException, AuthenticationException {
		AppUserSelective selective = new AppUserSelective();
		selective.setWarrant(user.getWarrant());
		if (user.getAppPassword() != null) {
			selective.setAppPassword(MD5.code16(user.getUserDetails().getUserName() + user.getAppPassword()));
		}
		AppUserExample example = new AppUserExample();
		example.createCriteria().andIdEqualTo(id);
		int count = 0;
		try {
			count = appUserDao.updateByExample(selective, example);
		} catch (SQLException e) {
			BizHelper.handleDBException(e, "用户列表查询");
		}
		AppUser appUser = appUserDao.selectByPrimaryKey(id);
		if (count < 1) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
							SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
							SessionUtil.getOperator(user.getUserDetails()), "用户授权,修改失败"));
			throw new AuthenticationException(AuthenticationException.AUTH_USER_AUTHORIZE_HAS_PERMIT);
		}
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl),
						SessionUtil.getLoginIp(user.getUserDetails()), user.getOperateAppId(),
						SessionUtil.getOperator(user.getUserDetails()), "用户授权成功"));

		return appUser;
	}

	@Override
	public void appUserWarrant(UserDetails user, long applicaitonId, String password, String functionUrl)
			throws SQLException, AuthenticationException, CommonException {
		AppUser appUser = getAppUserByUK(user.getId(), applicaitonId);
		if (appUser == null || CommonConstants.TrueOrFalse.FALSE.getKey().equals(appUser.getWarrantPermit())) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), SessionUtil.getLoginIp(user),
							applicaitonId, SessionUtil.getOperator(user), "用户的应用授权权限不存在或为不允许授权"));
			throw new AuthenticationException(AuthenticationException.AUTH_USER_AUTHORIZE_PERMIT);
		}

		AppUserSelective selective = new AppUserSelective();
		selective.setWarrant(CommonConstants.TrueOrFalse.TRUE.getKey());
		// 设置应用密码
		if (password != null && password.trim().length() > 0) {
			selective.setAppPassword(MD5.code16(user.getUserName() + password.trim()));
		}

		AppUserExample example = new AppUserExample();
		example.createCriteria().andIdEqualTo(appUser.getId())
				.andWarrantPermitEqualTo(CommonConstants.TrueOrFalse.TRUE.getKey())
				.andWarrantEqualTo(CommonConstants.TrueOrFalse.FALSE.getKey());

		if (appUserDao.updateByExample(selective, example) < 1) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), SessionUtil.getLoginIp(user),
							applicaitonId, SessionUtil.getOperator(user), "用户已经授权，不需要重复操作"));
			throw new AuthenticationException(AuthenticationException.AUTH_USER_AUTHORIZE_HAS_PERMIT);
		}

		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
				createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), SessionUtil.getLoginIp(user),
						applicaitonId, SessionUtil.getOperator(user), "用户授权成功"));
	}

	private AppUser getAppUserByUK(long userId, long applicaitonId) throws SQLException {
		AppUserExample example = new AppUserExample();
		example.createCriteria().andAppIdEqualTo(applicaitonId).andUserIdEqualTo(userId);
		List<AppUser> list = appUserDao.selectByExample(example);
		return list == null ? null : list.get(0);
	}

	@Override
	public PageResult<AdminAppUserResponse> getAppUserWarrant(Map<String, Object> map, int start, int limit)
			throws SQLException {
		PageResult<AdminAppUserResponse> list = this.page(AppUserManagements.SQLMAP_NAMESPACE + ".getAppUserWarrant",
				map, start, limit);
		return list;
	}
}
