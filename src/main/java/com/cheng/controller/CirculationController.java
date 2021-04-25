package com.cheng.controller;

import com.cheng.beans.Companyallinfo;
import com.cheng.beans.Companyinfo;
import com.cheng.beans.Lzxx;
import com.cheng.beans.PageDataVO;
import com.cheng.service.*;
import com.cheng.utils.GetUserCode;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/21/021
 */
@RestController("CirculationController")
@RequestMapping("/circulation")
public class CirculationController {

    @Resource(name = "UserServiceImpl")
    private UserService userService;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;
    @Resource(name = "CompanyallinfoServiceImpl")
    private CompanyallinfoService compservice;
    @Resource(name = "SlxxServiceImpl")
    private SlxxService slxxService;
    @Resource(name = "LzxxServiceImpl")
    private LzxxService lzxxService;
    @Resource(name = "CompanyinfoServiceImpl")
    private CompanyinfoService companyinfoService;

    @PostMapping("/selectinfobyslh")
    @ApiOperation(value = "流转过程-档案接收-查询企业信息",tags = "流转过程-档案接收-查询企业信息")
    public DataResult SelectInfoBySlh(String slh){
        //System.out.println(slh);
        if (StringUtils.isEmpty(slh)){
            return DataResult.FAILURE_NULLDATA();
        }
        String code = GetUserCode.getCode(userService,rolesService);
        if (code == null){
            return DataResult.FAILURE();
        }
        Companyallinfo info = compservice.SelectInfoBySlh(slh,code,"已受理","已受理");
        if (info == null){
            return  DataResult.FAILURE_NODATA();
        }
        //System.out.println(info);
        return  DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,info);
   }
    @PostMapping("/updateslxxofslzt")
    @ApiOperation(value = "流转过程-档案接收-根据受理号，修改受理状态",tags = "流转过程-档案接收-根据受理号，修改受理状态")
    public DataResult UpdateSLZT(@RequestParam("slh[]")List<String> slh){
        //System.out.println(slh);
        if (slh.size() == 0){
            return DataResult.FAILURE_NULLDATA();
        }
        int count = slxxService.UpdateSlxxOfslzt(slh,2);
        if (count == 0){
            return DataResult.FAILURE();
        }
        for(String slh_ : slh){
            Companyinfo info = companyinfoService.SelectCompanyInfoBySlh(slh_);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Lzxx lzxx = new Lzxx(0,info.getSlh(),info.getQymc(),GetUserCode.getUserName(userService),info.getCode(),date,"已接收");
            int lzxxcount = lzxxService.InsertLzxx(lzxx);
        }
        return  DataResult.SUCCESS();
    }
    @PostMapping("/selectinfobyslhofyjs")
    @ApiOperation(value = "流转过程-查询已接收的档案、以便数字化移交",tags = "流转过程-查询已接收的档案、以便数字化移交")
    public DataResult SelectInfoBySlhOfYjs(String slh){
        if (StringUtils.isEmpty(slh)){
            return DataResult.FAILURE_NULLDATA();
        }
        String code = GetUserCode.getCode(userService,rolesService);
        if (code == null){
            return DataResult.FAILURE();
        }
        Companyallinfo info = compservice.SelectInfoBySlh(slh,code,"已接收","数字化中");
        if (info == null){
            return  DataResult.FAILURE_NODATA();
        }
        //System.out.println(info);
        return  DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,info);
    }
    @PostMapping("/updateslxxofszhz")
    @ApiOperation(value = "流转过程-数字化移交-根据受理号，修改受理状态",tags = "流转过程-数字化移交-根据受理号，修改受理状态")
    public DataResult UpdateSLZTof(@RequestParam("slh[]")List<String> slh){
        //System.out.println(slh);
        if (slh.size() == 0){
            return DataResult.FAILURE_NULLDATA();
        }
        int count = slxxService.UpdateSlxxOfslzt(slh,3);
        if (count == 0){
            return DataResult.FAILURE();
        }
        for(String slh_ : slh){
            Companyinfo info = companyinfoService.SelectCompanyInfoBySlh(slh_);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Lzxx lzxx = new Lzxx(0,info.getSlh(),info.getQymc(),GetUserCode.getUserName(userService),info.getCode(),date,"数字化中");
            int lzxxcount = lzxxService.InsertLzxx(lzxx);
        }
        return  DataResult.SUCCESS();
    }
    @PostMapping("/updateslxxtoyszh")
    @ApiOperation(value = "流转过程-数字化完成-根据受理号，修改受理状态",tags = "流转过程-数字化完成-根据受理号，修改受理状态")
    public DataResult UpdateSlxxToYszh(@RequestParam("slh[]")List<String> slh){
        //System.out.println(slh);
        if (slh.size() == 0){
            return DataResult.FAILURE_NULLDATA();
        }
        int count = slxxService.UpdateSlxxOfslzt(slh,4);
        if (count == 0){
            return DataResult.FAILURE();
        }
        for(String slh_ : slh){
            Companyinfo info = companyinfoService.SelectCompanyInfoBySlh(slh_);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Lzxx lzxx = new Lzxx(0,info.getSlh(),info.getQymc(),GetUserCode.getUserName(userService),info.getCode(),date,"已数字化");
            int lzxxcount = lzxxService.InsertLzxx(lzxx);
        }
        return  DataResult.SUCCESS();
    }
    @PostMapping("/selectinfobyszhwc")
    @ApiOperation(value = "流转过程-查询已数字化完成的档案",tags = "流转过程-查询已数字化完成的档案")
    public DataResult SelectInfoBySzhwc(String slh){
        if (StringUtils.isEmpty(slh)){
            return DataResult.FAILURE_NULLDATA();
        }
        String code = GetUserCode.getCode(userService,rolesService);
        if (code == null){
            return DataResult.FAILURE();
        }
        Companyallinfo info = compservice.SelectInfoBySlh(slh,code,"已数字化","已数字化");
        if (info == null){
            return  DataResult.FAILURE_NODATA();
        }
        //System.out.println(info);
        return  DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,info);
    }
    @PostMapping("/updateslxxofyfp")
    @ApiOperation(value = "流转过程-预分批-根据受理号，修改预分批",tags = "流转过程-预分批-根据受理号，修改预分批")
    public DataResult UpdateSlxxOfYfp(@RequestParam("slh[]")List<String> slh){
        if (slh.size() == 0){
            return DataResult.FAILURE_NULLDATA();
        }
        int count = slxxService.UpdateYfp(slh,1);
        if (count == 0){
            return DataResult.FAILURE();
        }
        for(String slh_ : slh){
            Companyinfo info = companyinfoService.SelectCompanyInfoBySlh(slh_);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Lzxx lzxx = new Lzxx(0,info.getSlh(),info.getQymc(),GetUserCode.getUserName(userService),info.getCode(),date,"预分批");
            int lzxxcount = lzxxService.InsertLzxx(lzxx);
        }
        return  DataResult.SUCCESS();
    }
    @PostMapping("/SelectInfoBycondition")
    @ApiOperation(value = "流转过程-预分批管理-根据条件查询预分批档案信息",tags = "流转过程-预分批管理-根据条件查询预分批档案信息")
    public DataResult SelectInfoBycondition(String condition,String content,String startdate,String enddate,Integer pageindex,Integer pagecount){
        String code = GetUserCode.getCode(userService,rolesService);
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
        List<Companyallinfo> list = compservice.SelectInfoOfYFP(code,condition,"%"+content+"%",startdate_,enddate_,pageindex,pagecount);
        int count = compservice.SelectCountInfoOfYFP(code,condition,"%"+content+"%",startdate_,enddate_);
        if (list.size()==0){
            return DataResult.getDataResult(1,"对不起！没有查询到您想要的数据！",new PageDataVO<>(0,0,0,0));
        }
        if (count==0){
            return DataResult.getDataResult(1,"对不起！没有查询到您想要的数据！",new PageDataVO<>(0,0,0,0));
        }
        PageDataVO vo = new PageDataVO(count,pageindex,pagecount,list);
        return DataResult.getDataResult(0,"执行操作成功",vo);
    }
    @PostMapping("/updateinfolofisdd")
    @ApiOperation(value = "流转过程-转入待定",tags = "流转过程-转入待定")
    public DataResult Updateinfolofisdd(@RequestParam("slh[]")List<String> slh){
        if (slh.size() == 0){
            return DataResult.FAILURE_NULLDATA();
        }
        int count = slxxService.UpdateInfoOfZrdd(slh);
        if (count == 0){
            return DataResult.FAILURE();
        }
        return DataResult.SUCCESS();
    }
}
