package com.cheng.service.Impl;

import com.cheng.beans.UserInfo;
import com.cheng.mapper.UserMapper;
import com.cheng.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/11/20/020
 */
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    @Override
    public int SelectDelflagByName(String username) {
        return mapper.SelectDelflagByName(username);
    }

    @Override
    public UserInfo SelectByName(String username) {
        return mapper.SelectByName(username);
    }

    @Override
    public UserInfo SelectByUserId(String userid) {
        return mapper.SelectByUserId(userid);
    }

    @Override
    public UserInfo SelectUserInfoByUserid(String userid) {
        return mapper.SelectUserInfoByUserid(userid);
    }

    @Override
    public String SelectPasswordById(String userid) {
        return mapper.SelectPasswordById(userid);
    }

    @Override
    public int updatePassword(String newpassword, String userid) {
        return mapper.updatePassword(newpassword,userid);
    }

    @Override
    public int updatePrifilePhoto(String photoname,String userid) {
        return mapper.updatePrifilePhoto(photoname,userid);
    }

    @Override
    public List<UserInfo> SelectAllUser(String code) {
        return mapper.SelectAllUser(code);
    }

    @Override
    public int SelectCount(String dwcode) {
        return mapper.SelectCount(dwcode);
    }

    @Override
    public int UpdateUserById(Integer id) {
        return mapper.UpdateUserById(id);
    }

    @Override
    public int InsertUser(UserInfo user) {
        return mapper.InsertUser(user);
    }

    @Override
    public int CheckUserByUsername(String username) {
        return mapper.CheckUserByUsername(username);
    }

    @Override
    public int updateUser(UserInfo user) {
        return mapper.updateUser(user);
    }

    @Override
    public int SelectUserIDByName(String username) {
        return mapper.SelectUserIDByName(username);
    }
}
