package com.afis.cloud.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@Configuration
public class AfisGatewayApplication {
	
	@Value("${loadbalance.readTimeout}")
	private int readTimeOut = 1000;
	@Value("${loadbalance.connectTimeout}")
	private int connectTimeout = 1000;
	
	public static void main(String[] args) {
		SpringApplication.run(AfisGatewayApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory  = new SimpleClientHttpRequestFactory ();
		simpleClientHttpRequestFactory.setConnectTimeout(connectTimeout);
		simpleClientHttpRequestFactory.setReadTimeout(readTimeOut);
		return new RestTemplate(simpleClientHttpRequestFactory);
	}
}
