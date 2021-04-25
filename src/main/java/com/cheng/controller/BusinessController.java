package com.cheng.controller;

import com.cheng.beans.Companyinfo;
import com.cheng.beans.Lzxx;
import com.cheng.beans.Slxx;
import com.cheng.beans.UserInfo;
import com.cheng.service.*;
import com.cheng.token.JwtUtil;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author ChengJW
 * 2020/11/28/028
 */
@Controller("BusinessController")
@RequestMapping("/business")
@Api(value = "业务流转过程相关接口")
public class BusinessController {

    @Resource(name = "CompanyinfoServiceImpl")
    private CompanyinfoService service;
    @Resource(name = "UserServiceImpl")
    private UserService userService;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;
    @Resource(name = "SlxxServiceImpl")
    private SlxxService slxxService;
    @Resource(name = "LzxxServiceImpl")
    private LzxxService lzxxService;


    @ApiOperation(value = "查找企业信息",tags = "通过受理号查找企业信息")
    @PostMapping("/archivesSearchBySlh")
    @ResponseBody
    public DataResult archivesSearchBySlh(String qyzlx, String slh){
        //System.out.println(slh+"----"+qyzlx);
        String dwcode = getUserCode();
        if (dwcode == null){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
        }
        Companyinfo info = service.SelectInfoBySlh(qyzlx,slh,dwcode);
        if (info != null){
            //向受理信息表中插入数据
            String slh_ = info.getSlh();
            String slr = info.getSlr();
            String hzsj = info.getHzsj();
            String zxsj = info.getHzsj();
            String ywlx = info.getYwlx();
            String cywlx = info.getCywlx();
            String ewm = UUID.randomUUID().toString();
            String slrq = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Slxx slxx = new Slxx(slh,slr,hzsj,zxsj,ywlx,cywlx,1,ewm,slrq);
            //先查询是否已经受理
            Slxx slxx_ = slxxService.SelectSlxx(slh);
            if (slxx_ != null){
                return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_DuplicateData,info);
            }
            int count = slxxService.InsertSlxx(slxx);
            //插入信息到流转信息表中
            Lzxx lzxx = new Lzxx(0,slh_,info.getQymc(),getUserName(),info.getCode(),slrq,"已受理");
            int lzxxcount = lzxxService.InsertLzxx(lzxx);
            if (count >0){
                return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,info);
            }else {
                return DataResult.getDataResult(CodeAndMsgImpl.FAILURE);
            }
        }else {
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NODATA);
        }
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
    public String getUserName(){
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
