package org.anyframe.cloud.cargo.tracker.infrastructure.routing;

import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Itinerary;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Leg;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.RouteSpecification;
import org.anyframe.cloud.cargo.tracker.domain.model.location.LocationRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.location.UnLocode;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageNumber;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageRepository;
import org.anyframe.cloud.cargo.tracker.domain.service.RoutingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

//import org.anyframe.cargotracker.infrastructure.routing.service.PathFinderClient;

/**
 * Our end of the routing service. This is basically a data model translation
 * layer between our domain model and the API put forward by the routing team,
 * which operates in a different context from us.
 *
 */
@Service("externalRoutingService")
public class ExternalRoutingService implements RoutingService {

	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	@Autowired
	private RestTemplate restTemplate;

    @Autowired
    private LocationRepository locationRepository;
    
    @Autowired
    private VoyageRepository voyageRepository;
    
//    private String applicationUri = "http://Pathfinder";
    
    private String graphTraversalUrl = "/shortest-path?origin={origin}&destination={destination}&deadline={deadline}";
    
	private final Logger logger = LoggerFactory.getLogger(ExternalRoutingService.class);

	public ExternalRoutingService() {
	}

    @Override
    public List<Itinerary> fetchRoutesForSpecification(
            RouteSpecification routeSpecification) {
    	
        // The RouteSpecification is picked apart and adapted to the external API.
        String origin = routeSpecification.getOrigin().getUnLocode().getIdString();
        String destination = routeSpecification.getDestination().getUnLocode()
                .getIdString();
        Date arrivalDeadline = routeSpecification.getArrivalDeadline();

        Map<String, Object> requestParamMap = new HashMap<String, Object>();
        requestParamMap.put("origin",origin);
        requestParamMap.put("destination",destination);
        requestParamMap.put("deadline",arrivalDeadline.toString());
        
        ServiceInstance service = loadBalancerClient.choose("Pathfinder");
        
		ResponseEntity<TransitPaths> exchange = restTemplate.exchange(
//				applicationUri + graphTraversalUrl, 
				service.getUri() + graphTraversalUrl,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<TransitPaths>() {},
				requestParamMap); 
        TransitPaths transitPaths = exchange.getBody();
		
        // The returned result is then translated back into our domain model.
        List<Itinerary> itineraries = new ArrayList<>();
        for (TransitPath transitPath : transitPaths.getTransitPaths()) {
            Itinerary itinerary = toItinerary(transitPath);
            // Use the specification to safe-guard against invalid itineraries
            if (routeSpecification.isSatisfiedBy(itinerary)) {
                itineraries.add(itinerary);
            } else {
            	logger.debug("Received itinerary that did not satisfy the route specification");
            }
        }

        return itineraries;
    }

    private Itinerary toItinerary(TransitPath transitPath) {

        List<Leg> legs = new ArrayList<>(transitPath.getTransitEdges().size());
        for (TransitEdge edge : transitPath.getTransitEdges()) {
            legs.add(toLeg(edge));
        }
        return new Itinerary(legs);
    }

    private Leg toLeg(TransitEdge edge) {
        return new Leg(
                voyageRepository.find(new VoyageNumber(edge.getVoyageNumber())),
                locationRepository.find(new UnLocode(edge.getFromUnLocode())),
                locationRepository.find(new UnLocode(edge.getToUnLocode())),
                edge.getFromDate(), edge.getToDate());
    }
}
