package org.anyframe.booking.infrastructure.tracking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * CargoTracker 와의 통신을 담당하는 서비스
 * @author SDS
 * 
 */
@Service
public class CargoTrackerService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
//	private final String applicationUri = "http://CargoTracker";
	
	public CargoRoute getCargo(String trackingId) {
		ServiceInstance service = loadBalancerClient.choose("Cargotracker");
		
		ResponseEntity<CargoRoute> exchange = restTemplate.exchange(
//				applicationUri + "/cargos/{trackingId}",
				service.getUri() + "/cargos/{trackingId}",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<CargoRoute>() {},
				trackingId); 
		return exchange.getBody();
	}

	public String registerCargo(Map<String, String> bookingInformation) {
		ServiceInstance service = loadBalancerClient.choose("Cargotracker");
		
		ResponseEntity<String> exchange = restTemplate.postForEntity(
//				applicationUri + "/cargos/registration",
				service.getUri() + "/cargos/registration",
				bookingInformation,
				String.class);
		
		String trackingId = exchange.getBody();
		
		return trackingId;
	}
	
	public void changeDestination(String trackingId, String destination) {
		ServiceInstance service = loadBalancerClient.choose("Cargotracker");
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("destinationUnlocode", destination);
		
		restTemplate.postForEntity(
//				applicationUri + "/cargos/{trackingId}/change-destination",
				service.getUri() + "/cargos/{trackingId}/change-destination",
				param,
				String.class,
				trackingId);
	}
}
