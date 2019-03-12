package com.java.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * date:2019-02-18
 * 16:49
 * description:BannerMapper
 * author:潘全科
 */
public interface BannerMapper {
    @Select("select * from web_banners order by sort asc ")
    List<Map<String,Object>> getWebanner();
}
