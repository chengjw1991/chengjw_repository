package com.cheng.controller;


import com.cheng.beans.*;
import com.cheng.mapper.UserMapper;
import com.cheng.service.*;
import com.cheng.token.JwtUtil;
import com.cheng.utils.EncryptPassword;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author chengjw
 * @date 2020-12-10 08:46
 */
@Controller("UserAdminController")
@RequestMapping("/userAdmin")
public class UserAdminController {

    @Resource(name = "UserServiceImpl")
    private UserService service;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;
    @Resource(name = "UserallinfoServiceImpl")
    private UserallinfoService userallinfoService;
    @Resource(name = "UserRolesServiceImpl")
    private UserRolesService userRolesService;
    @Resource(name = "RolesPermissionServiceImpl")
    private RolesPermissionService rpservice;

    @ApiOperation(value = "查询所有的用户信息",tags = "查询所有的用户信息")
    @PostMapping("/selectalluserinfo")
    @ResponseBody
    public DataResult SelectAllUser(Integer pageindex,Integer pagecount,String bmname,String realname){
        //System.out.println("------");
        //获取当前用户的单位,用户只允许查看本单位的用户信息
        if (getUserCode() == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        String code = getUserCode();
        //通过视图查询用户信息
        //System.out.println("--"+code+"--"+pagecount+"--"+pageindex+"--"+bmname+"--"+realname+"--");
        String real_name = "%"+realname+"%";
        String bm_name = "%"+bmname+"%";

        List<Userallinfo> list = userallinfoService.SelectAllInfo(code,bm_name,real_name,pageindex,pagecount);
        if (list == null || list.size()==0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        //获取查询到的用户总数
        int count = userallinfoService.SelectCountInfo(code,bm_name,real_name);
        UserVO vo = new UserVO(count,pageindex,pagecount,list);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,vo);
    }
    @PostMapping("/deletebyid")
    @ResponseBody
    @ApiOperation(value = "通过id禁用用户",tags = "通过id禁用用户")
    public DataResult DeleteById(Integer id){
        System.out.println(id+"----");
        if (id == null || id<0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        int count = service.UpdateUserById(id);
        if (count == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @PostMapping("/checkuser")
    @ResponseBody
    @ApiOperation(value = "验证用户名是否存在",tags = "验证用户名是否存在")
    public DataResult CheckUser(String username){
        if (username == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        int count = service.CheckUserByUsername(username);
        if (count >0){
            //用户已经存在
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_DuplicateData);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @ApiOperation(value = "新增用户",tags = "新增用户")
    @ResponseBody
    @PostMapping("/adduser")
    public DataResult InsertUser(String username, String password, String realname, String sex, String dwcode, Integer bmid, Integer roleid, String phone, String photo, Integer delflag){
       if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(realname) || dwcode.equals("wscjw") || bmid ==0 || roleid == 0){
           return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NULLDATA);
       }
        int count = service.CheckUserByUsername(username);
        if (count >0){
            //用户已经存在
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_DuplicateData);
        }
       //加密密码
       String password_encrypt = EncryptPassword.Encrypt(password);
       //创建用户日期
       String registdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
       //创建userid
       String userid = UUID.randomUUID().toString();
       String prifilephoto = null;
       if(photo == null || photo ==""){
           prifilephoto = "defaultphoto.jpg";
       }
       prifilephoto = photo;

       //新增用户
       UserInfo userInfo = new UserInfo(0,userid,username,password_encrypt,realname,bmid,sex,phone,registdate,0,delflag,prifilephoto,dwcode);
       int usercount = service.InsertUser(userInfo);
       //插入userroles中间表
       int userrolecount = userRolesService.InsertUserRoles(new UserRoles(0,userInfo.getId(),roleid));
       if (usercount == 0 || userrolecount == 0){
           return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
       }
       return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @PostMapping("/selectuserinfobyid")
    @ApiOperation(value = "根据id回显用户信息",tags = "根据id回显用户信息")
    @ResponseBody
    public DataResult SelectUserInfoById(Integer id){
        //System.out.println(id+"------");
        //1. 通过id 查user信息
        Userallinfo info = userallinfoService.SelectUserInfoById(id);
        if (info == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NULLDATA);
        }
        //2. 通过id 查到roles信息
        Roles role = rolesService.SelectRolesByUserId(id).get(0);
        Integer roleid = role.getId();
        //3. 通过roleid 查到 对应的权限permid
        List<Integer> perms = rpservice.SelectPermList(roleid);
        userinfoadmVO vo = new userinfoadmVO(info,roleid,perms);
        //System.out.println(vo+"---");
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,vo);
    }
    @PostMapping("/updateuser")
    @ResponseBody
    @ApiOperation(value = "修改用户信息",tags = "修改用户信息")
    public DataResult updateUser(String username, String password, String realname, String sex, String dwcode, Integer bmid, Integer roleid, String phone, String photo, Integer delflag){
        System.out.println(delflag+"---");
        if (StringUtils.isEmpty(realname) || dwcode.equals("wscjw") || bmid == 0 || roleid == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NULLDATA);
        }
        String encryptPasswrod = null;
        if(!StringUtils.isEmpty(password)){
            encryptPasswrod = EncryptPassword.Encrypt(password);
        }
        String userid = UUID.randomUUID().toString();
        UserInfo info = new UserInfo(0,userid,username,encryptPasswrod,realname,bmid,sex,phone,"",0,delflag,photo,dwcode);
        int count = service.updateUser(info);
        if (count == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        Integer uid = service.SelectUserIDByName(username);
        //修改userrols中间表中该用户的角色
        int urid = userRolesService.SelectIdByUserName(username);

        UserRoles ur = new UserRoles(urid,uid,roleid);
        int urcount = userRolesService.UpdateUserRoles(ur);
        if (urcount == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @PostMapping("/selectuserroleperm")
    @ApiOperation(value = "查看用户权限",tags = "查看用户权限")
    @ResponseBody
    public DataResult SelectUserRolePerms(Integer pageindex,Integer pagecount){
        String dwcode = getUserCode();
        if (dwcode == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        // userallinfo 集合
        List<Userallinfo> list = userallinfoService.SelectAllInfo(dwcode,"%%","%%",pageindex,pagecount);
        if (list.size() == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        //总数据量
        int count = userallinfoService.SelectCountInfo(dwcode,"%%","%%");
        UserVO vo = new UserVO(count,pageindex,pagecount,list);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,vo);
    }

    public String getUserCode(){
        //获取当前用户的单位
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        //获取用户id，通过id获取用户code
        String userid = JwtUtil.ParseUserId(token);
        UserInfo user = service.SelectByUserId(userid);
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
