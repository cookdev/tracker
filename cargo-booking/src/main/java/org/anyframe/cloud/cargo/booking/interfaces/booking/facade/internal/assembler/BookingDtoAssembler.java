package org.anyframe.cloud.cargo.booking.interfaces.booking.facade.internal.assembler;

import org.anyframe.cloud.cargo.booking.domain.model.booking.Booking;
import org.anyframe.cloud.cargo.booking.interfaces.booking.facade.dto.BookingDto;

public class BookingDtoAssembler {

	public BookingDto toDto(Booking booking) {
		BookingDto dto = new BookingDto(
				booking.getBookingId().getIdString(),
				booking.getTrackingId(),
				booking.getOrigin().getName() + " (" + booking.getOrigin().getUnLocode().getIdString() + ")",
				booking.getDestination().getName() + " (" + booking.getDestination().getUnLocode().getIdString() + ")",
				booking.getArrivalDeadline(),
				booking.getUserId(),
				booking.getCommodity(),
				booking.getQuantity(),
				booking.getBookingStatus().name());
		return dto;
		
	}
}
