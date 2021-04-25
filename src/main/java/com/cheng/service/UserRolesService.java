package com.cheng.service;

import com.cheng.beans.UserRoles;

/**
 * @Author ChengJW
 * 2020/12/12/012
 */
public interface UserRolesService {
    int InsertUserRoles(UserRoles ur);
    int SelectIdByUserName(String username);
    int UpdateUserRoles(UserRoles ur);
}
