package org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.internal;

import org.anyframe.cloud.cargo.tracker.application.BookingService;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Cargo;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.CargoRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Itinerary;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.TrackingId;
import org.anyframe.cloud.cargo.tracker.domain.model.location.LocationRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.location.UnLocode;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageRepository;
import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.BookingServiceFacade;
import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.CargoRoute;
import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.RouteCandidate;
import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.internal.assembler.CargoRouteDtoAssembler;
import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.internal.assembler.ItineraryCandidateDtoAssembler;
import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.internal.assembler.LocationDtoAssembler;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DefaultBookingServiceFacade implements BookingServiceFacade,
        Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private BookingService bookingService;
    @Inject
    private LocationRepository locationRepository;
    @Inject
    private CargoRepository cargoRepository;
    @Inject
    private VoyageRepository voyageRepository;

    @Override
    public List<org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.Location> listShippingLocations() {
        List<org.anyframe.cloud.cargo.tracker.domain.model.location.Location> allLocations = locationRepository.findAll();
        LocationDtoAssembler assembler = new LocationDtoAssembler();
        return assembler.toDtoList(allLocations);
    }

    @Override
    public String bookNewCargo(String origin, String destination,
            Date arrivalDeadline) {
        TrackingId trackingId = bookingService.bookNewCargo(
                new UnLocode(origin), new UnLocode(destination),
                arrivalDeadline);
        return trackingId.getIdString();
    }

    @Override
    public CargoRoute loadCargoForRouting(String trackingId) {
        Cargo cargo = cargoRepository.find(new TrackingId(trackingId));
        CargoRouteDtoAssembler assembler = new CargoRouteDtoAssembler();
        return assembler.toDto(cargo);
    }

    @Override
    public void assignCargoToRoute(String trackingIdStr,
            RouteCandidate routeCandidateDTO) {
        Itinerary itinerary = new ItineraryCandidateDtoAssembler()
                .fromDTO(routeCandidateDTO, voyageRepository,
                        locationRepository);
        TrackingId trackingId = new TrackingId(trackingIdStr);

        bookingService.assignCargoToRoute(itinerary, trackingId);
    }

    @Override
    public void changeDestination(String trackingId, String destinationUnLocode) {
        bookingService.changeDestination(new TrackingId(trackingId),
                new UnLocode(destinationUnLocode));
    }

    @Override
    public List<CargoRoute> listAllCargos() {
        List<Cargo> cargos = cargoRepository.findAll();
        List<CargoRoute> routes = new ArrayList<>(cargos.size());

        CargoRouteDtoAssembler assembler = new CargoRouteDtoAssembler();

        for (Cargo cargo : cargos) {
            routes.add(assembler.toDto(cargo));
        }

        return routes;
    }

    @Override
    public List<RouteCandidate> requestPossibleRoutesForCargo(String trackingId) {
        List<Itinerary> itineraries = bookingService
                .requestPossibleRoutesForCargo(new TrackingId(trackingId));

        List<RouteCandidate> routeCandidates = new ArrayList<>(
                itineraries.size());
        ItineraryCandidateDtoAssembler dtoAssembler
                = new ItineraryCandidateDtoAssembler();
        for (Itinerary itinerary : itineraries) {
            routeCandidates.add(dtoAssembler.toDTO(itinerary));
        }

        return routeCandidates;
    }
}
