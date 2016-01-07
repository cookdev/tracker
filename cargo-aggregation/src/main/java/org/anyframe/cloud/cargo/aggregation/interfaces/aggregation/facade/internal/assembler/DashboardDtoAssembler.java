package org.anyframe.cloud.cargo.aggregation.interfaces.aggregation.facade.internal.assembler;

import org.anyframe.cloud.cargo.aggregation.infrastructure.booking.BookingDto;
import org.anyframe.cloud.cargo.aggregation.infrastructure.tracking.CargoRoute;
import org.anyframe.cloud.cargo.aggregation.interfaces.aggregation.facade.dto.DashboardDto;

import java.util.List;

public class DashboardDtoAssembler {

	public DashboardDtoAssembler() {
	}
	
	public DashboardDto toDto(List<BookingDto> bookingList, List<CargoRoute> trackingList) {
		DashboardDto dashboard = new DashboardDto(bookingList, trackingList);
		return dashboard;
	}
}
