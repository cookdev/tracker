package org.anyframe.cloud.cargo.aggregation.infrastructure.booking;

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
public class CargoBookingService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
//	private final String applicationUri = "http://CargoBooking";
	
	public List<BookingDto> listBookings() {
		
		ServiceInstance service = loadBalancerClient.choose("Cargobooking");
		
		ResponseEntity<List<BookingDto>> exchange = restTemplate.exchange(
//				applicationUri + "/bookings/not-accepted",
				service.getUri() + "/bookings/not-accepted",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<BookingDto>>() {}); 
		return exchange.getBody();
	}
}
