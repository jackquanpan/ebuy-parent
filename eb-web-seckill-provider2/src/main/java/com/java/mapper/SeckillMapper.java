package com.java.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * date:2019-02-24
 * 21:42
 * description:SeckillMapper
 * author:潘全科
 */
public interface SeckillMapper {
    /**
     * 根据秒杀id获取详细的商品信息
     * @param seckillId
     * @return
     */
    @Select("SELECT * FROM web_seckill WHERE id = #{arg0}")
    Map<String,Object> getProductBySeckillId(Integer seckillId);

}
