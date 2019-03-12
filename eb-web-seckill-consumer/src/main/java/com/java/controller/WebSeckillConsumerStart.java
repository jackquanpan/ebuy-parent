package com.java.controller;

import com.java.service.OrderService;
import org.mybatis.spring.annotation.MapperScan;
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

import java.util.Map;

/**
 * date:2019-02-25
 * 14:41
 * description:WebSeckillConsumerStart
 * author:潘全科
 */
@SpringBootApplication(scanBasePackages ="com.java.*")
@EnableEurekaClient
@EnableDiscoveryClient
@MapperScan("com.java.mapper")
@Controller
//开启过滤器扫描
@ServletComponentScan(basePackages = "com.java.filter")

public class WebSeckillConsumerStart {
    //注入负载均衡工具类 restTemplate,rest调用
    @Bean
    @LoadBalanced//开启负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(WebSeckillConsumerStart.class);
    }
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderService orderService;
    @RequestMapping("/doSeckillByConsumer.do")
    public@ResponseBody
    Map<String,Object>doSeckillByConsumer(Integer sckillId, Integer userId){
     //1.处理秒杀模块
        Map<String,Object> resultmap = restTemplate.getForObject("http://eb-web-seckill-provider/processSeckill.do", Map.class);
    //2.往消息对列中存放数据：订单编号，存放用户唯一标识，
       if(0==((Integer)resultmap.get("status"))){
           String orderNo = orderService.sendData2MQ(userId,sckillId);
           resultmap.put("orderNo",orderNo);
       }
        return resultmap;
    }
    @RequestMapping("/checkOrder.do")
    public@ResponseBody boolean checkoder(String orderNo){
     return   orderService.checkorder(orderNo);
    }
}
