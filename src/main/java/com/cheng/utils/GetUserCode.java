package com.cheng.utils;

import com.cheng.beans.UserInfo;
import com.cheng.service.RolesService;
import com.cheng.service.UserService;
import com.cheng.token.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/18/018
 * 自定义获取当前用户code工具类
 */
public class GetUserCode {

    public static String getCode(UserService userService, RolesService rolesService){
        //获取当前用户的单位
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        //获取用户id，通过id获取用户code
        String userid = JwtUtil.ParseUserId(token);
        UserInfo user = userService.SelectByUserId(userid);
        //获取用户单位code
        String userCode = null;
        //获取用户角色类型
        List<String> roleTypeList = rolesService.SelectTpyeList(user.getId());
        if (roleTypeList.size() == 0){
            return null;
        }
        if (roleTypeList.contains("superAdministrator")){
            userCode = "%3502%";
        }else {
            userCode = user.getDwcode();
        }
        if (StringUtils.isEmpty(userCode)){
            return null;
        }
        return userCode;
    }
    public static String getUserName(UserService userService){
        //获取当前用户的单位
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        //获取用户id，通过id获取用户code
        String userid = JwtUtil.ParseUserId(token);
        UserInfo user = userService.SelectByUserId(userid);
        return user.getUsername();
    }
}
