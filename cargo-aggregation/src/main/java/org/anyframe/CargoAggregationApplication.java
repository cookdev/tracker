package org.anyframe;

import org.anyframe.aggregation.filter.CargoAggregationFilter;
import org.anyframe.aggregation.filter.CargoAggregationServletContainer;
import org.anyframe.aggregation.filter.RestTemplateInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class CargoAggregationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CargoAggregationApplication.class, args);
    }
    
    @Bean
	public CargoAggregationFilter cargoAggregationFilter() {
		return new CargoAggregationFilter();
	}

	@Bean
	public RestTemplateInterceptor restTemplateInterceptor() {
		return new RestTemplateInterceptor();
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.getInterceptors().add(
				(ClientHttpRequestInterceptor) new RestTemplateInterceptor());
		return restTemplate;
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(2000);
		factory.setConnectTimeout(2000);
		return factory;
	}

	@Bean
	public CargoAggregationServletContainer cargoAggregationServletContainer() {
		return new CargoAggregationServletContainer();
	}
}
