package com.afis.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages="com.afis.cloud")
@EnableCircuitBreaker
@EnableFeignClients
@EnableHystrixDashboard
public class AfisCloudServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AfisCloudServerApplication.class, args);
	}
}
