package com.epol.AdministrativeService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdministrativeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdministrativeServiceApplication.class, args);
	}

}