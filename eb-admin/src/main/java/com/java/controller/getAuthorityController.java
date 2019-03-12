package com.java.controller;

import com.java.service.getAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pojo.OneMenu;

import java.util.List;
import java.util.Map;

/**
 * date:2019-02-15
 * 13:56
 * description:getAuthorityController
 * author:潘全科
 */
@Controller
public class getAuthorityController {
    @Autowired
    private getAuthorityService getAuthorityService;

    /**
     * 获取所有用户
     * @return
     */
    @RequestMapping("/getAlluser.do")
    public @ResponseBody
    List<Map<String,Object>> getAllUser(){

      return getAuthorityService.findSysUser();
    }

    /**
     * 获取权限
     * @param id
     * @return
     */
    @RequestMapping("/getAuthority.do")
    public @ResponseBody List<Map<String,Object>>getAuthority(@RequestParam(name="id",defaultValue = "0") Long id){
        return getAuthorityService.findAuthority(id);
    }

    /**
     * 看用户是否已经存在
     * @param username
     * @return
     */
    @RequestMapping("/isusernameExits.do")
    public @ResponseBody boolean finduser(String username){
        boolean b = getAuthorityService.finduser(username);
        return  b;
    }

    /**
     * 添加用户
     * @param username
     * @param pwd
     * @param menuId
     * @return
     */
    @RequestMapping("/addSystemUser.do")
    public @ResponseBody boolean  addSystemuser(String username,String pwd,String menuId){
        if(username==null||pwd==null){
            return false;
        }
       boolean flag1=username.matches(".{4,10}");
        boolean flag2=pwd.matches(".{4,10}");
        if(!flag1 || !flag2){
            return false;
        }
       return  getAuthorityService.saveSystemUser(username,pwd,menuId);
    }
    /**
     * 获取权限信息，通过关联关系描述
     * @return
     */
    @RequestMapping("findAuthorityByRelation.do")
    public @ResponseBody List<OneMenu> findAuthorityByRelation(){
        return getAuthorityService.findAuthorityByRelation();
    }

    @RequestMapping("/editAuthority.do")
    public @ResponseBody boolean editUserAuthority(Long userId,String menuid){
        boolean b = getAuthorityService.deletaUserAuthority(userId, menuid);
        return b;
    }
    @RequestMapping("/showAuthority.do")
    public @ResponseBody List<Long>showSelectAuthority(Long userId){
        return getAuthorityService.getUserAllAuthority(userId);
    }

}
