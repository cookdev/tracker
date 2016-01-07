package org.anyframe.cloud.cargo.ui.web;

import org.anyframe.cloud.cargo.ui.web.CargoUiWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CargoUiWebApplication.class)
@WebAppConfiguration
public class CargoUiWebApplicationTests {

	@Test
	public void contextLoads() {
	}

}
