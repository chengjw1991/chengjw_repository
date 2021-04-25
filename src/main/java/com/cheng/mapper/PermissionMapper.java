package com.cheng.mapper;

import com.cheng.beans.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/6/006
 */
@Mapper
public interface PermissionMapper {
    //查询所有的权限
    List<Permission> SelectAllPermission();
    //查询指定parentname的权限
    List<Permission> SelectPermsByParentName(@Param("parentname") String parentname);
    //分组查询权限
    List<Permission> SelectPermsByGroup();
    //查询二级菜单跳转页面上的增删改查权限
    List<Permission> selectPermByPermName(@Param("permname") String permname);
    //根据roleid 查询其拥有的权限信息
    List<Permission> SelectPermByRoleId(Integer roleid);
}
