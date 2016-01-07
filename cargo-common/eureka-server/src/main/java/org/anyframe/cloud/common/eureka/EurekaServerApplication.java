package org.anyframe.cloud.common.eureka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class EurekaServerApplication {

	private static final Logger logger = LoggerFactory.getLogger(EurekaServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
		logger.info("Eureka Server is started ================================");
	}

}
