package org.anyframe.cloud.cargo.booking.application;

import org.anyframe.cloud.cargo.booking.domain.model.booking.Booking;
import org.anyframe.cloud.cargo.booking.domain.model.booking.BookingId;
import org.anyframe.cloud.cargo.booking.domain.model.booking.BookingStatus;

import java.util.Map;

public interface BookingService {

	Booking registerBooking(Map<String, String> bookingInformation, BookingStatus bookingStatus);
	
	void acceptBooking(BookingId bookingId);

	String changeDestination(BookingId bookingId, String destination);
}
