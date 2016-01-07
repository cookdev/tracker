package org.anyframe.cloud.cargo.tracker.application.internal;

import org.anyframe.cloud.cargo.tracker.application.BookingService;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.*;
import org.anyframe.cloud.cargo.tracker.domain.model.location.Location;
import org.anyframe.cloud.cargo.tracker.domain.model.location.LocationRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.location.UnLocode;
import org.anyframe.cloud.cargo.tracker.domain.service.RoutingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DefaultBookingService implements BookingService {

    @Inject
    private CargoRepository cargoRepository;
    @Inject
    private LocationRepository locationRepository;
    @Inject
    private RoutingService routingService;
    // TODO See if the logger can be injected.
    private static final Logger logger = LoggerFactory.getLogger(
            DefaultBookingService.class);

    @Override
    public TrackingId bookNewCargo(UnLocode originUnLocode,
            UnLocode destinationUnLocode,
            Date arrivalDeadline) {
        TrackingId trackingId = cargoRepository.nextTrackingId();
        Location origin = locationRepository.find(originUnLocode);
        Location destination = locationRepository.find(destinationUnLocode);
        RouteSpecification routeSpecification = new RouteSpecification(origin,
                destination, arrivalDeadline);

        Cargo cargo = new Cargo(trackingId, routeSpecification);

        cargoRepository.store(cargo);
        logger.debug("Booked new cargo with tracking id {}",
                cargo.getTrackingId().getIdString());

        return cargo.getTrackingId();
    }

    @Override
    public List<Itinerary> requestPossibleRoutesForCargo(TrackingId trackingId) {
        Cargo cargo = cargoRepository.find(trackingId);

        if (cargo == null) {
            return Collections.emptyList();
        }

        return routingService.fetchRoutesForSpecification(cargo.getRouteSpecification());
    }

    @Override
    public void assignCargoToRoute(Itinerary itinerary, TrackingId trackingId) {
        Cargo cargo = cargoRepository.find(trackingId);

        cargo.assignToRoute(itinerary);
        cargoRepository.store(cargo);

        logger.debug("Assigned cargo {} to new route", trackingId);
    }

    @Override
    public void changeDestination(TrackingId trackingId, UnLocode unLocode) {
        Cargo cargo = cargoRepository.find(trackingId);
        Location newDestination = locationRepository.find(unLocode);

        RouteSpecification routeSpecification = new RouteSpecification(
                cargo.getOrigin(), newDestination,
                cargo.getRouteSpecification().getArrivalDeadline());
        cargo.specifyNewRoute(routeSpecification);

        cargoRepository.store(cargo);

        logger.debug("Changed destination for cargo {} to {}",
                new Object[]{trackingId, routeSpecification.getDestination()});
    }
}
