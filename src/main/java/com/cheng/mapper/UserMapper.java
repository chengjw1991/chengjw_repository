package com.cheng.mapper;

import com.cheng.beans.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/11/20/020
 */
@Mapper
public interface UserMapper {
    int SelectDelflagByName(@Param("username") String username);
    UserInfo SelectByName(@Param("username") String username);
    UserInfo SelectByUserId(@Param("userid") String userid);
    UserInfo SelectUserInfoByUserid(@Param("userid") String userid);
    String SelectPasswordById(@Param("userid")String userid);
    int updatePassword(@Param("newpassword")String newpassword,@Param("userid") String userid);
    int updatePrifilePhoto(@Param("photoname") String photoname,@Param("userid") String userid);
    List<UserInfo> SelectAllUser(@Param("dwcode") String dwcode);
    int SelectCount(@Param("dwcode") String dwcode);
    int UpdateUserById(Integer id);
    int InsertUser(UserInfo user);
    int CheckUserByUsername(@Param("username") String username);
    int updateUser(UserInfo user);
    int SelectUserIDByName(@Param("username") String username);
}
