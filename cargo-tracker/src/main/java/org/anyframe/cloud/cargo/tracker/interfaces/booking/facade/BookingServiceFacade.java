package org.anyframe.cloud.cargo.tracker.interfaces.booking.facade;

import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.CargoRoute;
import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.Location;
import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.RouteCandidate;

import java.util.Date;
import java.util.List;

/**
 * This facade shields the domain layer - model, services, repositories - from
 * concerns about such things as the user interface and remoting.
 */
public interface BookingServiceFacade {

    String bookNewCargo(String origin, String destination, Date arrivalDeadline);

    CargoRoute loadCargoForRouting(String trackingId);

    void assignCargoToRoute(String trackingId, RouteCandidate route);

    void changeDestination(String trackingId, String destinationUnLocode);

    List<RouteCandidate> requestPossibleRoutesForCargo(String trackingId);

    List<Location> listShippingLocations();

    List<CargoRoute> listAllCargos();
}
