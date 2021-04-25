package com.cheng.service;

import com.cheng.beans.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/11/20/020
 */
public interface UserService {
    int SelectDelflagByName(String username);
    UserInfo SelectByName( String username);
    UserInfo SelectByUserId(String userid);
    UserInfo SelectUserInfoByUserid( String userid);
    String SelectPasswordById(String userid);
    int updatePassword(String newpassword,String userid);
    int updatePrifilePhoto(String photoname,String userid);
    List<UserInfo> SelectAllUser(String code);
    int SelectCount(String dwcode);
    int UpdateUserById(Integer id);
    int InsertUser(UserInfo user);
    int CheckUserByUsername(String username);
    int updateUser(UserInfo user);
    int SelectUserIDByName(String username);
}
