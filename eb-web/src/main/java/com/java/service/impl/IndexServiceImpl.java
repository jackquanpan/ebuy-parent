package com.java.service.impl;

import com.java.mapper.IndexMenu;
import com.java.utils.MD5Too;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pojo.WebUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * date:2019-01-18
 * 15:03
 * description:IndexServiceImpl
 * author:潘全科
 */
//redis创建名称和配置
@CacheConfig(cacheNames = {"indexServiceImpl_"})
@Service
public class IndexServiceImpl implements com.java.service.IndexService {
    @Autowired
    private IndexMenu indexMenu;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * redis获得横向导航栏
     *
     * @param menuType
     * @return
     */
    @Override
    public List<Map<String, Object>> findhxMenu(String menuType) {
        try {
            ValueOperations ops = redisTemplate.opsForValue();
            Object hxWebmenu = ops.get("hxWebmenu");
            if (hxWebmenu == null) {
                //从mysql中读取在存在redis
                List<Map<String, Object>> menuList = indexMenu.selectWebMenu(menuType);
                //存入redis
                ops.set("hxWebmenu", menuList);
                //设置失效时间
                redisTemplate.expire("hxWebmenu", 5, TimeUnit.MINUTES);
                return menuList;
            }
            return (List<Map<String, Object>>) hxWebmenu;
        } catch (Exception e) {
            System.err.println("IndexServiceImpl......findWebMenus....Redis忘记开启了");
            return indexMenu.selectWebMenu(menuType);
        }
    }

    /**
     * 基于注解版的redis
     *
     * @return
     */
    @Cacheable("'findzhmenu'")
    @Override
    public List<Map<String, Object>> findZXMenu() {
        return indexMenu.selectWebMenu("1");
    }

    /**
     * 基于注解版redis
     */
    @CacheEvict("'findzhmenu'")
    @Override
    public void clearZxMenu() {
        System.out.println("清空redis");
    }

    /**
     * 查看用户是否存在
     *
     * @param username
     * @return
     */
    @Override
    public boolean esxitUser(String username) {
        int i = indexMenu.isUserExits(username);
        boolean flag = i == 0 ? false : true;
        return flag;
    }

    /**
     * 短信验证码添加
     *
     * @param smsCode
     * @param phone
     * @return
     */
    @Override
    public boolean addSMS(String smsCode, String phone) {
        return indexMenu.addSMS(smsCode, phone) == 1;
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public boolean insertUser(WebUser user, Map<String, Object> errorMap) {
        try {
            int flag1 = indexMenu.isPhoneOrEmailExists(user);
            if (flag1 >= 1) {
                errorMap.put("uniqError", "邮箱或者手机号已经被注册");
                return false;
            }
            //判断验证码是否正确，同时判断验证码时间是否有效
            long c = System.currentTimeMillis() - 60000;
            Date date = new Date(c);
            int flag2 = indexMenu.isSMSCodeExists(user.getSmsCode(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
            if (flag2 == 0) {
                errorMap.put("smsCodeStatus", "验证码不存在或者过期了");
                return false;
            }
            user.setPwd(MD5Too.Md5(user.getPwd()));
            return indexMenu.insertWebUser(user) >= 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Map<String, Object>> getALLProductDetail() {
        //1.获取商品详情不包括图片
        List<Map<String, Object>> detail = indexMenu.getALLProductDetail();
        //2.添加图片链接
        for (int i = 0; i < detail.size(); i++) {
            Integer id = (Integer) detail.get(i).get("id");
            List<String> imgUrlList = indexMenu.getProductImgURL(id);
            detail.get(i).put("imgUrlList", imgUrlList);
        }
        return detail;
    }

    @Override
    public List<Map<String, Object>> findUpdateDetail() {
        //1.获取商品详情不包括图片
        List<Map<String, Object>> detail = indexMenu.getUpdateDetail();
        //2.添加图片链接
        for (int i = 0; i < detail.size(); i++) {
            Integer id = (Integer) detail.get(i).get("id");
            List<String> imgUrlList = indexMenu.getProductImgURL(id);
            detail.get(i).put("imgUrlList", imgUrlList);
        }
        return detail;
    }

    @Override
    public void processSeckill() {
        //查询出秒杀表中即将开始的秒杀
        List<Map<String, Object>> secKillunStart = indexMenu.getSecKillunStart();
        ListOperations lot = redisTemplate.opsForList();
        //将秒杀产品在redis数据库中记录下来
        for (Map<String, Object> tempmap : secKillunStart) {
            //获取秒杀表主键
            Integer id = (Integer) tempmap.get("id");
            //获取秒杀表商品id
            Integer productId = (Integer) tempmap.get("productId");
            //构建key
            String key = "seckill_product_" + id;
            //将num数量的秒杀名额对应的商品id存放到redis中
            Integer num=(Integer)tempmap.get("num");

            for(int i=0;i<num;i++){
                lot.rightPush(key,productId);
            }
            //3.修改秒杀产品的状态
            indexMenu.updateSecKillStatus(1,id);
        }


    }

    @Override
    public void updateNoStart2Finish() {
        List<Map<String, Object>> finishProduct = indexMenu.getFinishProduct();
        //删除掉redis中秒杀空的产品
        for (Map<String, Object> tempMap : finishProduct){
            //获取秒杀id
            Integer seckillId = (Integer)tempMap.get("id");
            redisTemplate.delete("seckill_product_"+seckillId);
            //2、修改产品状态
            indexMenu.updateSecKillStatus(2,seckillId);

        }
    }

    @Override
    public List<Map<String, Object>> findSeckillProductDetai() {
        //先获取所有秒杀商品
        List<Map<String, Object>> seckillProduct= indexMenu.getSeckillProductDetail();
        //获取商品图片
        for (int i = 0; i <seckillProduct.size(); i++) {
            Integer id = (Integer) seckillProduct.get(i).get("id");
            List<String> imgUrlList = indexMenu.getProductImgURL(id);
           seckillProduct.get(i).put("imgUrlList", imgUrlList);
        }
        return seckillProduct;
    }

}
