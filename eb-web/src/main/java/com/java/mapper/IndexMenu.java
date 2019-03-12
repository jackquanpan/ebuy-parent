package com.java.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import com.pojo.WebUser;
import org.apache.ibatis.annotations.Update;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

/**
 * date:2019-01-18
 * 14:57
 * description:IndexMenu
 * author:潘全科
 */
public interface IndexMenu {
    /**
     * 获取横向菜单
     * @param menuType
     * @return
     */
    @Select("select * from web_menu  where menuType=#{menuType} order by updateTime ASC LIMIT 8")
    List<Map<String,Object>>selectWebMenu(String menuType);

    /**
     * 登录
     * @param username
     * @param pwd
     * @return
     */
    @Select("select count(*) from web_users where username=#{username} and pwd=#{pwd}")
    int login(String username,String pwd);



    /**
     * 注册查看用户名是否存在
     * @param username
     * @return
     */
    @Select("select count(*) from web_users where username=#{username}")
    int isUserExits(String username);

    /**
     * 添加短信验证码和手机号码
     * @param smsCode
     * @param phone
     * @return
     */
    @Insert("INSERT INTO web_sms VALUES(NULL,#{smsCode},NOW(),#{phone})")
    int addSMS(String smsCode,String phone);
    /**
     * 判断验证码是否有效
     * @param smsCode
     * @param cTime
     * @return
     */
    @Select("SELECT COUNT(*) FROM web_sms WHERE smsCode=#{smsCode} AND create_time>=#{cTime}")
    int isSMSCodeExists(String smsCode,String cTime);

    /**
     * 判断手机号或者邮箱是否存在
     * @param user
     * @return
     */
    @Select("SELECT COUNT(*) FROM web_users WHERE phone=#{phone} OR email=#{email}")
    int isPhoneOrEmailExists(WebUser user);

    /**
     * 注册
     * @param user
     * @return
     */
    @Insert("INSERT INTO web_users VALUES(NULL,#{username},#{pwd},#{email},#{phone})")
    int insertWebUser(WebUser user);

    /**
     * 查询所有商品信息
     * @return
     */
    @Select("select wpd.*,ws.sortName from web_product_detail wpd\n" +
            "inner join web_sort ws on wpd.typeId=ws.id")
    List<Map<String,Object>>getALLProductDetail();

    /**
     * 根据商品id获得图片url集合
     * @param pid
     * @return
     */
    @Select("select wpi.imgUrl,wpd.* from web_product_detail wpd inner join web_product_img wpi\n" +
            "on wpd.id=wpi.productId where wpd.id=#{arg0}")
    List<String> getProductImgURL(Integer pid);

    /**
     * 每5分钟获取修改的信息
     * @return
     */
    @Select("select * from web_product_detail wpd \n" +
            "inner join web_sort ws on wpd.typeId=ws.id where wpd.updateTime>=NOW()-INTERVAL 5 MINUTE")
    List<Map<String,Object>>getUpdateDetail();

    /**
     * 查询即将开始秒杀的商品
     * @return
     */
    @Select("select * from web_seckill where startTime<=NOW() and NOW()<=endTime and status=0")
    List<Map<String,Object>>getSecKillunStart();

    /**
     *修改秒杀状态
     * @param id
     * @return
     */
    @Update("update web_seckill set status=#{status} where id=#{id}")
    int updateSecKillStatus(Integer status,Integer id);

    /**
     *获取秒杀结束的产品集合
     * @return
     *
     */
    @Select("select * from web_seckill where endTime<NOW()")
    List<Map<String,Object>>getFinishProduct();

    /**
     * 获取秒杀商品详情
     * @return
     */
    @Select("select wpd.*,ws.id as seckillId,ws.seckillPrice,ws.`status`,ws.num,ws.endTime,ws.href " +
            "as seckillhref from web_seckill ws INNER JOIN web_product_detail wpd on " +
            "ws.productId=wpd.id where ws.`status`!=2")
    List<Map<String,Object>>getSeckillProductDetail();
}
