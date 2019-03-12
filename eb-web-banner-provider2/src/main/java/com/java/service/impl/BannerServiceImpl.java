package com.java.service.impl;

import com.java.mapper.BannerMapper;
import com.java.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * date:2019-02-18
 * 16:52
 * description:BannerServiceImpl
 * author:潘全科
 */
@Service
@Transactional(readOnly = false)
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public List<Map<String,Object>>getAllbanners(){
        try {
            ValueOperations ops = redisTemplate.opsForValue();
            Object webBanner = ops.get("WebBanner");
            if(webBanner==null){
                //从mysql中读取在存在redis
                List<Map<String, Object>> bunnerList = bannerMapper.getWebanner();
                //存入redis
                ops.set("WebBanner",bunnerList);
                //设置失效时间
                redisTemplate.expire("WebBanner",5, TimeUnit.MINUTES);
                return bunnerList;
            }
            return (List<Map<String,Object>>)webBanner;
        } catch (Exception e) {
            System.err.println("IndexServiceImpl......findWebMenus....Redis忘记开启了");
            return bannerMapper.getWebanner();
        }
    }
}
