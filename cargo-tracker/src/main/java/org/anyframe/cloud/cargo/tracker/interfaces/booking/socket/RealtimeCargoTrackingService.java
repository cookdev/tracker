package org.anyframe.cloud.cargo.tracker.interfaces.booking.socket;

import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Cargo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket service for tracking all cargoes in real time.
 *
 */
@Service
public class RealtimeCargoTrackingService {
	
	private final Logger logger = LoggerFactory.getLogger(RealtimeCargoTrackingService.class);
	
	@Autowired
	private SimpMessagingTemplate template;
	
	public void onCargoInspected(Cargo cargo) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("trackingId", cargo.getTrackingId().getIdString());
		map.put("origin", cargo.getOrigin().getName() + " (" + cargo.getOrigin().getUnLocode().getIdString() + ")");
		map.put("destination", cargo.getRouteSpecification().getDestination().getName() + " (" + cargo.getRouteSpecification().getDestination().getUnLocode().getIdString() + ")");
		map.put("lastKnownLocation", cargo.getDelivery().getLastKnownLocation().getName() + " (" + cargo.getDelivery().getLastKnownLocation().getUnLocode().getIdString() + ")");
		map.put("transportStatus", cargo.getDelivery().getTransportStatus().toString());
		template.convertAndSend("/transportstatus", map);
		logger.debug("## onCargoInspected fire! ##");
	}
	
}
