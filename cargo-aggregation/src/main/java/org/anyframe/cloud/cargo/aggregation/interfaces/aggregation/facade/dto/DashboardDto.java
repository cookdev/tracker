package org.anyframe.cloud.cargo.aggregation.interfaces.aggregation.facade.dto;

import org.anyframe.cloud.cargo.aggregation.infrastructure.booking.BookingDto;
import org.anyframe.cloud.cargo.aggregation.infrastructure.tracking.CargoRoute;

import java.io.Serializable;
import java.util.List;

public class DashboardDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<BookingDto> bookingList;
    
    private List<CargoRoute> trackingList;
    
    public DashboardDto() {
    }

	public DashboardDto(List<BookingDto> bookingList,
			List<CargoRoute> trackingList) {
		super();
		this.bookingList = bookingList;
		this.trackingList = trackingList;
	}

	public List<BookingDto> getBookingList() {
		return bookingList;
	}

	public List<CargoRoute> getTrackingList() {
		return trackingList;
	}

	public void setBookingList(List<BookingDto> bookingList) {
		this.bookingList = bookingList;
	}

	public void setTrackingList(List<CargoRoute> trackingList) {
		this.trackingList = trackingList;
	}
    
}
