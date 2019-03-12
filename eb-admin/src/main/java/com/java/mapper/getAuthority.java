package com.java.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import com.pojo.OneMenu;

import java.util.List;
import java.util.Map;

/**
 * date:2019-02-15
 * 11:00
 * description:getAuthority
 * author:潘全科
 */
public interface getAuthority {
    /**
     * 获取用户
     * @param startIndex
     * @param pagesize
     * @return
     */
    @Select("select * from admin_users")
    List<Map<String,Object>>getSysUser();

    /**
     * 获取除过admin用户个数
     * @return
     */
    @Select("select count(*) from admin_users where isroot=1")
    int usercount();

    /**
     * 获取权限树在表单中展现
     * @param parentId
     * @return
     */
    @Select("select * from admin_menus where parentId=#{parentId} and flag!=1")
    List<Map<String,Object>> getAuthority(Long parentId);

    /**
     * 查询用户名是否已经存在
     * @param username
     * @return
     */
   @Select("select count(*) from admin_users where username=#{username}")
    int finduser(String username);
    /**
     * 添加系统用户信息需要返回生成的id
     * @return
     */
    @Options(keyProperty = "userId",useGeneratedKeys = true,keyColumn ="id")
    @Insert("INSERT INTO admin_users VALUES(NULL,#{username},#{pwd},'0',NOW())")
    int insertUser(Map<String,Object> map);

    /**
     * 添加用户权限信息
     * @param id
     * @param menuid
     * @return
     */
    @Insert("insert into admin_user_authority values(#{id},#{menuid})")
    int insertAuthority(Long id ,Long menuid);

    /**
     * 获取权限对应关系
     * @return
     */
    List<OneMenu> getAuthorityByRelation();

    /**
     * 修改权限时删除权限
     * @param userId
     * @return
     */
    @Delete("delete  from admin_user_authority where userId=#{userId}")
    int deleteauthority(Long userId);

    /**
     * 回显权限
     * @param userId
     * @return
     */
    @Select("select menuId from admin_user_authority where userId=#{userId}")
   List<Long>getUserAuthority(Long userId);
}
