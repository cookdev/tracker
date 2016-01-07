package org.anyframe.cloud.cargo.tracker;

import org.anyframe.cloud.cargo.tracker.filter.CargoTrackerFilter;
import org.anyframe.cloud.cargo.tracker.filter.CargoTrackerServletContainer;
import org.anyframe.cloud.cargo.tracker.filter.RestTemplateInterceptor;
import org.anyframe.cloud.cargo.tracker.infrastructure.messaging.message.ApplicationContextProvider;
import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@Configuration
@EnableAutoConfiguration
@EnableHystrix
@ComponentScan(basePackages = "org.anyframe.cloud.cargo.tracker", useDefaultFilters = false, includeFilters = {
		@ComponentScan.Filter(Service.class),
		@ComponentScan.Filter(Repository.class),
		@ComponentScan.Filter(Component.class)})
public class CargoTrackerApplication {

	private static final Logger logger = LoggerFactory.getLogger(CargoTrackerApplication.class);

    public static void main(String[] args) {

		SpringApplication.run(CargoTrackerApplication.class, args);
		logger.info("Cargo Tracker Service가 구동되었습니다.");
    }
    
    
    @Bean
	public ApplicationContextProvider applicationContextProvder() {
		ApplicationContextProvider applicationContextProvder = new ApplicationContextProvider();
		
		return applicationContextProvder;
	}
    
    @Bean
   	public CargoTrackerFilter cargoTrackerFilter() {
   		return new CargoTrackerFilter();
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
   	public CargoTrackerServletContainer cargoTrackerServletContainer() {
   		return new CargoTrackerServletContainer();
   	}

	@Bean
	public ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
		registration.addUrlMappings("/console/*");
		return registration;
	}
}
