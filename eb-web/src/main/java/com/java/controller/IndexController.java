package com.java.controller;

import com.alibaba.fastjson.JSON;
import com.java.service.IndexService;
import com.java.utils.MD5Too;
import com.java.utils.SMSTool;
import com.pojo.web.BuyCar;
import com.pojo.web.GoodITem;
import freemarker.template.Configuration;
import freemarker.template.Template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.pojo.WebUser;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileWriter;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * date:2019-01-18
 * 15:36
 * description:IndexController
 * author:潘全科
 */
@Controller
public class IndexController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IndexService indexService;
    @Autowired
    private Configuration fkConfig;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取横向菜单栏
     *
     * @return
     */
    @RequestMapping("/getHxmenu.do")
    public @ResponseBody
    List<Map<String, Object>> findHxmenu() {
        String menuType = "1";
        return indexService.findhxMenu(menuType);
    }

    //通过注解式来开发redis
    @RequestMapping("/getZXByRedisZJ.do")
    public @ResponseBody
    List<Map<String, Object>> getZXByRedisZJ() {
        return indexService.findZXMenu();
    }

    //通过注解式来开发redis
    @RequestMapping("/clear.do")
    public void clear() {
        indexService.clearZxMenu();
    }

    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     */
    @RequestMapping("/sendSMS.do")
    public @ResponseBody
    Map<String, Object> sendSMS(String phone) {
        Map<String, Object> smsMap = SMSTool.sendSMS(phone);
        if ("0".equals(smsMap.get("error"))) {
            boolean flag = indexService.addSMS(smsMap.get("smsCode") + "", phone);
            if (!flag) {
                smsMap.put("error", 1);
            }
        }
        return smsMap;
    }

    /**
     * 查看username是否存在
     *
     * @param username
     * @return
     */
    @RequestMapping("isUsernameExits.do")
    public @ResponseBody
    boolean isUseranmeExits(String username) {
        //首先校验：username的规范,6~12位
        if (username == null) {
            return true;
        }
        if (!username.matches("\\w{6,12}")) {
            return true;
        }
        return indexService.esxitUser(username);
    }

    /**
     * 注册
     *
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("register.do")
    public String register(WebUser user, Model model) {
        //1.校验格式是否正确
        Map<String, Object> errorMap = new HashMap<>();
        //2判断手机号和邮箱在数据中是否存在
        boolean flag = indexService.insertUser(user, errorMap);
        if (flag) {
            return "Login";
        }
        return "Regist";
    }
    //-----------------------------------------------------------------

    /**
     * 通过freemaker动态生成html
     *
     * @param
     * @throws Exception
     */
    @RequestMapping("/doStaticProdectDetail.do")
    public @ResponseBody
    String doStaticProdectDetail() throws Exception {
        List<Map<String, Object>> productDetail = indexService.getALLProductDetail();
        //将数据保存到request域中并且跳转到Product--->Product.html
        //frmake取出数据后生成静态html页面（product.html）
        //1.获取指定模板
        File file = null;

        for (Map<String, Object> tempmap : productDetail) {
            Template template = fkConfig.getTemplate("Product.ftl");
            file = new File("E:\\fremaker\\details\\" + tempmap.get("id") + ".html");
            FileWriter out = new FileWriter(file);
            template.process(tempmap, out);
            out.close();
        }

        return file.getPath();

    }

    /**
     * 配置默认访问首页
     *
     * @return
     */
    @RequestMapping("/")
    public String defaultHomeHTML() {
        return "redirect:/toIndex.do";
    }

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/toIndex.do")
    public String toIndex(Model model) {
        //详情页面
        List<Map<String, Object>> productDetail = restTemplate.getForObject("http://eb-web-detail-provider/getAlldetail.do", List.class);
        System.out.println("进入detail负载均衡");
        //List<Map<String, Object>> productDetail = indexService.getALLProductDetail();
        model.addAttribute("productDetail", productDetail);
        //封装轮播图片
        List<Map<String, Object>> bannerList = restTemplate.getForObject("http://eb-web-banner-provider/getWebBanners.do", List.class);
        System.out.println("进入banner负载均衡");
        model.addAttribute("bannerList", bannerList);
        //获取秒杀信息
        List<Map<String, Object>> seckillProductList = indexService.findSeckillProductDetai();
        System.out.println(seckillProductList.size());
        for (int i = 0; i < seckillProductList.size(); i++) {
            Map<String, Object> map = seckillProductList.get(i);
            System.out.println(map.get("title"));
            model.addAttribute("map" + i, map);
        }
        return "Index";
    }

    /**
     * 添加商品到购物车
     */
    @RequestMapping("/addTobuycar.do")
    public void addBuyCar(Integer productId, Integer count, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        String t1 = MD5Too.initMd5("&123&");
        String t2 = MD5Too.initMd5("&@456&");
        String t3 = MD5Too.initMd5("&@9527&");
        //判断有没有登录
        Object username = session.getAttribute("username");
        //没有登录
        //获取cookie,好卖网cookie的key值key:hmwBuyCar
        Cookie[] cookies = request.getCookies();

        BuyCar buycar = null;
        boolean flag = true;
        //Cookie存在
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            flag=false;
            //取出cookie的name
            String cookiename = cookies[i].getName();
            if ("hmwBuyCar".equals(cookiename)) {//找到对应的cookie
                String cookieValue = cookies[i].getValue();
                cookieValue = cookieValue.replaceAll(t1,"count");
                cookieValue = cookieValue.replaceAll(t2,"productId");
                cookieValue = cookieValue.replaceAll(t3,"goodITemList");
                //转换之前进行替换，再解码
                cookieValue= URLDecoder.decode(cookieValue,"UTF-8");
                //将cookieValue字符串还原BuyCar对象 json字符串
                //需要将json类型字符串转化成Buycar对象
                buycar = JSON.parseObject(cookieValue, BuyCar.class);
                //购物车存在相同商品
                //购物车没有相同商品
                List<GoodITem> goodITemList = buycar.getGoodITemList();
                for (int j=0; j < goodITemList.size(); j++) {
                    Integer pd = goodITemList.get(j).getProductId();
                    //存在相同商品
                    if (pd == productId) {
                        goodITemList.get(j).setCount(goodITemList.get(j).getCount() + count);
                    } else {
                        //没有相同商品
                        GoodITem goodITem = new GoodITem(productId, count);
                        //添加到buycar
                        buycar.getGoodITemList().add(goodITem);
                    }
                }
             break;
            }
        }
        //如果cookie不存在
        if (flag) {
            GoodITem goodITem = new GoodITem(productId, count);
            buycar = new BuyCar();
            buycar.setGoodITemList(Arrays.asList(goodITem));
        }
        String buycarJson = JSON.toJSONString(buycar);
        //对数据进行编码
        buycarJson = URLEncoder.encode(buycarJson, "UTF-8");

        buycarJson = buycarJson.replaceAll("count",t1);
        buycarJson = buycarJson.replaceAll("productId",t2);
        buycarJson = buycarJson.replaceAll("goodITemList",t3);
        if (username == null) {
            Cookie cookie = new Cookie("hmwBuyCar", buycarJson);
            cookie.setMaxAge(24 * 3600 * 7 + 28800);
            response.addCookie(cookie);
        } else {//登录
            //将数据添加到redis中key-value set
            String uname = (String) username;
            SetOperations sOp = redisTemplate.opsForSet();
            sOp.add("hmwBuyCar_" + uname, buycar);
            //清空cookie
            Cookie cookie = new Cookie("hmwBuyCar", "");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
        }

    }
}
