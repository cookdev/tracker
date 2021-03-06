package org.anyframe.cloud.cargo.booking.interfaces.booking.facade.internal.assembler;

import org.anyframe.cloud.cargo.booking.domain.model.booking.Booking;
import org.anyframe.cloud.cargo.booking.domain.model.location.Location;
import org.anyframe.cloud.cargo.booking.domain.model.location.LocationRepository;
import org.anyframe.cloud.cargo.booking.domain.model.location.UnLocode;
import org.anyframe.cloud.cargo.booking.infrastructure.tracking.CargoRoute;
import org.anyframe.cloud.cargo.booking.infrastructure.tracking.Leg;
import org.anyframe.cloud.cargo.booking.interfaces.booking.facade.dto.BookingDetailDto;

import java.text.SimpleDateFormat;

public class BookingDetailDtoAssembler {
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

	private LocationRepository locationRepository;
	
	public BookingDetailDtoAssembler() {
	}
	
	public BookingDetailDtoAssembler(LocationRepository locationRepository) {
		this.locationRepository = locationRepository;
	}
	
	public BookingDetailDto toDto(Booking booking) {
		
		BookingDetailDto dto = new BookingDetailDto(
				booking.getBookingId().getIdString(),
				booking.getUserId(),
				booking.getCommodity(),
				booking.getQuantity(),
				booking.getBookingStatus().name(),
				booking.getOrigin().getName() + " (" + booking.getOrigin().getUnLocode().getIdString() + ")",
				booking.getDestination().getName() + " (" + booking.getDestination().getUnLocode().getIdString() + ")",
				DATE_FORMAT.format(booking.getArrivalDeadline())
		);
		return dto;
	}
	
	public BookingDetailDto toDto(Booking booking, CargoRoute cargoRoute) {
		
		BookingDetailDto dto = new BookingDetailDto(
				booking.getBookingId().getIdString(),
				booking.getUserId(),
				booking.getCommodity(),
				booking.getQuantity(),
				booking.getBookingStatus().name(),
				cargoRoute.getTrackingId(),
				cargoRoute.getOrigin(),
				cargoRoute.getFinalDestination(),
				cargoRoute.getArrivalDeadline(),
				cargoRoute.isMisrouted(),
				cargoRoute.isClaimed(),
				cargoRoute.getLastKnownLocation(),
				cargoRoute.getTransportStatus(),
				cargoRoute.getNextLocation()
		);
		for (Leg leg : cargoRoute.getLegs()) {
			Location from = locationRepository.find(new UnLocode(leg.getFromUnLocode()));
			Location to = locationRepository.find(new UnLocode(leg.getToUnLocode()));
			dto.addLeg(leg.getVoyageNumber(),
					leg.getFromUnLocode(),
					from.getName(),
					leg.getToUnLocode(),
					to.getName(),
					leg.getLoadTime(),
					leg.getUnloadTime());
        }
		return dto;
	}

}
