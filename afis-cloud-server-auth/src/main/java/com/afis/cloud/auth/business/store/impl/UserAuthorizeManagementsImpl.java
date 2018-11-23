package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.afis.cloud.auth.business.store.UserAuthorizeManagements;
import com.afis.cloud.auth.config.KafkaConfig;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.protocol.open.request.AbstractInterfaceRequest;
import com.afis.cloud.auth.model.protocol.open.request.UserAuthorizeRequest;
import com.afis.cloud.auth.model.protocol.open.response.AppUserResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.entities.dao.auth.AppUserDAO;
import com.afis.cloud.entities.dao.auth.UserDAO;
import com.afis.cloud.entities.dao.impl.auth.AppUserDAOImpl;
import com.afis.cloud.entities.dao.impl.auth.UserDAOImpl;
import com.afis.cloud.entities.model.auth.AppUser;
import com.afis.cloud.entities.model.auth.AppUserExample;
import com.afis.cloud.entities.model.auth.AppUserSelective;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.utils.MD5;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/16 14:18
 */
public class UserAuthorizeManagementsImpl extends AbstractManagementsImpl implements UserAuthorizeManagements {

	private KafkaTemplate kafkaTemplate;

	private AuthCacheUtil authCacheUtil;

	private Logger logger = LoggerFactory.getLogger(getClass());

	private AppUserDAO appUserDAO = new AppUserDAOImpl(sqlMapClient);

	private UserDAO userDao = new UserDAOImpl(sqlMapClient);

	public UserAuthorizeManagementsImpl(KafkaTemplate kafkaTemplate, AuthCacheUtil authCacheUtil) {
		this.kafkaTemplate = kafkaTemplate;
		this.authCacheUtil = authCacheUtil;
	}

	private AppUser getAppUserByUK(Long userId, Long appId) throws SQLException {
		AppUserExample example = new AppUserExample();
		example.createCriteria().andUserIdEqualTo(userId).andAppIdEqualTo(appId);
		List<AppUser> list = appUserDAO.selectByExample(example);
		return list == null || list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void addUserWarrant(String functionUrl, Long userId, UserAuthorizeRequest userRequest)
			throws SQLException, AuthenticationException, CommonException {
		// 用户授权应用是否存在
		ApplicationModel application = authCacheUtil.getApplicationFromRedis(userRequest.getAppCode());
		AppUser appUser = getAppUserByUK(userId, application.getId());
		if (appUser == null || CommonConstants.TrueOrFalse.FALSE.getKey()
				.equals(appUser.getWarrantPermit())) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"用户授权权限不存在，不能授权"));
			throw new AuthenticationException(AuthenticationException.AUTH_USER_AUTHORIZE_PERMIT);
		}

		User user = userDao.selectByPrimaryKey(userId);

		AppUserSelective selective = new AppUserSelective();
		selective.setWarrant(CommonConstants.TrueOrFalse.TRUE.getKey());
		// 设置应用密码
		if (userRequest.getPassword() != null && userRequest.getPassword().trim().length() > 0) {
			selective.setAppPassword(MD5.code16(user.getUserAccount() + userRequest.getPassword().trim()));
		}

		AppUserExample example = new AppUserExample();
		example.createCriteria().andIdEqualTo(appUser.getId())
				.andWarrantPermitEqualTo(CommonConstants.TrueOrFalse.TRUE.getKey())
				.andWarrantEqualTo(CommonConstants.TrueOrFalse.FALSE.getKey());

		if (appUserDAO.updateByExample(selective, example) < 1) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"用户已经授权，不需要重复操作"));
			throw new AuthenticationException(AuthenticationException.AUTH_USER_AUTHORIZE_HAS_PERMIT);
		} else {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"用户授权成功"));
		}
	}

	@Override
	public void deleteAppUserWarrant(String functionUrl, Long userId, AbstractInterfaceRequest userRequest)
			throws SQLException, AuthenticationException, CommonException {
		ApplicationModel application = authCacheUtil.getApplicationFromRedis(userRequest.getAppCode());
		AppUser appUser = getAppUserByUK(userId, application.getId());
		if (appUser == null || CommonConstants.TrueOrFalse.FALSE.getKey()
				.equals(appUser.getWarrantPermit())) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"用户授权权限不存在，不能删除授权"));
			throw new AuthenticationException(AuthenticationException.AUTH_USER_AUTHORIZE_PERMIT);
		}
		if (CommonConstants.TrueOrFalse.FALSE.getKey().equals(appUser.getWarrant())) {
			throw new CommonException(CommonException.ANY_DESC, "用户权限已删除，不需要重复删除");
		}

		AppUserSelective selective = new AppUserSelective();
		selective.setWarrant(CommonConstants.TrueOrFalse.FALSE.getKey());
		selective.setAppPassword(null);

		AppUserExample example = new AppUserExample();
		example.createCriteria().andIdEqualTo(appUser.getId()).andWarrantPermitEqualTo(appUser.getWarrantPermit())
				.andWarrantEqualTo(appUser.getWarrant());
		if (appUserDAO.updateByExample(selective, example) < 1) {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createFailLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"用户权限已删除，不需要重复删除"));
			throw new AuthenticationException(AuthenticationException.AUTH_USER_AUTHORIZE_HAS_PERMIT);
		} else {
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_OPERATE_TOPIC,
					createSuccessLog(authCacheUtil.getFunctionByFunctionUrl(functionUrl), null, application.getId(),
							authCacheUtil.getToken(userRequest.getToken(), userRequest.getAppCode()).getUserId(),
							"用户权限删除成功"));
		}
	}

	@Override
	public AppUserResponse getAppUserByUserIdAndAppCode(Long userId, String appCode)
			throws SQLException, AuthenticationException, CommonException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("appCode", appCode);

		return this.queryForObject(UserAuthorizeManagements.SQLMAP_NAMESPACE + ".getAppUserByUserIdAndAppCode", map);
	}
}
