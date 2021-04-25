package com.cheng.mapper;

import com.cheng.beans.Roles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/3/003
 */
@Mapper
public interface RolesMapper {

    List<Roles> SelectAllRoles(Integer pageindex,Integer pagecount,@Param("code") String code);
    int SelectSumRoles(@Param("code") String code);
    int SelectRolesByName(@Param("rolename") String rolename);
    int InsertRole(Roles role);
    int DeleteRole(Integer id);
    Roles SelectRoleById(Integer id);
    int UpdateRoleById(Roles role);
    int SelectIDByRoleName(@Param("rolename") String rolename);
    String SelectNameById(Integer id);
    //通过userid 查询 userid 对应的角色类型
    List<String> SelectTpyeList(Integer id);
    List<Roles> SelectAllRolesInfo(@Param("code") String code);
    List<Roles> SelectRolesByDwcode(@Param("dwcode")String dwcode);
    //通过user id查到role信息
    List<Roles> SelectRolesByUserId(Integer id);
}
