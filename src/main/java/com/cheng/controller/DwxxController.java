package com.cheng.controller;

import com.cheng.beans.Bmxx;
import com.cheng.beans.Dwxx;
import com.cheng.beans.UserInfo;
import com.cheng.service.DwxxService;
import com.cheng.service.RolesService;
import com.cheng.service.UserService;
import com.cheng.token.JwtUtil;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/6/006
 */
@Controller("DwxxController")
@Api(value = "单位信息相关接口")
@RequestMapping("/dwxx")
public class DwxxController {

    @Resource(name = "DwxxServiceImpl")
    private DwxxService service;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;
    @Resource(name = "UserServiceImpl")
    private UserService userService;

    @PostMapping("/selectalldwxx")
    @ResponseBody
    @ApiOperation(value = "查询所有的单位信息",tags = "查询所有的单位信息")
    public DataResult SelectAllDwxx(){
        if (getUserCode() == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        List<Dwxx> list = service.SelectAllDwxx(getUserCode());
        if (list == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }
    @ResponseBody
    @PostMapping("/selectinfo")
    @ApiOperation(value = "查询所有单位信息",tags = "查询所有单位信息")
    public DataResult SelectDwxxInfo(){
       // System.out.println("----");
        List<Dwxx> list = service.SelectInfo();
        if (list.size() == 0){
            return DataResult.FAILURE();
        }
        //System.out.println(list);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }

    public String getUserCode(){
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
}
