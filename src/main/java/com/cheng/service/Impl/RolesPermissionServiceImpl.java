package com.cheng.service.Impl;

import com.cheng.beans.RolesPermission;
import com.cheng.mapper.RolesPermissionMapper;
import com.cheng.service.RolesPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-08 10:49
 */
@Service("RolesPermissionServiceImpl")
public class RolesPermissionServiceImpl implements RolesPermissionService {

    @Autowired
    private RolesPermissionMapper mapper;
    @Override
    public int InsertRolePerms(List<RolesPermission> list) {
        return mapper.InsertRolePerms(list);
    }

    @Override
    public int DeleteRolePerms(Integer rid) {
        return mapper.DeleteRolePerms(rid);
    }

    @Override
    public List<Integer> SelectPermList(Integer roleid) {
        return mapper.SelectPermList(roleid);
    }
}
