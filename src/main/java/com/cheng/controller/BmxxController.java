package com.cheng.controller;

import com.cheng.beans.*;
import com.cheng.service.BmxxService;
import com.cheng.service.RolesService;
import com.cheng.service.UserService;
import com.cheng.token.JwtUtil;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/6/006
 */
@Controller("BmxxController")
@Api(value = "部门有关的接口")
@RequestMapping("/bmxx")
public class BmxxController {

    @Resource(name = "BmxxServiceImpl")
    private BmxxService service;
    @Resource(name = "UserServiceImpl")
    private UserService userService;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;

    @PostMapping("/selectallbmxx")
    @ResponseBody
    @ApiOperation(value = "获取所有的部门信息",tags = "获取所有的部门信息")
    public DataResult SelectAllBmxx(){
        //获取的部门是本单位的所有部门
        //System.out.println("--");
        if (getUserCode() == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        List<Bmxx>  list = service.SelectAllBmxx(getUserCode());
        if (list == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        //System.out.println(listVO);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }

    @ResponseBody
    @PostMapping("/selectbmxxbydwcode")
    @ApiOperation(value = "根据单位code获取部门信息",tags = "根据单位code获取部门信息")
    public DataResult SelectBmxxByDwcode(String dwcode){
        if (dwcode.equals("wscjw")){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        List<Bmxx> list = service.SelectBmxxByDwcode(dwcode);
        if (list.size()==0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }
    @ResponseBody
    @PostMapping("/selectbmxxbypage")
    @ApiOperation(value = "分页查询部门信息",tags = "分页查询部门信息")
    public DataResult SelectBmxxByPage(Integer pageindex,Integer pagecount){
        if (pageindex == null || pagecount == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        String bmcode = getUserCode();
        if (bmcode == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        List<Bmxx> list = service.SelectBmxxByPage(bmcode,pageindex,pagecount);
        if (list.size() == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        int sumcount = service.SelectCountBmxx(bmcode);
        if (sumcount == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        BmxxVO vo = new BmxxVO(sumcount,pageindex,pagecount,list);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,vo);
    }
    @ResponseBody
    @PostMapping("/kaiqidel")
    @ApiOperation(value = "开启部门",tags = "开启部门")
    public DataResult SetDelON(Integer id){
        int count = service.UpdateBmxxDelOn(id);
        if (count == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @ResponseBody
    @PostMapping("/jinyongdel")
    @ApiOperation(value = "禁用部门",tags = "禁用部门")
    public DataResult SetDelOFF(Integer id,HttpServletRequest req){
        int count = service.UpdateBmxxDelOff(id);
        if (count == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @ResponseBody
    @PostMapping("/selectbmxxbybmid")
    @ApiOperation(value = "根据bmid查询部门信息并回显",tags = "根据bmid查询部门信息并回显")
    public DataResult SelectBmxxByBmid(Integer bmid){
        Bmxx bmxx = service.SelectBmxxById(bmid);
        if (bmxx == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,bmxx);
    }
    @ResponseBody
    @PostMapping("/updatebmxxbyid")
    @ApiOperation(value = "修改部门信息",tags = "修改部门信息")
    public DataResult UpdateBmxxById(Integer bmid,String bmname,Integer del){
        System.out.println(bmid+"--"+bmname+"--"+del);
        Bmxx bmxx = new Bmxx(bmid,bmname,"",del);
        int count = service.UpdateBmxxById(bmxx);
        if (count == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        return  DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
    @ResponseBody
    @PostMapping("/insertbmxx")
    @ApiOperation(value = "新增部门",tags = "新增部门")
    public DataResult InsertBmxx(String bmcode,String bmname,Integer del){
        if (StringUtils.isEmpty(bmcode)||StringUtils.isEmpty(bmcode)||del==null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NULLDATA);
        }
        Bmxx bmxx = new Bmxx(0,bmname,bmcode,del);
        int count = service.InsertBmxx(bmxx);
        if (count == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
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
