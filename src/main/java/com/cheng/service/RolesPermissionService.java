package com.cheng.service;

import com.cheng.beans.RolesPermission;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-08 10:48
 */
public interface RolesPermissionService {
    int InsertRolePerms(List<RolesPermission> list);
    int DeleteRolePerms(Integer rid);
    List<Integer> SelectPermList(Integer roleid);
}
