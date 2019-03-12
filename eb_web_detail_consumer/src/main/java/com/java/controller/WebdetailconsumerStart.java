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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * date:2019-02-23
 * 13:49
 * description:WebdetailconsumerStart
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages = "com.java.controller")
@EnableEurekaClient//开启eureka
@EnableDiscoveryClient//和网关有关
@Controller
//开启过滤器扫描
@ServletComponentScan(basePackages = "com.java.filter")
public class WebdetailconsumerStart {
    //负载均衡注入
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(WebdetailconsumerStart.class);
    }
    @Autowired
    private  RestTemplate restTemplate;
    @RequestMapping("/getAlldetail.do")
    public @ResponseBody
    List<Map<String,Object>>findAllDetails(){
        System.out.println("WebDetailConsumerStartApplication----------进入了");
        return restTemplate.getForObject("http://eb-web-detail-provider/getAlldetail.do", List.class);
    }
}
