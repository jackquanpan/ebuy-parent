package com.java.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * date:2019-01-26
 * 13:49
 * description:ERK1Start
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages ={"com.java.controller"})
@EnableEurekaServer//开启注册中心
public class ERK1Start {
    public static void main(String[] args) {
        SpringApplication.run(ERK1Start.class);
    }
}
