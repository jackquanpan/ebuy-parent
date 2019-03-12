package com.java.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * date:2019-03-06
 * 17:07
 * description:ZFprovider1Start
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages = "com.java.controller")
@EnableEurekaClient
@EnableDiscoveryClient
public class ZFprovider1Start {
    public static void main(String[] args) {
        SpringApplication.run(ZFprovider1Start.class);
    }
}

