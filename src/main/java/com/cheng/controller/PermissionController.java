package com.cheng.controller;

import com.cheng.beans.Permission;

import com.cheng.beans.Roles;
import com.cheng.beans.permissionVO;
import com.cheng.service.PermissonService;
import com.cheng.service.RolesService;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author ChengJW
 * 2020/12/6/006
 */
@Controller("PermissionController")
@Api(value = "权限相关的接口")
@RequestMapping("/permissionAdmin")
public class PermissionController {

    @Resource(name = "PermissionServiceImpl")
    private PermissonService service;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;

    /**
     * 由于前端采用的layui tree 的格式，故后端返回的数据格式有一定的要求，按照此要求，返回的每一个节点的数据包括
     * title、id、children等，故返回的实体类对象为permissionVO，每一个节点的每一个子节点的所有格式类型都如此。
     * 1、通过数据库查询出1级菜单权限
     * 2、遍历1级菜单权限，获取每个1级菜单权限下的2级菜单权限，如果有3级菜单，则在遍历2级菜单，获取每个2级菜单下的3级菜单。以此类推。
     * 3、将每个2级菜单权限permission类，封装成前端需要的格式，即permissionVO，包含id，title
     * 4、将每个1级菜单权限封装成permissionVO,即包含id，title和子菜单所有节点 children
     * 5、将所有的1级菜单放入list几个钟，封装到dataresult类中返回给前端。
     * @return
     */
    @ApiOperation(value = "回显所有的权限")
    @PostMapping("/selectallperms")
    @ResponseBody
    public DataResult SelectAllPerms(){
        //获取一级权限
        List<Permission> firstPerms = service.SelectPermsByGroup();
        if (firstPerms == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //创建一级权限的集合
        List<permissionVO> listVO = new ArrayList<>();
        //遍历1级权限获取二级权限
        for(Permission perm : firstPerms){
            //获取二级权限集合
            List<Permission> secondPerms = service.SelectPermsByParentName(perm.getParentname());
            if (secondPerms == null){
                return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
            }
            //创建二级权限的list集合,作为1级权限的children数据
            List<permissionVO> secondlistVO = new ArrayList<>();
            //遍历2级权限，获取3级权限
            for (Permission secondeperm : secondPerms){
                //创建3级权限的集合，作为2级权限的children数据源
                List<permissionVO> thirdlistVO = new ArrayList<>();
                //获取3级权限集合
                List<Permission> thirdvo = service.selectPermByPermName(secondeperm.getPermname());
                //遍历三级权限，封装成tree格式的数据源
                for(Permission thirdperm : thirdvo){
                    //封装tree数据源，并添加到数组中
                    permissionVO thethirdperm = new permissionVO(thirdperm.getPermid(),thirdperm.getName());
                    //将数据源添加到数组中，作为二级权限的children数据
                    thirdlistVO.add(thethirdperm);
                }
                //2级菜单封装成为layui tree需要的格式类
                permissionVO secondvo = new permissionVO(secondeperm.getPermid(),secondeperm.getName(),thirdlistVO);
                //将所有的2级菜单放入list集合中。
                secondlistVO.add(secondvo);
            }
            //1级菜单封装成为layui tree需要的格式类
            permissionVO vo = new permissionVO(perm.getPermid(),perm.getParentname(),secondlistVO);
            //获取所有1级权限数据源，封装到集合里
            listVO.add(vo);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,listVO);
    }
    @ApiOperation(value = "根据roleID查询所有的权限permid并回显",tags = "根据roleID查询所有的权限permid并回显")
    @PostMapping("/selectpermsbyroleid")
    @ResponseBody
    public DataResult SelectPermByID(Integer roleid){
        List<Permission> list = service.SelectPermByRoleId(roleid);
        if (list.size() == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        //System.out.println(list);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }
    @ApiOperation(value = "根据userid查询所有的permid并回显",tags = "根据userid查询所有的permid并回显")
    @ResponseBody
    @PostMapping("/selectpermidbyuserid")
    public DataResult SelectPermIdByUserId(Integer id){
        Roles role = rolesService.SelectRolesByUserId(id).get(0);
        if (role == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        List<Permission> list = service.SelectPermByRoleId(role.getId());
        if (list.size()==0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }
}
