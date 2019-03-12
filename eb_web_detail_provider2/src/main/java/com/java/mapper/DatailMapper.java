package com.java.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * date:2019-02-23
 * 14:13
 * description:DatailMapper
 * author:潘全科
 */
public interface DatailMapper {
    /**
     * 查询所有商品信息
     * @return
     */
    @Select("select wpd.*,ws.sortName from web_product_detail wpd\n" +
            "inner join web_sort ws on wpd.typeId=ws.id")
    List<Map<String,Object>> getALLProductDetail();

    /**
     * 根据商品id获得图片url集合
     * @param pid
     * @return
     */
    @Select("select wpi.imgUrl,wpd.* from web_product_detail wpd inner join web_product_img wpi\n" +
            "on wpd.id=wpi.productId where wpd.id=#{arg0}")
    List<String> getProductImgURL(Integer pid);


}
