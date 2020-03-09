package com.middle.stage.test.coverage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.middle.stage.test.coverage.mapper")
public class MiddleStageTestCoverageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddleStageTestCoverageApplication.class, args);
    }

}
