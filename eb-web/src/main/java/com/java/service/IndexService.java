package com.java.service;

import com.pojo.WebUser;

import java.util.List;
import java.util.Map;

/**
 * date:2019-01-18
 * 15:34
 * description:IndexService
 * author:潘全科
 */
public interface IndexService {
    List<Map<String,Object>> findhxMenu(String menuType);
    List<Map<String,Object>> findZXMenu();
    void clearZxMenu();
    boolean esxitUser(String username);
    boolean  addSMS(String smsCode,String phone);
    boolean insertUser(WebUser user,Map<String,Object>errorMap);
    List<Map<String,Object>>getALLProductDetail();
    List<Map<String,Object>>findUpdateDetail();
    //秒杀
    void processSeckill();
    void updateNoStart2Finish();
    List<Map<String,Object>> findSeckillProductDetai();
}
