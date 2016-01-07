package org.anyframe.cloud.cargo.tracker.application.internal;

import org.anyframe.cloud.cargo.tracker.application.ApplicationEvents;
import org.anyframe.cloud.cargo.tracker.application.CargoInspectionService;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Cargo;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.CargoRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.TrackingId;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEventRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingHistory;
import org.anyframe.cloud.cargo.tracker.infrastructure.events.spring.CargoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class DefaultCargoInspectionService implements CargoInspectionService {
	
	private final Logger logger = LoggerFactory.getLogger(DefaultCargoInspectionService.class);

    @Inject
    private ApplicationEvents applicationEvents;
    
    @Inject
    private CargoRepository cargoRepository;
    
    @Inject
    private HandlingEventRepository handlingEventRepository;
    
    @Autowired
    private ApplicationContext ctx;

    @Override
    @Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
    public void inspectCargo(TrackingId trackingId) {
        Cargo cargo = cargoRepository.find(trackingId);

        if (cargo == null) {
            logger.debug("Can't inspect non-existing cargo {}", trackingId);
            return;
        }

        HandlingHistory handlingHistory = handlingEventRepository
                .lookupHandlingHistoryOfCargo(trackingId);

        cargo.deriveDeliveryProgress(handlingHistory);

        if (cargo.getDelivery().isMisdirected()) {
            applicationEvents.cargoWasMisdirected(cargo);
        }

        if (cargo.getDelivery().isUnloadedAtDestination()) {
            applicationEvents.cargoHasArrived(cargo);
            logger.debug("## Cargo has arrived ##");
        }

        cargoRepository.store(cargo);
        logger.debug("## Stored cargo ##");

        ctx.publishEvent(new CargoEvent(this, cargo));
        logger.debug("## Registered cargo websocket event ##");
    }
    
}
