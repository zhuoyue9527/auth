package com.zhuo.springbootredis.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtil {

	@Autowired
	private RedisTemplate redisTemplate;

	public Object getValueFromHashCache(String key, String hashKey) {
		log.info("---------------");
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	public  <T> void putValueToHashCache(String key, String hashKey, T value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	/**
	 * 删除整个key中的所有hash键值对
	 * 
	 * @param key
	 */
	public void deleteKeyFromHashCache(String key) {
		Set<String> hashKeys = redisTemplate.opsForHash().keys(key);
		if (!hashKeys.isEmpty()) {
			redisTemplate.opsForHash().delete(key, hashKeys.toArray());
		}
	}

	public void deleteFromHashCache(String key, String... hashKeys) {
		redisTemplate.opsForHash().delete(key, hashKeys);
	}

	public <T> void putValueToCache(String key, T value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public <T> void putValueToCache(String key, T value, int expireDate) {
		redisTemplate.opsForValue().set(key, value, expireDate, TimeUnit.MINUTES);
	}

	public Object getValueFromCache(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public void deleteFromCache(String key) {
		if (key.endsWith("*")) {
			Set<String> keys = redisTemplate.opsForValue().getOperations().keys(key);
			redisTemplate.delete(keys);
		} else {
			redisTemplate.delete(key);
		}
	}
}
