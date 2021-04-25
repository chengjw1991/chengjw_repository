package com.cheng.service;

import com.cheng.beans.Roles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/3/003
 */

public interface RolesService {
    List<Roles> SelectAllRoles(Integer pageindex,Integer pagecount,String code);
    int SelectSumRoles(String code);
    int SelectRolesByName(String rolename);
    int InsertRole(Roles role);
    int DeleteRole(Integer id);
    Roles SelectRoleById(Integer id);
    int UpateRole(Roles role);
    int SelectIDByRoleName(@Param("rolename") String rolename);
    String SelectNameById(Integer id);
    List<String> SelectTpyeList(Integer id);
    List<Roles> SelectAllRolesInfo(String code);
    List<Roles> SelectRolesByDwcode(String dwcode);
    List<Roles> SelectRolesByUserId(Integer id);
}
