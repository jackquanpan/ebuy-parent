package com.java.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.pojo.Banner;

import java.util.List;
import java.util.Map;

public interface WebMenuMapper {
    /**
     * 前台横向页面菜单展示
     * @param startIndex
     * @param pageSize
     * @retern list
     */
    @Select("SELECT id,title,url,DATE_FORMAT(updateTime,'%Y-%m-%d %H:%i:%S') AS updateTime FROM " +
            "web_menu WHERE menuType='1' LIMIT #{startIndex},#{pageSize}")
    List<Map<String,Object>> selectWebMenu(Integer startIndex, Integer pageSize);
    /**
     * 获取web_menu表中的所有数据
     * @return
     */
    @Select("SELECT COUNT(*) FROM web_menu")
    int countWebMenu();

    /**
     *
     * @param resultmap
     * @return
     */
    @Insert("insert  into  web_menu  VALUES(null,#{title},#{url},#{menuType},NOW())")
    int insert(Map<String,Object>resultmap);

    /**
     * 更新前端横向
     *
     * @param map
     * @return
     */
    @Update("UPDATE web_menu SET title=#{title},url=#{url},menuType=#{menuType},updateTime=NOW() where id=#{id}")
    int update(Map<String,Object>map);

    /**
     *删除前端横向
     * @param map
     * @return
     */
    @Delete("DELETE FROM web_menu WHERE id IN(${idstr})")
    int deleteMenu(Map<String,Object>map);



    //banner----------------------------------------------------------
    /**
     * 分页查询轮播图
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Select("SELECT id,img_url,href,remark,sort,updateTime FROM web_banners LIMIT #{startIndex},#{pageSize}")
    List<Banner>selectBanner(Integer startIndex, Integer pageSize);

    /**
     * 查询banner条数
     * @return
     */
    @Select("select count(*) from web_banners")
    int countBanner();

    /**
     * 添加轮播图片
     * @param parammap
     * @return
     */
    @Insert("INSERT INTO web_banners VALUES(NULL,#{img_url},#{href},#{remark},#{sort},NOW())")
    int insertBanner(Map<String,Object>parammap);
}
