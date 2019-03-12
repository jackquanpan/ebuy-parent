package com.java.service;

import com.java.exception.SeckillException;

/**
 * date:2019-02-25
 * 13:55
 * description:SeckillService
 * author:潘全科
 */
public interface SeckillService {
    void doSeckill(Integer userId, Integer seckillId) throws SeckillException;
}
