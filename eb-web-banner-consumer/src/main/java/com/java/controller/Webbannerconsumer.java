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
 * date:2019-02-18
 * 15:53
 * description:Webbannerconsumer
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages = "com.java.controller")
@EnableEurekaClient
@EnableDiscoveryClient
@Controller
//开启过滤器扫描
@ServletComponentScan(basePackages = "com.java.filter")
public class Webbannerconsumer {
    //注入负载均衡工具类 restTemplate,rest调用
    @Bean
    @LoadBalanced//开启负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(Webbannerconsumer.class);
    }
    @Autowired
    private  RestTemplate restTemplate;
    //消费者调用提供者
    @RequestMapping("/findBanner.do")
    public @ResponseBody
    List<Map<String,Object>>findBanners(){
        System.out.println("WebBannerConsumerStartApplication----------进入了");
        return restTemplate.getForObject("http://eb-web-banner-provider/getWebBanners.do",List.class);
    }
}
