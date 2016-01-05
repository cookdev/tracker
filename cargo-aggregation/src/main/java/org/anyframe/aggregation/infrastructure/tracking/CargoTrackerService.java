package org.anyframe.aggregation.infrastructure.tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CargoTrackerService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
//	private final String applicationUri = "http://CargoTracker";
	
	public List<CargoRoute> listTrackings() {

		ServiceInstance service = loadBalancerClient.choose("Cargotracker");
		
		ResponseEntity<List<CargoRoute>> exchange = restTemplate.exchange(
//				applicationUri + "/cargos",
				service.getUri() + "/cargos",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<CargoRoute>>() {}); 
		return exchange.getBody();
	}
}
