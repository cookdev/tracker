package org.anyframe.cloud.cargo.pathfinder;

import org.anyframe.cloud.cargo.pathfinder.filter.CargoTrackerPathfinderFilter;
import org.anyframe.cloud.cargo.pathfinder.filter.CargoTrackerPathfinderServletContainer;
import org.anyframe.cloud.cargo.pathfinder.filter.RestTemplateInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class CargoTrackerPathfinderApplication {

	private static final Logger logger = LoggerFactory.getLogger(CargoTrackerPathfinderApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(CargoTrackerPathfinderApplication.class, args);
		logger.info("Cargo Tracker Pathfinder Service is started ================================");
    }
    
    @Bean
   	public CargoTrackerPathfinderFilter cargoTrackerPathfinderFilter() {
   		return new CargoTrackerPathfinderFilter();
   	}

   	@Bean
   	public RestTemplateInterceptor restTemplateInterceptor() {
   		return new RestTemplateInterceptor();
   	}

   	@Bean
   	public RestTemplate restTemplate() {
   		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
   		restTemplate.getInterceptors().add(
				new RestTemplateInterceptor());
   		return restTemplate;
   	}

   	private ClientHttpRequestFactory clientHttpRequestFactory() {
   		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
   		factory.setReadTimeout(2000);
   		factory.setConnectTimeout(2000);
   		return factory;
   	}

   	@Bean
   	public CargoTrackerPathfinderServletContainer cargoTrackerPathfinderServletContainer() {
   		return new CargoTrackerPathfinderServletContainer();
   	}
}
