package com.java.service;


import java.util.Map;

public interface WebMenuService {
    Map<String,Object> findWebMenu(Integer startIndex, Integer pagesize);
    int insertMenu(Map<String,Object>resultmap);
    int updateMenu(Map<String,Object>map);
    int deleteMenu(Map<String,Object>map);

    boolean esixt(String name);

    Map<String,Object> findBanners(Integer startIndex, Integer pageSize);
    int insertBanner(Map<String,Object>map);
}
