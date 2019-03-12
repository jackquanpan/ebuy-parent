package com.java.service;

import com.pojo.OneMenu;

import java.util.List;
import java.util.Map;

/**
 * date:2019-02-15
 * 11:04
 * description:getAuthorityService
 * author:潘全科
 */
public interface getAuthorityService {
   List<Map<String,Object>> findSysUser();
   List<Map<String,Object>> findAuthority(Long parentId);
   boolean finduser(String username);
   boolean saveSystemUser(String username,String pwd,String menuIds);
   List<OneMenu> findAuthorityByRelation();
   boolean deletaUserAuthority(Long userId,String menuid);
   List<Long>getUserAllAuthority(Long userId);
}
