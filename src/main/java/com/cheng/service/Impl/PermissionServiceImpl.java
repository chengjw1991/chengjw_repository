package com.cheng.service.Impl;

import com.cheng.beans.Permission;
import com.cheng.mapper.PermissionMapper;
import com.cheng.service.PermissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/6/006
 */
@Service("PermissionServiceImpl")
public class PermissionServiceImpl implements PermissonService {

    @Autowired
    private PermissionMapper mapper;

    @Override
    public List<Permission> SelectAllPermission() {
        return mapper.SelectAllPermission();
    }

    @Override
    public List<Permission> SelectPermsByParentName(String parentname) {
        return mapper.SelectPermsByParentName(parentname);
    }

    @Override
    public List<Permission> SelectPermsByGroup() {
        return mapper.SelectPermsByGroup();
    }

    @Override
    public List<Permission> selectPermByPermName(String permname) {
        return mapper.selectPermByPermName(permname);
    }

    @Override
    public List<Permission> SelectPermByRoleId(Integer roleid) {
        return mapper.SelectPermByRoleId(roleid);
    }
}
