package com.middle.stage.test.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.middle.stage.test.dao.mapper")
public class MiddleStageTestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddleStageTestServiceApplication.class, args);
    }

}
