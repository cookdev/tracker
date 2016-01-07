package org.anyframe.cloud.cargo.ui.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CargoUiWebApplication {

    private static final Logger logger = LoggerFactory.getLogger(CargoUiWebApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CargoUiWebApplication.class, args);
        logger.info("Web UI가 구동되었습니다.");
    }
}
