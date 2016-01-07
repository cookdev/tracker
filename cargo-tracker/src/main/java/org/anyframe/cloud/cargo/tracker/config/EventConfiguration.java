package org.anyframe.cloud.cargo.tracker.config;

import org.anyframe.cloud.cargo.tracker.infrastructure.events.spring.CargoEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfiguration {
	
	@Bean
	public CargoEventListener messageEventListener() {
		return new CargoEventListener();
	}
	
}
