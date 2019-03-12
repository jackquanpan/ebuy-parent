package com.java.service.impl;

import com.java.mapper.OrderMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * date:2019-02-25
 * 16:58
 * description:OrderServiceImpl
 * author:潘全科
 */
@Service
public class OrderServiceImpl implements com.java.service.OrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public String sendData2MQ(Integer userId,Integer seckillId) {
        //生成订单编号
        String orderNo = UUID.randomUUID().toString();
        //订单编号 用户id存放到rabbit
        Map<String,Object>datamap=new HashMap<>();
        datamap.put("orderNo",orderNo);
        datamap.put("userId",userId);
        datamap.put("seckillId",seckillId);
        rabbitTemplate.convertAndSend("exchange-order",null,datamap);
        return orderNo;
    }
@Transactional(readOnly = false)
    @Override
    public void addorder(Map<String, Object> parammap) {
        //查询出秒杀价格，将数据添加到数据库
    Float cost=(Float)orderMapper.getSeckillPrice((Integer)parammap.get("seckillid"));
        parammap.put("cost",cost);
        orderMapper.insertOrder(parammap);
    }

    @Override
    public boolean checkorder(String orderNo) {
        return orderMapper.checkorder(orderNo)>=1;
    }
}
