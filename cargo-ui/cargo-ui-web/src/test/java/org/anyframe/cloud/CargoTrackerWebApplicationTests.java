package org.anyframe.cloud;

import org.anyframe.web.CargoTrackerWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CargoTrackerWebApplication.class)
@WebAppConfiguration
public class CargoTrackerWebApplicationTests {

	@Test
	public void contextLoads() {
	}

}
