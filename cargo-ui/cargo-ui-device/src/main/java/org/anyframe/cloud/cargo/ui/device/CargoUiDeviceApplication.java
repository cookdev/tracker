package org.anyframe.cloud.cargo.ui.device;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CargoUiDeviceApplication {

    private static final Logger logger = LoggerFactory.getLogger(CargoUiDeviceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CargoUiDeviceApplication.class, args);
        logger.info("Device UI가 구동되었습니다.");
    }
}
