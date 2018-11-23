package com.afis.web.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.afis.web.auth.security.AuthAuthenticationManager;
import com.afis.web.auth.security.InRedisTokenService;
import com.afis.web.config.WebProperties;
import com.afis.web.security.inf.AuthenticationManager;

@Configuration
public class AuthConfig {
	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private WebProperties webProperties;

	@Value("${session.valid.time}")
	private int sessionValidTime;

	@Bean(name = "authenticationManager")
	public AuthenticationManager authenticationManager() {
		AuthAuthenticationManager authenticationManager = new AuthAuthenticationManager();
		authenticationManager.setTokenService(inRedisTokenService());
		authenticationManager.setLogoutSuccessPage(webProperties.getSecurity().getLogoutSuccessPage());
		return authenticationManager;
	}

	@Bean(name = "tokenService")
	public InRedisTokenService inRedisTokenService() {
		InRedisTokenService inRedisTokenService = new InRedisTokenService();
		inRedisTokenService.setRedisTemplate(redisTemplate);
		inRedisTokenService.setSessionValidTime(sessionValidTime);
		return inRedisTokenService;
	}
}
