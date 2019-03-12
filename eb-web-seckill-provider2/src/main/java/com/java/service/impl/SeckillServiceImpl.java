package com.java.service.impl;

import com.java.exception.SeckillException;
import com.java.mapper.SeckillMapper;
import com.java.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * date:2019-02-24
 * 21:47
 * description:SeckillServiceImpl
 * author:潘全科
 */
@Service
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void doSeckill(Integer userId, Integer seckillId) throws SeckillException {
        //判断商品状态是否已经开始
        Map<String, Object> productMap = seckillMapper.getProductBySeckillId(seckillId);
       if(productMap==null){
           throw new SeckillException("秒杀商品存在");
       }
     Integer status= (Integer)productMap.get("status");
       if(status==0){
           throw new SeckillException("秒杀还未开始请稍后");
       }
       //秒杀产品已经结束
        if(status==2){
            throw new SeckillException("秒杀已经结束，下次再来");
        }
        //秒杀还在进行
        ListOperations lop = redisTemplate.opsForList();
        Integer productId=(Integer)lop.leftPop("seckill_product_"+seckillId);//减去一个商品id
        if(productId==null){
            throw new SeckillException("秒杀结束了，下次再来");
        }else{//还有商品可以抢购
            SetOperations sop = redisTemplate.opsForSet();
        Boolean flag= sop.isMember("seckill_user_"+seckillId,userId);
        if(flag){
            lop.rightPush("seckill_product_"+seckillId,productId);//前边减过
            throw new SeckillException("秒杀名额只有一个，不能重复抢购，下次再来吧");
        }else{
            System.out.println("SeckillService.....抢购成功");
            sop.add("seckill_user_"+seckillId,userId);
        }

        }
    }
}
