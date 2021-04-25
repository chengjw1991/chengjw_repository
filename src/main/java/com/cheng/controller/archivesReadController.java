package com.cheng.controller;

import com.cheng.beans.Borrowread;
import com.cheng.beans.Companyinfo;
import com.cheng.beans.PageDataVO;
import com.cheng.beans.UserInfo;
import com.cheng.service.BorrowreadService;
import com.cheng.service.CompanyinfoService;
import com.cheng.service.Impl.UserServiceImpl;
import com.cheng.service.RolesService;
import com.cheng.token.JwtUtil;
import com.cheng.utils.GetUserCode;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;
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
 * @author chengjw
 * @date 2020-12-17 14:55
 */
@RestController("archivesReadController")
@RequestMapping("/archivesRead")
@Api(value = "档案借阅相关的接口")
public class archivesReadController {

    @Resource(name = "CompanyinfoServiceImpl")
    private CompanyinfoService compservice;
    @Resource(name = "BorrowreadServiceImpl")
    private BorrowreadService borrowreadService;
    @Resource(name = "UserServiceImpl")
    private UserServiceImpl userService;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;

    @PostMapping("/selectcompanyinfobyslh")
    @ApiOperation(value = "根据受理号回显企业信息",tags = "根据受理号回显企业信息")
    public DataResult SelectCompanyInfoBySlh(String slh){
        if (slh == null){
            return DataResult.FAILURE_NULLDATA();
        }
        Companyinfo info = compservice.SelectCompanyInfoBySlh(slh);
        if (info != null){
            return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,info);
        }
        return DataResult.FAILURE_NODATA();
    }
//    @PostMapping("/insertborrowinfo")
//    @ApiOperation(value = "新增借阅信息",tags = "新增借阅信息")
//    public DataResult InsertBorrowInfo(String jyr,String ghrq,String jyrq,String slh,String tyshxydm,String qymc,String bz,String code){
//        if (StringUtils.isEmpty(jyr)  || StringUtils.isEmpty(ghrq) || StringUtils.isEmpty(jyrq) || StringUtils.isEmpty(slh) || StringUtils.isEmpty(tyshxydm) || StringUtils.isEmpty(qymc)){
//            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NULLDATA);
//        }
//        //查看该档案是否已经借阅
//        Companyinfo companyinfo = compservice.SelectCompanyInfoBySlh(slh);
//        if (companyinfo.getIsborrow()==1){
//            return DataResult.getDataResult(205,"该卷档案已经借出，无法再次出借！");
//        }
//        int companycount = compservice.UpdateCompanyIsBorrow(slh,1);
//        String token = (String) SecurityUtils.getSubject().getPrincipal();
//        String userid = JwtUtil.ParseUserId(token);
//        UserInfo info = userService.SelectUserInfoByUserid(userid);
//        String username = info.getUsername();
//        Borrowread borrowread = new Borrowread(0,jyr,username,jyrq,ghrq,1,slh,tyshxydm,qymc,bz,code);
//        int count = borrowreadService.InsertBorrowread(borrowread);
//        if(count>0){
//            return DataResult.SUCCESS();
//        }
//        return DataResult.FAILURE();
//    }
    @PostMapping("/insertborrowinfo")
    @ApiOperation(value = "新增借阅信息",tags = "新增借阅信息")
    public DataResult InsertBorrowInfo(String jyr,String ghrq,String jyrq,String slh,String tyshxydm,String qymc,String bz,String code){
        return borrowreadService.InsertBorrowInfo(jyr, ghrq, jyrq, slh, tyshxydm, qymc, bz, code);
    }

    @PostMapping("/selectallborrowreadinfo")
    @ApiOperation(value = "档案借阅-根据条件查询所有的借阅信息",tags = "档案借阅-根据条件查询所有的借阅信息")
    public DataResult SelectAllBorrowreadInfo(String condition,String content,String startdate,String enddate,Integer pageindex,Integer pagecount){
        String code = GetUserCode.getCode(userService,rolesService);
        if (StringUtils.isEmpty(condition) || code == null){
            return DataResult.getDataResult(1,"单位代码为空或者输入的数据有问题",new PageDataVO<>(0,0,0,0));
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
        List<Borrowread> list = borrowreadService.SelectBorrowInfo(condition,"%"+content+"%",startdate_,enddate_,code,pageindex,pagecount);
        int sumcount = borrowreadService.SelectCountBorrowInfo(condition,"%"+content+"%",startdate_,enddate_,code);
        if (list.size() == 0 || sumcount == 0){
            return DataResult.getDataResult(1,"对不起没有查询到您需要的信息",new PageDataVO<>(0,0,0,0));
        }
        PageDataVO vo = new PageDataVO(sumcount,pageindex,pagecount,list);
        return DataResult.getDataResult(0,"操作执行成功",vo);
    }
    @PostMapping("/UpdateIsBackBySlh")
    @ApiOperation(value = "档案借阅-档案归还",tags = "档案借阅-档案归还")
    public DataResult UpdateBorrowreadBySlh(@RequestParam("slh[]")List<String> slh){
        return borrowreadService.UpdateIsBackBySlh(slh);
    }
    @PostMapping("/UpdateBzBySlh")
    @ApiOperation(value = "档案借阅-修改备注",tags = "档案借阅-修改备注")
    public DataResult UpdateBzBySlh(String slh,String bz){
        return  borrowreadService.UpdateBzBySlh(slh,bz);
    }
}
