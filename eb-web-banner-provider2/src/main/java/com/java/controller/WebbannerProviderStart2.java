package com.java.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;

/**
 * date:2019-02-18
 * 15:43
 * description:WebbannerStart2
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages ={"com.java.controller","com.java.service.impl"} )
@EnableEurekaClient
@EnableDiscoveryClient
@Controller
@MapperScan(basePackages = "com.java.mapper")
public class WebbannerProviderStart2 {

    public static void main(String[] args) {
        SpringApplication.run(WebbannerProviderStart2.class);
    }
}
