package com.udacity.careurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class CarEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarEurekaServerApplication.class, args);
	}

}
