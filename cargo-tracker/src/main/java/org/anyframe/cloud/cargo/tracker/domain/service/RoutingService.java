package org.anyframe.cloud.cargo.tracker.domain.service;

import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Itinerary;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.RouteSpecification;

import java.util.List;

public interface RoutingService {

    /**
     * @param routeSpecification route specification
     * @return A list of itineraries that satisfy the specification. May be an
     * empty list if no route is found.
     */
    List<Itinerary> fetchRoutesForSpecification(RouteSpecification routeSpecification);
}
