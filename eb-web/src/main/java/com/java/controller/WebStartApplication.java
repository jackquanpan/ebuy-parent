package com.java.controller;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * date:2019-01-18
 * 14:03
 * description:WebStartApplication
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages ={"com.java.controller","com.java.service.impl","com.java.tasks"})
@EnableEurekaClient
@MapperScan(basePackages = {"com.java.mapper"})
@EnableCaching
@EnableScheduling//开启任务调度
public class WebStartApplication {
    @Bean
    @LoadBalanced//开启负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(WebStartApplication.class);
    }
}
