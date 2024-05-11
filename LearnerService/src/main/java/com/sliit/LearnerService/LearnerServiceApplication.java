package com.sliit.LearnerService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableDiscoveryClient
public class LearnerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnerServiceApplication.class, args);
	}

}
