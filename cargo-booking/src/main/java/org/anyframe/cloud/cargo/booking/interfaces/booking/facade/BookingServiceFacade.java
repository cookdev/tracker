package org.anyframe.cloud.cargo.booking.interfaces.booking.facade;

import org.anyframe.cloud.cargo.booking.domain.model.booking.Booking;
import org.anyframe.cloud.cargo.booking.interfaces.booking.facade.dto.BookingDetailDto;
import org.anyframe.cloud.cargo.booking.interfaces.booking.facade.dto.BookingDto;

import java.util.List;
import java.util.Map;

public interface BookingServiceFacade {

	List<BookingDto> listUserBookings(String userId);
	
	BookingDetailDto getBookingDetail(String bookingId);
	
	List<BookingDto> listNotAcceptedBookings();
	
	String registerBooking(Map<String,String> bookingInfomation);

	Booking registerAcceptedBooking(Map<String, String> bookingInfomation);	
	
	void acceptBooking(String bookingId);
	
	void changeDestination(String bookingId, String destination);

}
