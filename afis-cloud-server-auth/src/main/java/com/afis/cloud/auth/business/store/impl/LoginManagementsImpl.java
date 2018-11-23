package com.afis.cloud.auth.business.store.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.afis.cloud.auth.business.store.LoginManagements;
import com.afis.cloud.auth.config.KafkaConfig;
import com.afis.cloud.auth.model.AppUserExt;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.kafka.LoginLogModel;
import com.afis.cloud.auth.model.protocol.AdminLoginRequest;
import com.afis.cloud.auth.model.protocol.cust.request.CustomerLoginRequest;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.auth.util.AuthConstants;
import com.afis.cloud.business.impl.BusinessCore;
import com.afis.cloud.entities.dao.auth.UserDAO;
import com.afis.cloud.entities.dao.impl.auth.UserDAOImpl;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.entities.model.auth.UserExample;
import com.afis.cloud.entities.model.auth.UserSelective;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.model.CommonConstants.OperatorMemberStatus;
import com.afis.cloud.utils.SessionUtil;
import com.afis.utils.MD5;
import com.afis.web.modal.UserApplications;
import com.afis.web.modal.UserDetails;

public class LoginManagementsImpl extends BusinessCore implements LoginManagements {

	private UserDAO userDao = new UserDAOImpl(sqlMapClient);

	private KafkaTemplate kafkaTemplate;

	private AuthCacheUtil authUtil;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public LoginManagementsImpl(KafkaTemplate kafkaTemplate, AuthCacheUtil authUtil) {
		this.kafkaTemplate = kafkaTemplate;
		this.authUtil = authUtil;
	}

	@Override
	public UserDetails adminLogin(AdminLoginRequest request, int failNum)
			throws SQLException, AuthenticationException, CommonException {
		User user = this.getUserByUserAccount(request.getUsername());
		if (user == null) {
			SessionUtil.addDebugLog(logger, "user not found:{}", request.getUsername());
			throw new AuthenticationException(AuthenticationException.USER_NOT_EXIST);
		}
		// 下面的都要记录登录日志
		if (!CommonConstants.ClientType.MANAGER.getKey().equals(user.getClientType())) {
			SessionUtil.addDebugLog(logger, "只有认证管理员账号才能登陆后台,userAccount:{}", user.getUserAccount());
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
					createLoginLog(user.getId(), request.getAppId(), AuthConstants.LoginStatus.FAIL.getKey(),
							"只有认证管理员账号才能登陆后台", request.getBrowserInfo(), request.getLoginIp(),
							request.getSystemInfo()));
			throw new CommonException(CommonException.ANY_DESC, "只有认证管理员账号才能登陆后台");
		}
		if (!CommonConstants.OperatorMemberStatus.Valid.getKey().equals(user.getStatus())) {
			SessionUtil.addDebugLog(logger, "用户状态非正常,userAccount:{}", user.getUserAccount());
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
					createLoginLog(user.getId(), request.getAppId(), AuthConstants.LoginStatus.FAIL.getKey(),
							"用户状态为" + getStatus(user.getStatus()).getValue(), request.getBrowserInfo(),
							request.getLoginIp(), request.getSystemInfo()));
			throw new AuthenticationException(AuthenticationException.USER_STATUS_INVALID);
		}

		// 校验失败次数
		if (authUtil.getFailNum(request.getUsername()) >= failNum) {
			SessionUtil.addDebugLog(logger, "登录帐号:{}失败次数已超过允许的失败次数:{}", request.getUsername(), failNum);
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
					createLoginLog(user.getId(), request.getAppId(), AuthConstants.LoginStatus.FAIL.getKey(),
							"用户登录失败次数不低于" + failNum + "次，已锁定！", request.getBrowserInfo(), request.getLoginIp(),
							request.getSystemInfo()));
			throw new AuthenticationException(AuthenticationException.USER_FORBID_LOGIN);
		}

		if (!MD5.code16(request.getUsername() + request.getPassword()).equals(user.getPassword())) {
			// 失败次数+1
			authUtil.addFailNum(request.getUsername());
			SessionUtil.addDebugLog(logger, "用户登录失败,userAccount:{}", user.getUserAccount());
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
					createLoginLog(user.getId(), request.getAppId(), AuthConstants.LoginStatus.FAIL.getKey(),
							"密码错误", request.getBrowserInfo(), request.getLoginIp(), request.getSystemInfo()));

			throw new AuthenticationException(AuthenticationException.USER_NOT_EXIST);
		}

		UserDetails details = new UserDetails();
		details.setId(user.getId());
		details.setClientType(user.getClientType());
		details.setBrowserInfo(request.getBrowserInfo());
		details.setLastAccessTime(System.currentTimeMillis());
		details.setLoginTime(System.currentTimeMillis());
		details.setSourceIp(request.getLoginIp());
		details.setSystemInfo(request.getSystemInfo());
		details.setUserName(user.getUserAccount());

		// 插入登录成功的日志
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
				createLoginLog(user.getId(), request.getAppId(), AuthConstants.LoginStatus.SUCCESS.getKey(),
						"登录成功", request.getBrowserInfo(), request.getLoginIp(), request.getSystemInfo()));

		// 删除已存在的失败次数
		authUtil.removeFailNum(request.getUsername());

		// 更新最后登录时间和登录IP
		updateUserLoginParam(user.getId(), request.getLoginIp());

		return details;
	}

	@Override
	public UserDetails customerLogin(CustomerLoginRequest request, ApplicationModel application, int failNum)
			throws SQLException, AuthenticationException, CommonException {
		User user = this.getUserByUserAccount(request.getUsername());
		if (user == null) {
			SessionUtil.addDebugLog(logger, "user not found:{}", request.getUsername());
			throw new AuthenticationException(AuthenticationException.USER_NOT_EXIST);
		}

		if (!CommonConstants.ClientType.TRADER.getKey().equals(user.getClientType())) {
			SessionUtil.addDebugLog(logger, "只有应用帐号才能登陆,userAccount:{}", user.getUserAccount());
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
					createLoginLog(user.getId(), application.getId(), AuthConstants.LoginStatus.FAIL.getKey(),
							"只有应用帐号才能使用该登录", request.getBrowserInfo(), request.getLoginIp(), request.getSystemInfo()));
			throw new CommonException(CommonException.ANY_DESC, "只有应用帐号才能使用该登录");
		}

		if (!CommonConstants.OperatorMemberStatus.Valid.getKey().equals(user.getStatus())) {
			SessionUtil.addDebugLog(logger, "用户状态非正常,userAccount:{}", user.getUserAccount());
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
					createLoginLog(user.getId(), application.getId(), AuthConstants.LoginStatus.FAIL.getKey(),
							"用户状态非正常", request.getBrowserInfo(), request.getLoginIp(), request.getSystemInfo()));
			throw new AuthenticationException(AuthenticationException.USER_STATUS_INVALID);
		}

		List<AppUserExt> appList = this.getAppUserExtByUserId(user.getId());
		AppUserExt appUser = this.getAppUserExtByUk(appList, user.getId(), application.getId());

		if (appUser == null || CommonConstants.TrueOrFalse.FALSE.getKey().equals(appUser.getWarrantPermit())) {
			SessionUtil.addDebugLog(logger, "user:[{}] can not allow login application:[{}]", user.getUserAccount(),
					application.getAppName());
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
					createLoginLog(user.getId(), application.getId(), AuthConstants.LoginStatus.FAIL.getKey(),
							"用户未允许登录该应用", request.getBrowserInfo(), request.getLoginIp(), request.getSystemInfo()));
			throw new CommonException(CommonException.ANY_DESC, "用户未允许登录");
		}

		if (CommonConstants.TrueOrFalse.FALSE.getKey().equals(appUser.getWarrant())) {
			SessionUtil.addDebugLog(logger, "user:[{}] can not permit login application:[{}]", user.getUserAccount(),
					application.getAppName());
			kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
					createLoginLog(user.getId(), application.getId(), AuthConstants.LoginStatus.FAIL.getKey(),
							"用户未授权登录该应用", request.getBrowserInfo(), request.getLoginIp(), request.getSystemInfo()));
			throw new CommonException(CommonException.ANY_DESC, "用户尚未授权");
		}

		if (authUtil.getFailNum(request.getUsername()) > failNum) {
			SessionUtil.addDebugLog(logger, "登录帐号:{}失败次数已超过允许的失败次数:{}", request.getUsername(), failNum);
			throw new AuthenticationException(AuthenticationException.USER_FORBID_LOGIN);
		}

		if (appUser.getAppPassword() == null || appUser.getAppPassword().trim().length() == 0) {
			SessionUtil.addDebugLog(logger,
					"user:[{}] can not set app password!,application:[{}],use common password login!",
					user.getUserAccount(), application.getAppName());
			if (!MD5.code16(request.getUsername() + request.getPassword()).equals(user.getPassword())) {
				// 失败次数+1
				authUtil.addFailNum(request.getUsername());
				SessionUtil.addDebugLog(logger, "用户登录失败,userAccount:{}", user.getUserAccount());
				kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
						createLoginLog(user.getId(), application.getId(), AuthConstants.LoginStatus.FAIL.getKey(),
								"密码错误", request.getBrowserInfo(), request.getLoginIp(), request.getSystemInfo()));

				throw new AuthenticationException(AuthenticationException.USER_NOT_EXIST);
			}

		} else {
			SessionUtil.addDebugLog(logger, "user:[{}] has set app password!,application:[{}],use app password login!",
					user.getUserAccount(), application.getAppName());

			if (!MD5.code16(request.getUsername() + request.getPassword()).equals(appUser.getAppPassword())) {
				// 失败次数+1
				authUtil.addFailNum(request.getUsername());

				SessionUtil.addDebugLog(logger, "用户登录失败,userAccount:{}", user.getUserAccount());
				kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
						createLoginLog(user.getId(), application.getId(), AuthConstants.LoginStatus.FAIL.getKey(),
								"密码错误", request.getBrowserInfo(), request.getLoginIp(), request.getSystemInfo()));

				throw new AuthenticationException(AuthenticationException.USER_NOT_EXIST);
			}
		}

		UserDetails details = new UserDetails();
		details.setId(user.getId());
		details.setClientType(user.getClientType());
		details.setBrowserInfo(request.getBrowserInfo());
		details.setLastAccessTime(System.currentTimeMillis());
		details.setLoginTime(System.currentTimeMillis());
		details.setSourceIp(request.getLoginIp());
		details.setSystemInfo(request.getSystemInfo());
		details.setUserName(user.getUserAccount());
		details.setAppList(this.setAppList(appList));

		// 插入登录成功的日志
		kafkaTemplate.send(KafkaConfig.AFIS_AUTH_LOGIN_TOPIC,
				createLoginLog(user.getId(), user.getRegisterAppId(), AuthConstants.LoginStatus.SUCCESS.getKey(),
						"登录成功", request.getBrowserInfo(), request.getLoginIp(), request.getSystemInfo()));

		// 删除已存在的失败次数
		authUtil.removeFailNum(request.getUsername());

		// 更新最后登录时间和登录IP
		updateUserLoginParam(user.getId(), request.getLoginIp());

		return details;
	}

	private List<UserApplications> setAppList(List<AppUserExt> appList) {
		List<UserApplications> list = new ArrayList<UserApplications>();
		appList.forEach(item -> {
			UserApplications app = new UserApplications();
			app.setAppCode(item.getAppCode());
			app.setUserId(item.getUserId());
			app.setWarrant(item.getWarrant());
			app.setWarrantPermit(item.getWarrantPermit());
			list.add(app);
		});
		return list;
	}

	private AppUserExt getAppUserExtByUk(List<AppUserExt> appList, long userId, long appId) {
		if (appList == null) {
			return null;
		}
		for (AppUserExt appUser : appList) {
			if (appUser.getAppId() == appId) {
				return appUser;
			}
		}
		return null;
	}

	private List<AppUserExt> getAppUserExtByUserId(long userId) throws SQLException {
		return this.queryForList(LoginManagements.SQLMAP_NAMESPACE + ".getAppUserExtByUserId", userId);
	}

	/**
	 * 更新最后登录ip和时间
	 * 
	 * @param id
	 * @param loginIp
	 * @throws SQLException
	 */
	private void updateUserLoginParam(long id, String loginIp) throws SQLException {
		UserSelective selective = new UserSelective();
		selective.setId(id);
		selective.setLastLoginIp(loginIp);
		selective.setLastLoginTime(new Date());
		userDao.updateByPrimaryKey(selective);
	}

	/**
	 * 创建登录日志
	 * 
	 * @param userId
	 * @param loginApp
	 * @param status
	 * @param remark
	 * @param browserInfo
	 * @param loginIp
	 * @param systemInfo
	 * @return
	 * @throws SQLException
	 */
	private LoginLogModel createLoginLog(long userId, long loginApp, String status, String remark, String browserInfo,
			String loginIp, String systemInfo) throws SQLException {
		LoginLogModel selective = new LoginLogModel();
		selective.setBrowserInfo(browserInfo);
		selective.setLoginApp(loginApp);
		selective.setLoginIp(loginIp);
		selective.setLoginTime(new Date());
		selective.setRemark(remark);
		selective.setStatus(status);
		selective.setSystemInfo(systemInfo);
		selective.setUserId(userId);
		return selective;
	}

	/**
	 * 根据登录名获取用户信息
	 * 
	 * @param userAccount
	 * @return
	 * @throws SQLException
	 */
	private User getUserByUserAccount(String userAccount) throws SQLException {
		UserExample example = new UserExample();
		example.createCriteria().andUserAccountEqualTo(userAccount);
		List<User> list = userDao.selectByExample(example);
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	private OperatorMemberStatus getStatus(String status) {
		if (OperatorMemberStatus.Frozen.getKey().equals(status)) {
			return OperatorMemberStatus.Frozen;
		} else if (OperatorMemberStatus.Invalid.getKey().equals(status)) {
			return OperatorMemberStatus.Invalid;
		} else if (OperatorMemberStatus.Valid.getKey().equals(status)) {
			return OperatorMemberStatus.Valid;
		} else {
			return null;
		}
	}
}
