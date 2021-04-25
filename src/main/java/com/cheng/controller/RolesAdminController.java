package com.cheng.controller;

import com.cheng.beans.*;
import com.cheng.service.DwxxService;
import com.cheng.service.RolesPermissionService;
import com.cheng.service.RolesService;
import com.cheng.service.UserService;
import com.cheng.token.JwtUtil;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.management.relation.Role;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/3/003
 */
@Api(value = "角色管理相关接口")
@Controller("RolesAdminController")
@RequestMapping("/rolesAdmin")
public class RolesAdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RolesAdminController.class);

    @Resource(name = "UserServiceImpl")
    private UserService userService;
    @Resource(name = "RolesServiceImpl")
    private RolesService service;
    @Resource(name = "DwxxServiceImpl")
    private DwxxService dwxxService;
    @Resource(name = "RolesPermissionServiceImpl")
    private RolesPermissionService rpservice;

    /**
     * 根据用户单位，查询所属单位的所有角色
     * 超级管理员除外，超级管理员能够给查看所有的角色信息
     * @param pagecount
     * @param pageindex
     * @return
     */
    @ResponseBody
    @PostMapping("/rolesSelect")
    @ApiOperation(value = "分页查询所有的角色",tags = "分页查询所有的角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pagecount",value = "每页数据行数"),
            @ApiImplicitParam(name = "pageindex",value = "当前页数")
    })
    public DataResult SelectAllRoles(Integer pagecount,Integer pageindex){
        //System.out.println(pageindex+"--"+pagecount);
        String code = getUserCode();
        if (code == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //获取用户的单位代码
        int count = service.SelectSumRoles(code);
        List<Roles> list = service.SelectAllRoles(pageindex,pagecount,code);
        if (list != null&& count >0){
            RolesVO vo = new RolesVO(count,list,pageindex,pagecount);
            //System.out.println(vo);
            return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,vo);
        }else {
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
    }
    @ResponseBody
    @PostMapping("/selectallrole")
    @ApiOperation(value = "查询所有的角色信息",tags = "查询所有的角色信息")
    public DataResult SelectAllRoleInfo(){
        String code = getUserCode();
        if (code == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        List<Roles> list = service.SelectAllRolesInfo(code);
        if (list.size() == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }

    @PostMapping("/checkrole")
    @ResponseBody
    @ApiOperation(value = "验证输入的角色是否已经存在",tags = "验证输入的角色是否已经存在")
    public DataResult CheckRole(String rolename){
        int count = service.SelectRolesByName(rolename);
        //System.out.println(count);
        if (count>0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @PostMapping("/updatecheckrole")
    @ResponseBody
    @ApiOperation(value = "修改角色的时候验证角色",tags = "修改角色的时候验证角色")
    public DataResult UpdateCheckRole(String rolename,Integer roleid){
        //System.out.println(rolename+"--"+roleid);
        String name = service.SelectNameById(roleid);
        if (StringUtils.isEmpty(name)){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        int count = service.SelectRolesByName(rolename);
        //System.out.println(rolename+"--"+roleid+"--");
        if (count>0 && !rolename.equals(name) ){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //System.out.println(rolename+"--"+roleid+"-------");
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
   // @Transactional
    @PostMapping("/addrole")
    @ResponseBody
    @ApiOperation(value = "新增角色",tags = "新增角色")
    public DataResult AddRole(String rolename, String dwxxcode, @RequestParam("perms") List<Integer> perms, String explain){
        //System.out.println(rolename+"--"+dwxxcode+"--"+perms+"--"+explain);
        if (StringUtils.isEmpty(rolename) || dwxxcode.equals("wscjw") || perms == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //验证角色名是否存在
        int rolescount = service.SelectRolesByName(rolename);
        if (rolescount>0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_DuplicateData);
        }
        //获取单位code跟dwmc
        Dwxx dwxx = dwxxService.SelectByCode(dwxxcode);
        if (dwxx == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        String code = dwxx.getCode();
        String dwmc = dwxx.getName();
        String createdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //新增橘色信息，并回显id
        Roles roles = new Roles(0,rolename,"otherTypeMan",0,explain,code,createdate,dwmc);
        int rolecount = service.InsertRole(roles);
        Integer rid = roles.getId();
        //插入角色权限中间表
        if (rid == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //判断是否拥有用户信息权限，没有的话，需要添加上
        for(int i=7000;i<=7003;i++){
            if (!perms.contains(i)){
                perms.add(i);
            }
        }
        List<RolesPermission> rp = new ArrayList<>();
        for(Integer pid : perms){
            RolesPermission rperms = new RolesPermission(0,rid,pid);
            rp.add(rperms);
        }
        int rpcount = rpservice.InsertRolePerms(rp);
        if (rpcount == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @PostMapping("/deleterole")
    @ResponseBody
    @ApiOperation(value = "禁用角色",tags = "禁用角色")
    public DataResult DeleteRole(Integer id){
        if (id ==0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        int count = service.DeleteRole(id);
        if (count == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //System.out.println("-----删除成功-----");
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @PostMapping("/selectrolebyid")
    @ResponseBody
    @ApiOperation(value = "回显角色信息",tags = "回显角色信息")
    public DataResult SelectRoleByID(Integer id){
        //System.out.println(id);
        if (id == 0){
            return  DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        Roles role = service.SelectRoleById(id);
        if (role == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //System.out.println(role);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,role);
    }
    @PostMapping("/updaterole")
    @ResponseBody
    @ApiOperation(value = "修改角色信息",tags = "修改角色信息")
    public DataResult UpdateRole(String rolename, String dwcode, @RequestParam("perms") List<Integer> perms, String explain,Integer del,Integer roleId){
        //System.out.println(rolename+"--"+dwcode+"--"+perms+"--"+explain+"--"+del+"--"+roleId);
        if (StringUtils.isEmpty(rolename) || dwcode.equals("wscjw") || perms == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //验证角色名是否存在
        String name = service.SelectNameById(roleId);
        int rolescount = service.SelectRolesByName(rolename);
        if (rolescount>0 && !rolename.equals(name)){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_DuplicateData);
        }
        //获取单位code跟dwmc
        Dwxx dwxx = dwxxService.SelectByCode(dwcode);
        if (dwxx == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        String code = dwxx.getCode();
        String dwmc = dwxx.getName();
        Roles role = new Roles(roleId,rolename,"",del,explain,code,"",dwmc);
        //System.out.println(role);
        int updateCount = service.UpateRole(role);
        //System.out.println(updateCount+"--------");
        if (updateCount == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //判断是否拥有用户信息权限，没有的话，需要添加上
        for(int i=7000;i<=7003;i++){
            if (!perms.contains(i)){
                perms.add(i);
            }
        }
        List<RolesPermission> rp = new ArrayList<>();
        for (Integer pid : perms){
            RolesPermission rolePerm = new RolesPermission(0,roleId,pid);
            rp.add(rolePerm);
        }
        // 先删除用户权限中间表的id的数据，再重新添加
        int rpdeleteCount = rpservice.DeleteRolePerms(roleId);
        if (rpdeleteCount == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //新插入数据
        int rpcount = rpservice.InsertRolePerms(rp);
        if (rpcount == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @ApiOperation(value = "根据单位code获取本单位的角色信息",tags = "根据单位code获取本单位的角色信息")
    @PostMapping("/selectrolesbydwcode")
    @ResponseBody
    public DataResult SelectRoleByDwcode(String dwcode){
        if (dwcode.equals("wscjw")){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        List<Roles> list = service.SelectRolesByDwcode(dwcode);
        if (list.size()==0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }
    //获取用的单位code
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
        List<String> roleTypeList = service.SelectTpyeList(user.getId());
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
