package org.anyframe.cloud.cargo.booking;

import org.anyframe.cloud.cargo.booking.filter.CargoBookingFilter;
import org.anyframe.cloud.cargo.booking.filter.CargoBookingServletContainer;
import org.anyframe.cloud.cargo.booking.filter.RestTemplateInterceptor;
import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class CargoBookingApplication {

	private static final Logger logger = LoggerFactory.getLogger(CargoBookingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CargoBookingApplication.class, args);
		logger.info("Cargo Tracker Booking Service가 구동되었습니다.");
    }
    
    @Bean
   	public CargoBookingFilter cargoBookingFilter() {
   		return new CargoBookingFilter();
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
   	public CargoBookingServletContainer cargoBookingServletContainer() {
   		return new CargoBookingServletContainer();
   	}

	@Bean
	public ServletRegistrationBean h2servletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
		registration.addUrlMappings("/console/*");
		return registration;
	}
}
