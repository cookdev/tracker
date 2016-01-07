package org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.internal.assembler;

import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Itinerary;
import org.anyframe.cloud.cargo.tracker.domain.model.location.Location;
import org.anyframe.cloud.cargo.tracker.domain.model.location.LocationRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.location.UnLocode;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.Voyage;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageNumber;
import org.anyframe.cloud.cargo.tracker.domain.model.voyage.VoyageRepository;
import org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.RouteCandidate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ItineraryCandidateDtoAssembler {

    private static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("MM/dd/yyyy hh:mm a z");

    public RouteCandidate toDTO(Itinerary itinerary) {
        List<org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.Leg> legDTOs = new ArrayList<>(
                itinerary.getLegs().size());
        for (org.anyframe.cloud.cargo.tracker.domain.model.cargo.Leg leg : itinerary.getLegs()) {
            legDTOs.add(toLegDTO(leg));
        }
        return new RouteCandidate(legDTOs);
    }

    protected org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.Leg toLegDTO(
            org.anyframe.cloud.cargo.tracker.domain.model.cargo.Leg leg) {
        VoyageNumber voyageNumber = leg.getVoyage().getVoyageNumber();
        return new org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.Leg(
                voyageNumber.getIdString(),
                leg.getLoadLocation().getUnLocode().getIdString(),
                leg.getLoadLocation().getName(),
                leg.getUnloadLocation().getUnLocode().getIdString(),
                leg.getUnloadLocation().getName(),
                leg.getLoadTime(),
                leg.getUnloadTime());
    }

    public Itinerary fromDTO(RouteCandidate routeCandidateDTO,
            VoyageRepository voyageRepository,
            LocationRepository locationRepository) {
        List<org.anyframe.cloud.cargo.tracker.domain.model.cargo.Leg> legs = new ArrayList<>(routeCandidateDTO.getLegs().size());

        for (org.anyframe.cloud.cargo.tracker.interfaces.booking.facade.dto.Leg legDTO
                : routeCandidateDTO.getLegs()) {
            VoyageNumber voyageNumber = new VoyageNumber(
                    legDTO.getVoyageNumber());
            Voyage voyage = voyageRepository.find(voyageNumber);
            Location from = locationRepository.find(new UnLocode(legDTO
                    .getFromUnLocode()));
            Location to = locationRepository.find(new UnLocode(legDTO.getToUnLocode()));

            try {
                legs.add(new org.anyframe.cloud.cargo.tracker.domain.model.cargo.Leg(voyage, from, to,
                        DATE_FORMAT.parse(legDTO.getLoadTime()),
                        DATE_FORMAT.parse(legDTO.getUnloadTime())));
            } catch (ParseException ex) {
                throw new RuntimeException("Could not parse date", ex);
            }
        }

        return new Itinerary(legs);
    }
}
