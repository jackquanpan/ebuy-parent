package com.java.service.impl;

import com.java.mapper.DatailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * date:2019-02-23
 * 14:14
 * description:DatailServiceImpl
 * author:潘全科
 */
@Service
public class DatailServiceImpl implements com.java.service.DatailService {
    @Autowired
    private DatailMapper datailMapper;

    @Override
    public List<Map<String, Object>> getALLProductDetail() {
        //1.获取商品详情不包括图片
        List<Map<String, Object>> detail = datailMapper.getALLProductDetail();
        //2.添加图片链接
        for (int i = 0; i < detail.size(); i++) {
            Integer id= (Integer)detail.get(i).get("id");
            List<String> imgUrlList = datailMapper.getProductImgURL(id);
            detail.get(i).put("imgUrlList",imgUrlList);
        }
        return detail;
    }
}
