package org.anyframe.cloud.cargo.tracker.application;

import org.anyframe.cloud.cargo.tracker.CargoTrackerApplication;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Cargo;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.CargoRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.RouteSpecification;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.TrackingId;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEvent;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEventRepository;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingHistory;
import org.anyframe.cloud.cargo.tracker.domain.model.location.Location;
import org.anyframe.cloud.cargo.tracker.domain.model.location.UnLocode;
import org.anyframe.cloud.cargo.tracker.infrastructure.events.spring.CargoEvent;
import org.anyframe.cloud.cargo.tracker.util.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CargoTrackerApplication.class)
@WebAppConfiguration
public class CargoInspectionServiceUnitTests {

	@Mock
	private ApplicationEvents mockApplicationEvents;

	@Mock
	private CargoRepository mockCargoRepository;

	@Mock
	private HandlingEventRepository mockHandlingEventRepository;

	@Mock
	private ApplicationContext mockCtx;

	@InjectMocks
	@Autowired
	private CargoInspectionService cargoInspectionService;

	private TrackingId trackingId;

	private Cargo cargo;

	@Before
	public void setUp() throws Exception {
		// Mock setting
		MockitoAnnotations.initMocks(this);

		ReflectionTestUtils.setField(TestUtils.unwrapProxy(cargoInspectionService), "cargoRepository", mockCargoRepository);
		ReflectionTestUtils.setField(TestUtils.unwrapProxy(cargoInspectionService), "applicationEvents", mockApplicationEvents);
		ReflectionTestUtils.setField(TestUtils.unwrapProxy(cargoInspectionService), "handlingEventRepository", mockHandlingEventRepository);
		ReflectionTestUtils.setField(TestUtils.unwrapProxy(cargoInspectionService), "ctx", mockCtx);
	}

	/**
	 * Test inpectCargoTest input - trackingId
	 */
	@Test
	public void inpectCargoTest() {
		this.trackingId = new TrackingId("CARGO123");

		this.cargo = new Cargo(this.trackingId, new RouteSpecification(
							new Location(new UnLocode("USCHI"), "Chicago"),
							new Location(new UnLocode("JNTKO"), "Tokyo"), 
							new Date()));
		when(mockCargoRepository.find(this.trackingId)).thenReturn(this.cargo);
		when(mockHandlingEventRepository.lookupHandlingHistoryOfCargo(this.trackingId))
				.thenReturn(new HandlingHistory(new ArrayList<HandlingEvent>()));

		cargoInspectionService.inspectCargo(this.trackingId);

		verify(mockCargoRepository).find(this.trackingId);
		verify(mockHandlingEventRepository).lookupHandlingHistoryOfCargo(this.trackingId);
		verify(mockCargoRepository).store((Cargo) any());
		verify(mockCtx).publishEvent((CargoEvent) any());
	}
}
