package com.afis.cloud.auth.util;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.afis.cloud.auth.business.store.FunctionManagements;
import com.afis.cloud.auth.business.store.OnlineUserManagements;
import com.afis.cloud.auth.business.store.impl.FunctionManagementsImpl;
import com.afis.cloud.auth.business.store.impl.OnlineUserManagementsImpl;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.TicketModel;
import com.afis.cloud.auth.model.TokenModel;
import com.afis.cloud.cache.util.RedisUtil;
import com.afis.cloud.entities.model.auth.Function;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.utils.DataConstans;
import com.afis.utils.DateUtils;
import com.afis.web.modal.UserDetails;

@Component
public class AuthCacheUtil {
	@Autowired
	private RedisUtil redisUtil;

	@Value("${ticket.valid.time}")
	private int ticketValidTime;

	@Value("${session.valid.time}")
	private int sessionValidTime;

	@Value("${token.valid.time}")
	private int tokenValidTime;

	private FunctionManagements functionManagements = new FunctionManagementsImpl();

	private OnlineUserManagements onlineUserManagements = new OnlineUserManagementsImpl();

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	public RedisUtil getRedisUtil() {
		return redisUtil;
	}

	/**
	 * 从redis中获取应用信息
	 * 
	 * @param appCode
	 * @return
	 */
	public ApplicationModel getApplicationFromRedis(String appCode) {
		return (ApplicationModel) redisUtil.getValueFromHashCache(AuthCache.APPLICTION, appCode);
	}

	/**
	 * 将应用信息添加到redis中
	 * 
	 * @param applicationModel
	 */
	public void addApplicationToRedis(ApplicationModel applicationModel) {
		redisUtil.putValueToHashCache(AuthCache.APPLICTION, applicationModel.getAppCode(), applicationModel);
	}

	/**
	 * 根据请求url获取对应的function
	 * 
	 * @param functionUrl
	 * @return
	 * @throws SQLException
	 */
	public Function getFunctionByFunctionUrl(String functionUrl) throws SQLException {
		List<Function> list = (List<Function>) redisUtil.getValueFromCache(AuthCache.FUNCTION);
		if (list == null) {
			list = functionManagements.getAllFunction();
			redisUtil.putValueToCache(AuthCache.FUNCTION, list);
		}
		if (list != null && !list.isEmpty()) {
			for (Function function : list) {
				if (antPathMatcher.match(function.getUrl(), functionUrl)) {
					return function;
				}
			}
		}
		return null;
	}

	/**
	 * 获取失败次数
	 * 
	 * @param username
	 * @param redisUtil
	 * @return
	 */
	public int getFailNum(String username) {
		String currentDay = getCurrentDay();
		if (!currentDay.equals(AuthCache.currentDate)) {
			AuthCache.currentDate = (String) redisUtil.getValueFromCache(AuthCache.CURRENT_DAY);
			if (!currentDay.equals(AuthCache.currentDate)) {
				AuthCache.currentDate = currentDay;
				redisUtil.deleteKeyFromHashCache(AuthCache.USER_FAIL_COUNT);// 删除所有失败次数
				redisUtil.putValueToCache(AuthCache.CURRENT_DAY, currentDay);// 将缓存中的日期设置成当前日期
				return 0;
			}
		}
		Integer failCount = (Integer) redisUtil.getValueFromHashCache(AuthCache.USER_FAIL_COUNT, username);
		return failCount == null ? 0 : failCount;
	}

	/**
	 * 登录失败时，失败次数+1
	 * 
	 * @param username
	 * @param redisUtil
	 */
	public void addFailNum(String username) {
		Integer failCount = (Integer) redisUtil.getValueFromHashCache(AuthCache.USER_FAIL_COUNT, username);
		if (failCount == null) {
			failCount = 1;
		} else {
			failCount += 1;
		}
		redisUtil.putValueToHashCache(AuthCache.USER_FAIL_COUNT, username, failCount);
	}

	/**
	 * 登录成功后，删除已存在的失败次数
	 * 
	 * @param username
	 * @param redisUtil
	 */
	public void removeFailNum(String username) {
		redisUtil.deleteFromHashCache(AuthCache.USER_FAIL_COUNT, username);
	}

	/**
	 * 
	 * @param userDetails
	 * @param isAdmin
	 *            是否管理端
	 * @throws SQLException
	 */
	public void addOnlineUserToRedis(UserDetails userDetails, boolean isAdmin) throws SQLException {
		if (isAdmin) {
			String loginKey = (String) redisUtil.getValueFromHashCache(AuthCache.AUTH_USER_LOGINKEY,
					userDetails.getUserName());
			if (loginKey != null) {
				// 删除管理端的在线用户
				redisUtil.deleteFromCache(AuthCache.ONLINE_USER + AuthCache.SPLIT_SIGN + loginKey);
			}
			redisUtil.putValueToHashCache(AuthCache.AUTH_USER_LOGINKEY, userDetails.getUserName(),
					userDetails.getLoginKey());
			onlineUserManagements.deleteOnlineUserByUserId(userDetails.getId());
		}
		redisUtil.putValueToCache(AuthCache.ONLINE_USER + AuthCache.SPLIT_SIGN + userDetails.getLoginKey(), userDetails,
				sessionValidTime);
		onlineUserManagements.insertOnlineUser(userDetails.getLoginKey(), userDetails.getId());
	}

	/**
	 * 从redis中获取当前用户
	 * 
	 * @param loginKey
	 * @return
	 */
	public UserDetails getOnlineUserFromRedis(String loginKey) {
		return (UserDetails) redisUtil.getValueFromCache(AuthCache.ONLINE_USER + AuthCache.SPLIT_SIGN + loginKey);
	}

	/**
	 * 用户登出时，清除在线用户信息
	 * 
	 * @param userDetails
	 * @param isAdmin
	 * @throws SQLException
	 */
	public void removeOnlineUserFromRedis(UserDetails userDetails, boolean isAdmin) throws SQLException {
		if (isAdmin) {
			redisUtil.deleteFromHashCache(AuthCache.AUTH_USER_LOGINKEY, userDetails.getUserName());
		}
		redisUtil.deleteFromCache(AuthCache.ONLINE_USER + AuthCache.SPLIT_SIGN + userDetails.getLoginKey());
		onlineUserManagements.deleteOnlineUserByLoginKey(userDetails.getLoginKey());
	}

	/**
	 * 踢除在线用户
	 * 
	 * @param loginKey
	 * @param loginAccount
	 * @param isAdmin
	 * @throws SQLException
	 */
	public void removeOnlineUserWithLoginKeyFromRedis(String loginKey, String loginAccount, boolean isAdmin)
			throws SQLException {
		if (isAdmin) {
			redisUtil.deleteFromHashCache(AuthCache.AUTH_USER_LOGINKEY, loginAccount);
		}
		redisUtil.deleteFromCache(AuthCache.ONLINE_USER + AuthCache.SPLIT_SIGN + loginKey);
		onlineUserManagements.deleteOnlineUserByLoginKey(loginKey);
	}

	/**
	 * 将ticket添加到redis缓存中
	 * 
	 * @param model
	 */
	public void addTicketToRedis(TicketModel model) {
		redisUtil.putValueToCache(AuthCache.AUTH_TICKET + AuthCache.SPLIT_SIGN + model.getTicket(), model,
				ticketValidTime);
	}

	/**
	 * 根据ticket获取ticket对象
	 * 
	 * @param ticket
	 * @return
	 */
	public TicketModel getTicketFromRedis(String ticket) {
		return (TicketModel) redisUtil.getValueFromCache(AuthCache.AUTH_TICKET + AuthCache.SPLIT_SIGN + ticket);
	}

	/**
	 * 将ticket从缓存中移除
	 * 
	 * @param ticket
	 */
	public void removeTicketFromRedis(String ticket) {
		redisUtil.deleteFromCache(AuthCache.AUTH_TICKET + AuthCache.SPLIT_SIGN + ticket);
	}

	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	private static String getCurrentDay() {
		return DateUtils.format(new Date(), DataConstans.YYYYMMDD);
	}

	/**
	 * 获取缓存中的token
	 * 
	 * @param appCode
	 * @param userName
	 * @return
	 */
	public TokenModel getTokenFromRedis(String appCode, String userName) {
		String token = (String) redisUtil.getValueFromCache(
				AuthCache.AUTH_USER_TOKEN + AuthCache.SPLIT_SIGN + appCode + AuthCache.SPLIT_SIGN + userName);
		if (token != null) {
			return getTokenFromRedis(token);
		}
		return null;
	}

	/**
	 * 根据token获取tokenModel
	 * 
	 * @param token
	 * @return
	 */
	private TokenModel getTokenFromRedis(String token) {
		return (TokenModel) redisUtil.getValueFromCache(AuthCache.AUTH_TOKEN + AuthCache.SPLIT_SIGN + token);
	}

	/**
	 * 根据token获取tokenModel
	 * 
	 * @param token
	 * @param appCode
	 * @return
	 * @throws AuthenticationException
	 */
	public TokenModel getToken(String token, String appCode) throws AuthenticationException {
		TokenModel tokenModel = getTokenFromRedis(token);
		if (tokenModel == null || !tokenModel.getAppCode().equals(appCode)) {
			throw new AuthenticationException(AuthenticationException.AUTH_TOKEN_INVALID);
		}
		return tokenModel;
	}

	/**
	 * 将token缓存到redis中
	 * 
	 * @param token
	 */
	public void addTokenToRedis(TokenModel token) {
		redisUtil.putValueToCache(AuthCache.AUTH_USER_TOKEN + AuthCache.SPLIT_SIGN + token.getAppCode()
				+ AuthCache.SPLIT_SIGN + token.getUserName(), token.getToken());
		redisUtil.putValueToCache(AuthCache.AUTH_TOKEN + AuthCache.SPLIT_SIGN + token.getToken(), token,
				tokenValidTime);
	}
}
