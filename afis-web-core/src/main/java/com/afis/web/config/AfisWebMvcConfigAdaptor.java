package com.afis.web.config;

import java.io.File;
import java.io.IOException;

import javax.servlet.MultipartConfigElement;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.afis.web.utils.ExceptionRenderUtil;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by hsw on 2017/1/11.
 */
@Configuration
@EnableSwagger2
public class AfisWebMvcConfigAdaptor implements WebMvcConfigurer {

	@Autowired
	private WebProperties webProperties;
	@Autowired
	private MessageSource messageSource;

	private static final String TMP = "tmp";

	@Bean
	public ExceptionRenderUtil exceptionRenderUtil() {
		ExceptionRenderUtil exceptionRenderUtil = new ExceptionRenderUtil();
		exceptionRenderUtil.setMessageSource(messageSource);
		return exceptionRenderUtil;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler(webProperties.getUploader().getVirtualPath() + "**")
				.addResourceLocations("file:" + webProperties.getUploader().getDestinationPath());
	}

	@Bean
	MultipartConfigElement multipartConfigElement() throws IOException {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(createMultipartConfigFactoryLocation());
		return factory.createMultipartConfig();
	}

	/**
	 * 生成MultipartConfigFactory的location
	 * 
	 * @return
	 * @throws IOException
	 */
	private String createMultipartConfigFactoryLocation() throws IOException {
		String path = FilenameUtils.concat(webProperties.getUploader().getDestinationPath(), TMP);
		File file = new File(path);
		if (!file.exists()) {
			FileUtils.forceMkdir(file);
		}
		return path;
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.afis.web")).paths(PathSelectors.any()).build()
				// 不需要时，或者生产环境可以在此处关闭
				.enable(true);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Boot中使用Swagger2构建RESTful APIs").description("描述：测试使用Swagger2！")
				// 服务条款网址
				.termsOfServiceUrl("www.auto-fis.com").contact(new Contact("Aslan", "", "")).version("1.0").build();
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(Boolean.FALSE);// 可以让URL路径中带小数点 '.' 后面的值不被忽略
	}
}
