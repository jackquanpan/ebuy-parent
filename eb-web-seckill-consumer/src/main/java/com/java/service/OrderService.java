package com.java.service;

import java.util.Map;

/**
 * date:2019-02-25
 * 16:58
 * description:OrderService
 * author:潘全科
 */
public interface OrderService {
    String sendData2MQ(Integer userId,Integer seckillId);
    void addorder(Map<String,Object> parammap);
    boolean checkorder(String orderNo);
}
