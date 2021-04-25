package com.cheng.controller;

import com.cheng.beans.Companyallinfo;
import com.cheng.beans.PageDataVO;
import com.cheng.service.*;

import com.cheng.utils.GetUserCode;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * @Author ChengJW
 * 2020/12/18/018
 */
@Api(value = "业务受理相关接口")
@RestController("AcceptAdminController")
@RequestMapping("/acceptAdmin")
public class AcceptAdminController {

    @Resource(name = "CompanyallinfoServiceImpl")
    private CompanyallinfoService service;
    @Resource(name = "CompanyinfoServiceImpl")
    private CompanyinfoService companyinfoService;
    @Resource(name = "UserServiceImpl")
    private UserService userService;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;
    @Resource(name = "SlxxServiceImpl")
    private SlxxService slxxService;


    @PostMapping("/SelectCompanyInfo")
    @ApiOperation(value = "查询所有的企业信息",tags = "查询所有的企业信息")
    public DataResult SelectAllcompanyinfo(String condition, String content, String startdate, String enddate,Integer pageindex,Integer pagecount){
        //获取用户code
        String code = GetUserCode.getCode(userService,rolesService);
        if (code == null){
            return DataResult.getDataResult(1,"当前用户的单位代码为空",new PageDataVO<>(0,0,0,0));
        }
        String startdate_ = null;
        String enddate_ = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
      //从系统有数据开始算起
        if (startdate == "" && enddate == ""){
            startdate_ = "2020-11-01";
            //startdate_ = "2020-01-01 00:00:00";
            enddate_ = date;
        }else if (startdate == "" && enddate != ""){
            //起始日期为空，截止日期不为空
            startdate_ ="2020-11-01";
            enddate_ = enddate;
        }else if (startdate != "" && enddate == ""){
            startdate_ = startdate;
            enddate_ = date;
        }else if (startdate != "" && enddate != ""){
            startdate_ = startdate;
            enddate_ = enddate;
        }
        //System.out.println(code+"--"+condition+"--"+content+"--"+startdate_+"--"+enddate_);
        List<Companyallinfo> list = service.SelectAllCompanyInfo(code,condition,"%"+content+"%",startdate_,enddate_,pageindex,pagecount);
        if (list.size()==0){
            return DataResult.getDataResult(1,"对不起！没有查询到您想要的数据！",new PageDataVO<>(0,0,0,0));
        }
        int count = service.SelectCountAllinfo(code,condition,"%"+content+"%",startdate_,enddate_);
        if (count==0){
            return DataResult.getDataResult(1,"对不起！没有查询到您想要的数据！",new PageDataVO<>(0,0,0,0));
        }
        PageDataVO vo = new PageDataVO(count,pageindex,pagecount,list);
        return DataResult.getDataResult(0,"操作执行成功！！！",vo);
    }
    @PostMapping("/updateslys")
    @ApiOperation(value = "修改企业信息的页数",tags = "修改企业信息的页数")
    public DataResult UpdateCompanyInfo(String slh,Integer slys){
        if (StringUtils.isEmpty(slh) || slys == null){
            return DataResult.FAILURE();
        }
        int count = companyinfoService.UpdateCompanyinfo(slh,slys);
        if (count == 0){
            return DataResult.FAILURE();
        }
        return DataResult.SUCCESS();
    }
    @PostMapping("/updateslrq")
    @ApiOperation(value = "修改企业信息的受理日期",tags = "修改企业信息的受理日期")
    public DataResult UpdateSLRQ(String slh,String slrq){
        System.out.println(slrq+"--"+slh);
        if (StringUtils.isEmpty(slh) || slrq == null){
            return DataResult.FAILURE();
        }
        System.out.println("------");
        int count = slxxService.UpdateSLRQ(slh,slrq);
        if (count == 0){
            return DataResult.FAILURE();
        }
        return DataResult.SUCCESS();
    }
    @PostMapping("/DeleteCompanyinfo")
    @ApiOperation(value = "删除企业信息",tags = "删除企业信息")
    public DataResult DeleteCompanyinfo( @RequestParam("slh") List<String> slh){
        System.out.println(slh+"---");
        if (slh == null){
            return DataResult.FAILURE_NULLDATA();
        }
        int count = companyinfoService.DeleteCompanyinfo(slh);
        if (count == 0){
            return DataResult.FAILURE_NULLDATA();
        }
        return  DataResult.SUCCESS();
    }
    @PostMapping("/SelectCompanyInfobyCondition")
    @ApiOperation(value = "根据条件查询企业信息",tags = "根据条件查询企业信息")
    public DataResult SelectAllcompanyinfo(String condition, String content, Integer pageindex,Integer pagecount){
        //获取用户code
        String code = GetUserCode.getCode(userService,rolesService);
        if (code == null){
            return DataResult.getDataResult(1,"当前用户的单位代码为空",new PageDataVO<>(0,0,0,0));
        }

        //System.out.println(code+"--"+condition+"--"+content+"--"+startdate_+"--"+enddate_);
        List<Companyallinfo> list = service.SelectAllCompanyInfoByCondition(code,condition,"%"+content+"%",pageindex,pagecount);
        if (list.size()==0){
            return DataResult.getDataResult(1,"对不起！没有查询到您想要的数据！",new PageDataVO<>(0,0,0,0));
        }
        int count = service.SelectCountAllinfoByCondition(code,condition,"%"+content+"%");
        if (count==0){
            return DataResult.getDataResult(1,"对不起！没有查询到您想要的数据！",new PageDataVO<>(0,0,0,0));
        }
        PageDataVO vo = new PageDataVO(count,pageindex,pagecount,list);
        return DataResult.getDataResult(0,"操作执行成功！！！",vo);
    }
    @PostMapping("/UpdateSlxxBySlh")
    @ApiOperation(value = "修改捆号",tags = "修改捆号")
    public DataResult UpdateSlxx(String slh,String kh){
        if (StringUtils.isEmpty(slh) || StringUtils.isEmpty(kh)){
            return DataResult.getDataResult(1,"当前用户的单位代码为空",new PageDataVO<>(0,0,0,0));
        }
        int count = slxxService.UpdateSlxx(slh,kh);
        if (count == 0){
            return DataResult.FAILURE();
        }
        return DataResult.SUCCESS();
    }
    @PostMapping("/SelectCompanyInfoBySlzt")
    @ApiOperation(value = "根据受理状态与受理部门查询档案卷",tags = "根据受理状态与受理部门查询档案卷")
    public DataResult SelectCompanyInfoBySlzt(String slzt,String slrbm,String startdate,String enddate,Integer pageindex,Integer pagecount){
        //System.out.println(slzt+"--"+slrbm+"--"+startdate+"--"+enddate+"--"+pageindex+"--"+pagecount);
        String code = GetUserCode.getCode(userService,rolesService);
        if (StringUtils.isEmpty(slzt) || StringUtils.isEmpty(slrbm)){
            return DataResult.FAILURE_NULLDATA();
        }
        String startdate_ = null;
        String enddate_ = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        //从系统有数据开始算起
        if (startdate == "" && enddate == ""){
            startdate_ = "2020-11-01";
            //startdate_ = "2020-01-01 00:00:00";
            enddate_ = date;
        }else if (startdate == "" && enddate != ""){
            //起始日期为空，截止日期不为空
            startdate_ ="2020-11-01";
            enddate_ = enddate;
        }else if (startdate != "" && enddate == ""){
            startdate_ = startdate;
            enddate_ = date;
        }else if (startdate != "" && enddate != ""){
            startdate_ = startdate;
            enddate_ = enddate;
        }
        //System.out.println("---"+slzt+"--"+slrbm+"--"+startdate_+"--"+enddate_+"--"+pageindex+"--"+pagecount);
        List<Companyallinfo> list = service.SelectCompanyInfoBySlzt(code,slzt,"%"+slrbm+"%",startdate_,enddate_,pageindex,pagecount);
        int count = service.SelectCountBySlzt(code,slzt,"%"+slrbm+"%",startdate_,enddate_);
        if (list.size()==0){
            return DataResult.getDataResult(1,"对不起！没有查询到您想要的数据！",new PageDataVO<>(0,0,0,0));
        }
        if (count==0){
            return DataResult.getDataResult(1,"对不起！没有查询到您想要的数据！",new PageDataVO<>(0,0,0,0));
        }
        PageDataVO vo = new PageDataVO(count,pageindex,pagecount,list);
        return DataResult.getDataResult(0,"执行操作成功",vo);
    }
}
