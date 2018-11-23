package com.afis.web.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.afis.web.auth.security.AuthAuthenticationManager;
import com.afis.web.auth.security.InRedisTokenService;
import com.afis.web.config.WebProperties;
import com.afis.web.security.inf.AuthenticationHandler;
import com.afis.web.security.inf.AuthenticationManager;

@Configuration
public class AfisAuthWebMvcConfigAdaptor implements WebMvcConfigurer {
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

	private HandlerInterceptor securityInterceptor() {
		WebSecurityInterceptor interceptor = new WebSecurityInterceptor();
		interceptor.setWebProperties(webProperties);
		interceptor.setAuthenticationManager(authenticationManager());
		interceptor.setAuthenticationHandler(authenticationHandler());
		return interceptor;
	}
	
	@Bean(name = "authenticationHandler")
	public AuthenticationHandler authenticationHandler() {
		DefaultAuthenticationHandler authenticationHandler = new DefaultAuthenticationHandler();
		authenticationHandler.setWebProperties(webProperties);
		return authenticationHandler;
	}

	@Bean(name = "tokenService")
	public InRedisTokenService inRedisTokenService() {
		InRedisTokenService inRedisTokenService = new InRedisTokenService();
		inRedisTokenService.setRedisTemplate(redisTemplate);
		inRedisTokenService.setSessionValidTime(sessionValidTime);
		return inRedisTokenService;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(securityInterceptor()).excludePathPatterns(webProperties.getSecurity().getPermitAll());
	}
}
