package org.anyframe.cloud.cargo.tracker.application.internal;

import org.anyframe.cloud.cargo.tracker.application.ApplicationEvents;
import org.anyframe.cloud.cargo.tracker.application.HandlingEventService;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.TrackingId;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.CannotCreateHandlingEventException;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEvent;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEventFactory;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEventRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.location.UnLocode;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class DefaultHandlingEventService implements HandlingEventService {
	
	private final Logger logger = LoggerFactory.getLogger(DefaultHandlingEventService.class);

	@Autowired
    private ApplicationEvents applicationEvents;
	
	@Autowired
    private HandlingEventRepository handlingEventRepository;
	
	@Autowired
    private HandlingEventFactory handlingEventFactory;

    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public void registerHandlingEvent(Date completionTime,
            TrackingId trackingId, VoyageNumber voyageNumber, UnLocode unLocode,
            HandlingEvent.Type type) throws CannotCreateHandlingEventException {
    	
    	Date registrationTime = new Date();
      
        /* Using a factory to create a HandlingEvent (aggregate). This is where
         it is determined wether the incoming data, the attempt, actually is capable
         of representing a real handling event. */
    	HandlingEvent event = handlingEventFactory.createHandlingEvent(
                registrationTime, completionTime, trackingId, voyageNumber, unLocode, type);

        /* Store the new handling event, which updates the persistent
         state of the handling event aggregate (but not the cargo aggregate -
         that happens asynchronously!)
         */
    	handlingEventRepository.store(event);
    	logger.debug("## Stored handling event ##");
       
        /* Publish an event stating that a cargo has been handled. */
    	applicationEvents.cargoWasHandled(event);
    	logger.debug("## Registered handling event ##");
    }
    
}