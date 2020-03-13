package com.middle.stage.test.unavailable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.middle.stage.test.unavailable.mapper")
public class MiddleStageTestUnavailableApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddleStageTestUnavailableApplication.class, args);
    }

}
