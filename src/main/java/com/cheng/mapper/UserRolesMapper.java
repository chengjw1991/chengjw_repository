package com.cheng.mapper;

import com.cheng.beans.UserRoles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author ChengJW
 * 2020/12/12/012
 */
@Mapper
public interface UserRolesMapper {
    int InsertUserRoles(UserRoles ur);
    int SelectIdByUserName(@Param("username") String username);
    int UpdateUserRoles(UserRoles ur);
}
