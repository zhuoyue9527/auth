package com.afis.web.auth.security;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import com.afis.web.auth.config.AuthConstants;
import com.afis.web.modal.UserDetails;
import com.afis.web.security.AbstractTokenService;

public class InRedisTokenService extends AbstractTokenService {

	private RedisTemplate redisTemplate;

	private int sessionValidTime;

	@Override
	public void storeToken(UserDetails userDetails) {
		redisTemplate.opsForValue().set(
				AuthConstants.ONLINE_USER + AuthConstants.SPLIT_SIGN + userDetails.getLoginKey(), userDetails,
				sessionValidTime, TimeUnit.MINUTES);
	}

	@Override
	public UserDetails getToken(String loginKey) {
		return (UserDetails) redisTemplate.opsForValue()
				.get(AuthConstants.ONLINE_USER + AuthConstants.SPLIT_SIGN + loginKey);
	}

	@Override
	public void removeToken(String loginKey) {
		redisTemplate.delete(AuthConstants.ONLINE_USER + AuthConstants.SPLIT_SIGN + loginKey);
	}

	@Override
	public void freshTokenTime(String loginKey) {
		UserDetails details = getToken(loginKey);
		details.setLastAccessTime(System.currentTimeMillis());
		storeToken(details);
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void setSessionValidTime(int sessionValidTime) {
		this.sessionValidTime = sessionValidTime;
	};
}
