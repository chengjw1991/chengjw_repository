package com.cheng.mapper;

import com.cheng.beans.RolesPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-08 10:36
 */
@Mapper
public interface RolesPermissionMapper {
    int InsertRolePerms(List<RolesPermission> list);
    int DeleteRolePerms(Integer rid);
    //通过 roleid查询对应的permid集合
    List<Integer> SelectPermList(Integer roleid);
}
