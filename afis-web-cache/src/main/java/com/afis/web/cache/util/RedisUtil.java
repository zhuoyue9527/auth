package com.afis.web.cache.util;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 增加到缓存
	 * 
	 * @param key
	 * @param value
	 * @param expireDate
	 */
	public <T> void addCache(String key, T value, int expireDate) {
		redisTemplate.opsForValue().set(key, value, expireDate);
	}

	/**
	 * 移除缓存
	 * 
	 * key以*结尾的，则删除匹配的一堆key
	 * 
	 * @param key
	 */
	public void removeCache(String key) {
		if (key.endsWith("*")) {
			Set<String> keys = redisTemplate.keys(key);
			redisTemplate.delete(keys);
		} else {
			redisTemplate.delete(key);
		}
	}
}
