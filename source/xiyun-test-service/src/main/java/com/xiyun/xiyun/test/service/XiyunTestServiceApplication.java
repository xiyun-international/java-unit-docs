package com.xiyun.xiyun.test.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.xiyun.xiyun.test.dao.mapper")
public class XiyunTestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiyunTestServiceApplication.class, args);
    }

}
