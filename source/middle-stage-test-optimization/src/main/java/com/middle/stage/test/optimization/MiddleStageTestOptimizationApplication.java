package com.middle.stage.test.optimization;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.middle.stage.test.optimization.dao")
public class MiddleStageTestOptimizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiddleStageTestOptimizationApplication.class, args);
    }

}
