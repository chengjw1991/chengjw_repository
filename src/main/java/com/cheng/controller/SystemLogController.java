package com.cheng.controller;

import com.cheng.beans.Loginlog;
import com.cheng.beans.Operationlog;
import com.cheng.beans.PageDataVO;
import com.cheng.beans.UserInfo;
import com.cheng.service.LoginLogService;
import com.cheng.service.OperationLogService;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/14/014
 */
@Api(value = "日志相关的接口")
@Controller("SystemLogController")
@RequestMapping("/systemLog")
public class SystemLogController {

    @Resource(name = "LoginLogServiceImpl")
    private LoginLogService service;
    @Resource(name = "UserServiceImpl")
    private UserService userService;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;
    @Resource(name = "OperationLogServiceImpl")
    private OperationLogService logService;

    @PostMapping("/selectloginlogduringdate")
    @ApiOperation(value = "查询登录信息",tags = "查询登录信息")
    @ResponseBody
    public DataResult SelectLoginLog(Integer pageindex,Integer pagecount,String username,String startdate,String enddate){
        //System.out.println(username+"--"+startdate+"--"+enddate+"----**----"+pageindex+"--"+pagecount);
        String startdate_ = null;
        String enddate_ = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        //起始日期跟截止日期都为空
        if (startdate == "" && enddate == ""){
            startdate_ = date + " 00:00:00";
            enddate_ = date + " 24:59:59";
        }else if (startdate == "" && enddate != ""){
            //起始日期为空，截止日期不为空
            //获取前7天的日期
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE,-14);
            Date d = c.getTime();
            startdate_ = format.format(d)+" 00:00:00";
             //startdate_ = "2020-01-01 00:00:00";
             enddate_ = enddate + " 24:59:59";
        }else if (startdate != "" && enddate == ""){
            startdate_ = startdate + " 00:00:00";
            enddate_ = date + " 24:59:59";
        }else if (startdate != "" && enddate != ""){
            startdate_ = startdate + " 00:00:00";
            enddate_ = enddate + " 24:59:59";
        }
        String dwcode = getUserCode();
        String username_ = "%"+username+"%";
        if (dwcode == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        List<Loginlog> list = service.SelectLoginLog(startdate_,enddate_,dwcode,username_,pageindex,pagecount);
        if (list.size() == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        int sum = service.SelectLoginLogByCode(startdate_,enddate_,dwcode,username_);
        if (sum == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        //分页查询数据回显
        PageDataVO vo = new PageDataVO(sum,pageindex,pagecount,list);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,vo);
    }

    @PostMapping("/selectoperationlogduringdate")
    @ApiOperation(value = "查询操作日志信息",tags = "查询操作日志信息")
    @ResponseBody
    public DataResult SelectOperationLog(Integer pageindex,Integer pagecount,String username,String startdate,String enddate){
        //System.out.println(username+"--"+startdate+"--"+enddate+"----**----"+pageindex+"--"+pagecount);
        String startdate_ = null;
        String enddate_ = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        //起始日期跟截止日期都为空
        if (startdate == "" && enddate == ""){
            startdate_ = date + " 00:00:00";
            enddate_ = date + " 24:59:59";
        }else if (startdate == "" && enddate != ""){
            //起始日期为空，截止日期不为空
            //获取前7天的日期
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE,-14);
            Date d = c.getTime();
            startdate_ = format.format(d)+" 00:00:00";
            //startdate_ = "2020-01-01 00:00:00";
            enddate_ = enddate + " 24:59:59";
        }else if (startdate != "" && enddate == ""){
            startdate_ = startdate + " 00:00:00";
            enddate_ = date + " 24:59:59";
        }else if (startdate != "" && enddate != ""){
            startdate_ = startdate + " 00:00:00";
            enddate_ = enddate + " 24:59:59";
        }
        String dwcode = getUserCode();
        String username_ = "%"+username+"%";
        if (dwcode == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        List<Operationlog> list = logService.SelectLog(startdate_,enddate_,dwcode,username_,pageindex,pagecount);
        if (list.size() == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        int sum = logService.SelectCountLog(startdate_,enddate_,dwcode,username_);
        if (sum == 0){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
        //分页查询数据回显
        PageDataVO vo = new PageDataVO(sum,pageindex,pagecount,list);
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
