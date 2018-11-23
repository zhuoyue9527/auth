package com.afis.cloud.auth.config;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.afis.cloud.auth.business.store.OnlineUserManagements;
import com.afis.cloud.auth.business.store.impl.OnlineUserManagementsImpl;
import com.afis.cloud.auth.util.AuthCache;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

public class AuthJedisPubSub extends JedisPubSub {

	private JedisPool jedisPool;

	private OnlineUserManagements onlineUserManagements = new OnlineUserManagementsImpl();

	public AuthJedisPubSub(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onMessage(String channel, String message) {
		logger.debug("channel:{},message:{}", channel, message);
	}

	@Override
	public void onPMessage(String pattern, String channel, String message) {
		logger.debug("pattern:{},channel:{},message:{}", pattern, channel, message);
		if (message.startsWith(AuthCache.ONLINE_USER)) {// 在线用户被清除
			String loginKey = message.substring(message.indexOf(AuthCache.SPLIT_SIGN)+1);
			try {
				onlineUserManagements.deleteOnlineUserByLoginKey(loginKey);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		logger.debug("channel:{},subscribedChannels:{}", channel, subscribedChannels);
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		logger.debug("channel:{},subscribedChannels:{}", channel, subscribedChannels);
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		logger.debug("pattern:{},subscribedChannels:{}", pattern, subscribedChannels);
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		logger.debug("pattern:{},subscribedChannels:{}", pattern, subscribedChannels);
	}
}
