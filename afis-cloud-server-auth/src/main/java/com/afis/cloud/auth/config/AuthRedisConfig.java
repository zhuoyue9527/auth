package com.afis.cloud.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;

@Component
@Order(value = 1)
public class AuthRedisConfig implements CommandLineRunner {

	@Autowired
	private JedisPool jedisPool;

	@Override
	public void run(String... args) throws Exception {
		new Thread() {
			@Override
			public void run() {
				jedisPool.getResource().psubscribe(new AuthJedisPubSub(jedisPool), "__keyevent*@*__:expired");
			}
		}.start();
	}
}
