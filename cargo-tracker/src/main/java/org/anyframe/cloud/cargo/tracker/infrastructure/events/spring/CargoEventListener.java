package org.anyframe.cloud.cargo.tracker.infrastructure.events.spring;

import org.anyframe.cloud.cargo.tracker.interfaces.booking.socket.RealtimeCargoTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class CargoEventListener implements ApplicationListener<ApplicationEvent> {
	
	@Autowired
	private RealtimeCargoTrackingService realtimeCargoTrackingService;
	
	private final Logger logger = LoggerFactory.getLogger(CargoEventListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if(event instanceof CargoEvent) {
			CargoEvent cargoEvt = (CargoEvent)event;
			logger.debug("## Cargo received in CargoEventListener ##");
			
			realtimeCargoTrackingService.onCargoInspected(cargoEvt.getCargo());
	    }
	}

}
