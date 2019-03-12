package com.java.mapper;


import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface LoginMapper {
    /**
     * 登录
     * @param username
     * @param pwd
     * @return
     */
    @Select("SELECT COUNT(*) FROM admin_users WHERE username=#{username} AND pwd=#{pwd}")
    int login(String username,String pwd);

    /**
     * 获得标识
     * @param username
     * @return
     */
    @Select("SELECT isroot from admin_users where username=#{username}")
    int flag(String username);

    /**
     * 管理后台
     * @param paramMap
     * @return
     */
    List<Map<String,Object>>getAuthorityByUsername(Map<String,Object> paramMap);


}
