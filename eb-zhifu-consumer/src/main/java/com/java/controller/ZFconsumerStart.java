package com.java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 * date:2019-03-06
 * 17:21
 * description:ZFconsumerStart
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages = "com.java.controller")
@EnableEurekaClient
@EnableDiscoveryClient
@Controller
@ServletComponentScan(basePackages = "com.java.filter")
public class ZFconsumerStart {
    public static void main(String[] args) {
        SpringApplication.run(ZFconsumerStart.class);
    }
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    @Autowired
    private  RestTemplate restTemplate;


}
