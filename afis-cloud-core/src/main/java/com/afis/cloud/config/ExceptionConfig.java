package com.afis.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.afis.cloud.utils.ExceptionRenderUtil;

@Configuration
public class ExceptionConfig {
	@Autowired
    private MessageSource messageSource;
	
	@Bean
    public ExceptionRenderUtil exceptionRenderUtil() {
        ExceptionRenderUtil exceptionRenderUtil = new ExceptionRenderUtil();
        exceptionRenderUtil.setMessageSource(messageSource);
        return exceptionRenderUtil;
    }
}
