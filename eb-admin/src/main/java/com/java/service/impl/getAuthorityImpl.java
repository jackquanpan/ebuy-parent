package com.java.service.impl;

import com.java.mapper.getAuthority;
import com.java.service.getAuthorityService;
import com.java.utils.MD5Too;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pojo.OneMenu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * date:2019-02-15
 * 13:32
 * description:getAuthorityImpl
 * author:潘全科
 */
@Service
@Transactional(readOnly = false)
public class getAuthorityImpl implements getAuthorityService {
    @Autowired
    private getAuthority getAuthority;

    @Override
    public List<Map<String, Object>> findSysUser() {

        List<Map<String, Object>> list = getAuthority.getSysUser();
        return list;
    }

    @Override
    public List<Map<String, Object>> findAuthority(Long parentId) {
        return getAuthority.getAuthority(parentId);
    }

    /**
     * 查看用户名是否已经存在
     * @param username
     * @return
     */
    @Override
    public boolean finduser(String username) {
      int i=getAuthority.finduser(username);
      boolean flag= i==0?false:true;
        return flag;
    }

    /**
     * 添加账户和对应权限添加
     * @param username
     * @param pwd
     * @param menuIds
     * @return
     */

    @Transactional(readOnly = false)
    @Override
    public boolean saveSystemUser(String username, String pwd, String menuIds) {
        Map<String, Object> parammap = new HashMap<>();
        parammap.put("username", username);
        try {
            parammap.put("pwd", MD5Too.Md5(pwd));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int flag = getAuthority.insertUser(parammap);
        if (flag >= 1) {
            Long userId = (Long) parammap.get("userId");
            String[] maneuId = menuIds.split("\\,");
            for (String s : maneuId) {
                long id = Integer.parseInt(s);
                int flag2 = getAuthority.insertAuthority(userId, id);
                if (flag2 <= 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 获取权限对应关系
     * @return
     */
    @Override
    public List<OneMenu> findAuthorityByRelation() {
        return getAuthority.getAuthorityByRelation();
    }

    /**
     * 删除用户权限并注册权限
     * @param userId
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public boolean deletaUserAuthority(Long userId,String menuid) {
       int i= getAuthority.deleteauthority(userId);
       if(i>=1){
           String[] split = menuid.split("\\,");
           for (String o :split ) {
               Long mid=Long.parseLong(o);
               int i1 = getAuthority.insertAuthority(userId, mid);
               if(i1<=0){
                   return false;
               }
           }
           return true;
       }
        return false;
    }

    /**
     * 回显权限信息
     * @param userId
     * @return
     */
    @Override
    public List<Long> getUserAllAuthority(Long userId) {
        return getAuthority.getUserAuthority(userId);
    }
}
