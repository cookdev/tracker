package org.anyframe.cloud.cargo.aggregation.interfaces.aggregation.facade.internal;

import org.anyframe.cloud.cargo.aggregation.infrastructure.booking.BookingDto;
import org.anyframe.cloud.cargo.aggregation.infrastructure.booking.CargoBookingService;
import org.anyframe.cloud.cargo.aggregation.infrastructure.tracking.CargoRoute;
import org.anyframe.cloud.cargo.aggregation.infrastructure.tracking.CargoTrackerService;
import org.anyframe.cloud.cargo.aggregation.interfaces.aggregation.facade.AggregationServiceFacade;
import org.anyframe.cloud.cargo.aggregation.interfaces.aggregation.facade.dto.DashboardDto;
import org.anyframe.cloud.cargo.aggregation.interfaces.aggregation.facade.internal.assembler.DashboardDtoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultAggregationServiceFacade implements
		AggregationServiceFacade {

	@Autowired
	private CargoBookingService cargoBookingService;
	
	@Autowired
	private CargoTrackerService cargoTrackerService;
	
	@Override
	public DashboardDto getDashboard() {
		List<BookingDto> bookingList = cargoBookingService.listBookings();
		List<CargoRoute> trackingList = cargoTrackerService.listTrackings();
		
		DashboardDtoAssembler assembler = new DashboardDtoAssembler();
		DashboardDto dashboard = assembler.toDto(bookingList, trackingList);
		
		return dashboard;
	}

}
