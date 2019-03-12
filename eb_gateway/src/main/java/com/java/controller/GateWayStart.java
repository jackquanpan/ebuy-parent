package com.java.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * date:2019-01-26
 * 15:06
 * description:GateWayStart
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages = {"com.java.controller"})
@EnableEurekaClient
@EnableZuulProxy//代理网关
@EnableDiscoveryClient
public class GateWayStart {
    public static void main(String[] args) {
        SpringApplication.run(GateWayStart.class);
    }
}
