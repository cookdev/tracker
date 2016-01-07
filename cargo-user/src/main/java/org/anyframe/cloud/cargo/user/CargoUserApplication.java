package org.anyframe.cloud.cargo.user;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import org.anyframe.cloud.cargo.user.filter.CargoUserFilter;
import org.anyframe.cloud.cargo.user.filter.CargoUserServletContainer;
import org.anyframe.cloud.cargo.user.filter.RestTemplateInterceptor;
import org.anyframe.cloud.cargo.user.infrastructure.api.swagger.SwaggerConfiguration;
import org.h2.server.web.WebServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;

import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableDiscoveryClient
@Import({ SwaggerConfiguration.class })
public class CargoUserApplication {

	private static final Logger logger = LoggerFactory.getLogger(CargoUserApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CargoUserApplication.class, args);
		logger.info("Cargo User Service is started ================================");
	}

	@Bean
	public Predicate<String> swaggerPaths() {
		return regex("/users.*|/sign.*|/log.*|/withdrawal.*|/cargo.*");
	}

	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Cargo User API")
				.description("Cargo Tracker User API")
				.contact("Anyframe Cloud Edition")
				.license("Anyframe Cloud Ed.").version("1.0").build();
	}

	public ObjectMapper jacksonObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper;
	}

	@Bean
	public RestTemplate springRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> converters = restTemplate
				.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
				jsonConverter.setObjectMapper(jacksonObjectMapper());
			}
		}
		return restTemplate;
	}

	@Bean
	public CargoUserFilter cargoUserFilter() {
		return new CargoUserFilter();
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
	public CargoUserServletContainer cargoUserServletContainer() {
		return new CargoUserServletContainer();
	}
	
	@Bean
	public ServletRegistrationBean h2servletRegistration() {
	    ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
	    registration.addUrlMappings("/console/*");
	    return registration;
	}
}
