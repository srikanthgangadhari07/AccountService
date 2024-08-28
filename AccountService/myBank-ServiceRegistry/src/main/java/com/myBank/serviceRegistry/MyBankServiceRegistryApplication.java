package com.myBank.serviceRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MyBankServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBankServiceRegistryApplication.class, args);
	}

}
