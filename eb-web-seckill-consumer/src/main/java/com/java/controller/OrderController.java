package com.java.controller;

import com.java.service.OrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * date:2019-02-25
 * 17:19
 * description:订单结算 自动从消息对列中存放到数据库
 * author:潘全科
 */
@Component
public class OrderController {
    @Autowired
    private OrderService orderService;
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "queue-order"),
                    exchange = @Exchange(value = "exchange-order",type = "fanout")
            )
    )
    @RabbitHandler
    public void handleMQMessage(@Payload Map<String,Object> datamap, Channel channel,@Header Map<String,Object>header){
        try {
            //1.对接支付宝或者微信
            System.out.println("-----------安全扫描----------------");
           Thread.sleep(5000);
            System.out.println("----------支付宝模块加载完成------------");
            //2.从消息对列取出datamap数据
            String orderNo=(String)datamap.get("orderNo");
            Integer userId=(Integer)datamap.get("userId");
            System.out.println("-----订单编号"+orderNo);
            //3.将ddatmap的订单表中数据存到数据库中去
            orderService.addorder(datamap);
            System.out.println("-------数据已经进入数据库------");
            //4.手动确认正确的从消息队列中取出消息，并处理完毕
            Integer tag= (Integer)header.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(tag,false);//消息队列确认
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
