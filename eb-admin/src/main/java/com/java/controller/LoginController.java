package com.java.controller;

import com.java.utils.MD5Too;
import com.java.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;
    @RequestMapping("/login.do")
    public String login(String username, String pwd, HttpSession session) throws Exception {
        if(username==null ||pwd==null){
            return "login";
        }
        boolean flag1 = username.matches(".{3,12}");
        boolean flag2 = pwd.matches(".{6,12}");
       if(!flag1 || !flag2){
           return "login";
       }
        pwd=MD5Too.Md5(pwd);
        boolean flag = loginService.login(username, pwd);
        if(flag){
            session.setAttribute("username",username);
        }
        return flag? "Manager" :"login";
    }
    @RequestMapping("/getAuthorityByUsername.do")
    @ResponseBody
    public List<Map<String,Object>>getAuthorityByUsername(@RequestParam(name="id",defaultValue = "0" )long id,HttpSession session){
        String username =(String) session.getAttribute("username");
        int isroot = loginService.isroot(username);
        int flag=1;
        if(isroot==0){
             flag=0;
        }
        System.out.println(flag);
        Map<String,Object> map=new HashMap<>();
        map.put("username",username);
        map.put("id",id);
        map.put("flag",flag);
        List<Map<String, Object>> authorityByUsername = loginService.getAuthorityByUsername(map);

        return authorityByUsername;
    }
    @RequestMapping("/test1.do")
    public @ResponseBody  String test1(){
        return "hello springcloud";
    }
}
