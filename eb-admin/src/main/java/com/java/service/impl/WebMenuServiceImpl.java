package com.java.service.impl;

import com.java.mapper.WebMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pojo.Banner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = false)
public class WebMenuServiceImpl implements com.java.service.WebMenuService {
    @Autowired
    private WebMenuMapper menuMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 分页查询前台横向导航栏数据
     * @param startIndex
     * @param pagesize
     * @return
     */
    @Override
    public Map<String,Object>findWebMenu(Integer startIndex, Integer pagesize){
      Map<String,Object>map=new HashMap<>();
      //1.调用dao层分页数据
        List<Map<String, Object>> list = menuMapper.selectWebMenu(startIndex, pagesize);
        //2、调用dao层获取web_menu表中的总记录数
        int total = menuMapper.countWebMenu();
        map.put("rows",list);
        map.put("total",total);
        return map;
    }

    /**
     * 增加
     * @param resultmap
     * @return
     */
    @Override
    public int insertMenu(Map<String,Object>resultmap) {
        if(this.esixt("hxWebmenu")){
            redisTemplate.delete("hxWebmenu");
            System.out.println("清除redis");
        }
        return menuMapper.insert(resultmap);
    }

    /**
     * 前端修改时更改redis实现实时更新
     * @param map
     * @return
     */

    @Override
    public int updateMenu(Map<String, Object> map) {
        if(this.esixt("hxWebmenu")){
            redisTemplate.delete("hxWebmenu");
            System.out.println("清除redis");
        }
        return menuMapper.update(map);

    }

    /**
     * 前端修改时更改redis实现实时更新
     * @param map
     * @return
     */
    @Override
    public int deleteMenu(Map<String, Object> map) {
        if(this.esixt("hxWebmenu")){
            redisTemplate.delete("hxWebmenu");
            System.out.println("清除redis");
        }
        return menuMapper.deleteMenu(map);
    }


    @Override
    public boolean esixt(String name) {
        ValueOperations ops = redisTemplate.opsForValue();
        Object hxWebmenu = ops.get("hxWebmenu");
        if(hxWebmenu==null){
            return false;
        }
       return true;
    }

    /**
     *分页查看轮播图
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public Map<String, Object> findBanners(Integer startIndex, Integer rows) {
        //获取分页数据
        List<Banner> bannerList = menuMapper.selectBanner(startIndex, rows);
        //获取total
        int countBanner = menuMapper.countBanner();
        Map<String,Object>resultMap=new HashMap<>();
        resultMap.put("tatal",countBanner);
        resultMap.put("rows",bannerList);
        return resultMap;
    }

    @Override
    public int insertBanner(Map<String, Object> map) {
        return menuMapper.insertBanner(map);
    }
}
