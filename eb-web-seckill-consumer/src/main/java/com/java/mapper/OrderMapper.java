package com.java.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * date:2019-02-25
 * 21:22
 * description:OrderMapper
 * author:潘全科
 */
public interface OrderMapper {
    /**
     * 添加订单到数据库
     * @param parammap
     * @return
     */
    @Insert("insert into web_order values(null,#{orderNo},#{userId},0,#{cost})")
    int insertOrder(Map<String,Object>parammap);

    /**
     * 秒杀主键获取秒杀产品价格
     * @param id
     * @return
     */
    @Select("select seckillPrice from web_seckill where id=#{arg0}")
    Float getSeckillPrice(Integer id);

    /**
     * 确定用户是否可以支付了
     * @param orderNo
     * @return
     */
    @Select("select count(*) from web_order where orderNo=#{arg0}")
    int checkorder(String orderNo);

}
