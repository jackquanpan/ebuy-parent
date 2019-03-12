package com.java.controller;

import com.java.service.WebMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WebMenuController {
    @Autowired
    private WebMenuService webMenuService;

    /**
     * 分页查询横向菜单
     *
     * @return
     */
    @RequestMapping("/getHxmenu.do")
    @ResponseBody
    public Map<String, Object> getHxMenus(Integer page, Integer rows) {

        int startIndex = (page - 1) * rows;
        return webMenuService.findWebMenu(startIndex, rows);
    }

    @RequestMapping("/addWebMenu.do")
    public @ResponseBody
    boolean addWebMenu(String title, String url, String menuType) {
        if (title == null || url == null || menuType == null) {
            return false;
        }
        boolean b1 = title.matches(".{1,10}");
       String check="http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
       boolean b2=url.matches(check);
        if(!b1||!b2){
            return false;
        }
       Map<String,Object>map=new HashMap<>();
        map.put("title",title);
        map.put("url",url);
        map.put("menuType",menuType);

       return webMenuService.insertMenu(map)>=1;
    }
   @RequestMapping("/updateWebMenu.do")
    @ResponseBody
    public boolean updateMenu(String title,String url,String menuType,long id){
       if (title == null || url == null || menuType == null) {
           return false;
       }
       boolean b1 = title.matches(".{1,10}");
       String check="http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
       boolean b2=url.matches(check);
       if(!b1||!b2){
           return false;
       }
       Map<String,Object>map=new HashMap<>();
       map.put("title",title);
       map.put("url",url);
       map.put("menuType",menuType);
       map.put("id",id);

       return webMenuService.updateMenu(map)>=1;
   }
   @RequestMapping("/deleteWebMenu.do")
    public @ResponseBody boolean deleteMenu(String idstr){
        //去除最后一个dou号
       idstr=idstr.substring(0,idstr.length()-1);
       Map<String,Object>map=new HashMap<>();
       map.put("idstr",idstr);

       return webMenuService.deleteMenu(map)>=1;
   }

    /**
     * 分页查询banner信息
     * @param page
     * @param rows
     * @return
     */
   @RequestMapping("/getBanners.do")
   @ResponseBody
   public Map<String,Object>getBanner(Integer page, Integer rows){
       int startIndex = (page - 1) * rows;
        return webMenuService.findBanners(startIndex,rows);
   }
   @RequestMapping("/addbanner.do")
    public @ResponseBody boolean insertBanners(String img_url,String href,String remark,Integer sort){
       //1、校验数据的正确性
       if(img_url==null ||href==null||remark==null||sort==null){
           return false;
       }
       String check="http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
       if(!href.matches(check)){
           return false;
       }

       //2、调用添加轮播信息的业务层
       Map<String,Object> paramMap = new HashMap<>();
       paramMap.put("img_url",img_url);
       paramMap.put("href",href);
       paramMap.put("remark",remark);
       paramMap.put("sort",sort);
       return webMenuService.insertBanner(paramMap)>1;
   }


}
