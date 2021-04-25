package com.cheng.controller;

import com.cheng.beans.Companyallinfo;
import com.cheng.beans.Companyinfo;
import com.cheng.beans.Lzxx;
import com.cheng.beans.Lzxxinfo;
import com.cheng.service.*;
import com.cheng.utils.GetUserCode;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
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
 * @author chengjw
 * @date 2021-01-05 17:09
 */
@RestController("archivesAdminController")
@RequestMapping("/archivesAdmin")
@Api(value = "操作流程")
public class archivesAdminController {

    @Resource(name = "LzxxinfoServiceImpl")
    private LzxxinfoService lzxxinfoService;
    @Resource(name = "SlxxServiceImpl")
    private SlxxService slxxService;
    @Resource(name = "CompanyinfoServiceImpl")
    private CompanyinfoService companyinfoService;
    @Resource(name = "UserServiceImpl")
    private UserService userService;
    @Resource(name = "LzxxServiceImpl")
    private LzxxService lzxxService;

    @PostMapping("/selectlzxxbyslh")
    @ApiOperation(value = "档案库房-档案管理-操作流程",tags = "档案库房-档案管理-操作流程")
    public DataResult SelectLzxxBySlh(String slh){
        if (StringUtils.isEmpty(slh)){
            return DataResult.FAILURE_NULLDATA();
        }
        List<Lzxxinfo> list = lzxxinfoService.SelectLzxxBySlh(slh);
        if (list.size() == 0){
            return DataResult.FAILURE_NODATA();
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }
    @PostMapping("/updateslxxofrk")
    @ApiOperation(value = "库房管理-档案管理-入库",tags = "库房管理-档案管理-入库")
    public DataResult UpdateSlxxOfRk(@RequestParam("slh[]")List<String> slh){
        //System.out.println(slh);
        if (slh.size() == 0){
            return DataResult.FAILURE_NULLDATA();
        }
        int count = slxxService.UpdateSlxxOfslzt(slh,5);
        if (count == 0){
            return DataResult.FAILURE();
        }
        for(String slh_ : slh){
            Companyinfo info = companyinfoService.SelectCompanyInfoBySlh(slh_);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Lzxx lzxx = new Lzxx(0,info.getSlh(),info.getQymc(),GetUserCode.getUserName(userService),info.getCode(),date,"入库");
            int lzxxcount = lzxxService.InsertLzxx(lzxx);
        }
        return  DataResult.SUCCESS();
    }
    @PostMapping("/updateslxxofck")
    @ApiOperation(value = "库房管理-档案管理-出库",tags = "库房管理-档案管理-出库")
    public DataResult UpdateSlxxOfCk(@RequestParam("slh[]")List<String> slh){
        //System.out.println(slh);
        if (slh.size() == 0){
            return DataResult.FAILURE_NULLDATA();
        }
        int count = slxxService.UpdateSlxxOfslzt(slh,6);
        if (count == 0){
            return DataResult.FAILURE();
        }
        for(String slh_ : slh){
            Companyinfo info = companyinfoService.SelectCompanyInfoBySlh(slh_);
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Lzxx lzxx = new Lzxx(0,info.getSlh(),info.getQymc(),GetUserCode.getUserName(userService),info.getCode(),date,"出库");
            int lzxxcount = lzxxService.InsertLzxx(lzxx);
        }
        return  DataResult.SUCCESS();
    }
}
