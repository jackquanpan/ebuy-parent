package com.java.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * date:2019-01-26
 * 14:08
 * description:ERK2Start
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages = {"com.java.controller"})
@EnableEurekaServer
public class ERK2Start {
    public static void main(String[] args) {
        SpringApplication.run(ERK2Start.class);
    }
}
