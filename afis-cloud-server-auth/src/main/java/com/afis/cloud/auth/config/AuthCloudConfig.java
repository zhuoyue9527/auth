package com.afis.cloud.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthCloudConfig {

	@Value("${login.fail.num}")
	private int failNum;//登陆错误次数

	public int getFailNum() {
		return failNum;
	}
}
