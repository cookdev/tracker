package org.anyframe.cloud.cargo.tracker.domain.model.voyage;

import org.anyframe.cloud.cargo.tracker.CargoTrackerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes= CargoTrackerApplication.class)
@WebAppConfiguration
public class VoyageRepositoryIntegrationTests {
	
	@Autowired
	private VoyageRepository voyageRepository;
	
	@Value("0300A")
	private String voyageNumber;
	
	/**
	 * Test findTest
	 * input 
	 * 	- voyageNumber : 0300A
	 */
	@Test
	@Transactional
	public void findTest() {
		VoyageNumber testVoyageNumber = new VoyageNumber(this.voyageNumber);
		
		// Test method(find) Execution
		Voyage resultVoyage = voyageRepository.find(testVoyageNumber);
		
		// Result check
		assertEquals("Result voyage's number would be 0300A", this.voyageNumber, resultVoyage.getVoyageNumber().getIdString());
	}
	
}
