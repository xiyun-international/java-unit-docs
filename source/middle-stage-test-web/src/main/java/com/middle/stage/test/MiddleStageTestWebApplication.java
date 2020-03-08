package com.middle.stage.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.middle.stage.test.mapper")
public class MiddleStageTestWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddleStageTestWebApplication.class, args);
    }

}
