package com.cheng.service.Impl;

import com.cheng.beans.UserRoles;
import com.cheng.mapper.UserRolesMapper;
import com.cheng.service.UserRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ChengJW
 * 2020/12/12/012
 */
@Service("UserRolesServiceImpl")
public class UserRolesServiceImpl implements UserRolesService {

    @Autowired
    private UserRolesMapper mapper;
    @Override
    public int InsertUserRoles(UserRoles ur) {
        return mapper.InsertUserRoles(ur);
    }

    @Override
    public int SelectIdByUserName(String username) {
        return mapper.SelectIdByUserName(username);
    }

    @Override
    public int UpdateUserRoles(UserRoles ur) {
        return mapper.UpdateUserRoles(ur);
    }
}
