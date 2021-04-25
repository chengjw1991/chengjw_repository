package com.cheng.controller;

import com.cheng.beans.Companyallinfo;
import com.cheng.beans.Qrqc;
import com.cheng.beans.Qyallinfo;
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
import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-06 10:08
 */
@RestController("ArchivesMoveController")
@RequestMapping("/archivesmove")
public class ArchivesMoveController {

    @Resource(name = "QyallinfoServiceImpl")
    private QyallinfoService qyallinfoService;
    @Resource(name = "UserServiceImpl")
    private UserService userService;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;
    @Resource(name = "CompanyallinfoServiceImpl")
    private CompanyallinfoService compservice;
    @Resource(name = "QrqcServiceImpl")
    private QrqcService qrqcService;

    @PostMapping("/selectqyinfo")
    @ApiOperation(value = "档案库房-档案迁移-档案迁出-根据条件查询企业",tags = "档案库房-档案迁移-档案迁出-根据条件查询企业")
    public DataResult SelectQyinfo(String condition,String content){
        String code = GetUserCode.getCode(userService,rolesService);
        List<Qyallinfo> list = qyallinfoService.SelectQyallinfo(condition,"%"+content+"%",code);
        if (list.size()==0){
            return DataResult.FAILURE_NODATA();
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }

    @PostMapping("/selectcompanyinfo")
    @ApiOperation(value = "档案库房-档案迁移-档案迁出-查看档案卷",tags = "档案库房-档案迁移-档案迁出-查看档案卷")
    public DataResult SelectCompanyInfo(String tyshxydm){
        if (StringUtils.isEmpty(tyshxydm)){
            return DataResult.FAILURE_NULLDATA();
        }
        List<Companyallinfo> list = compservice.SelectInfoByTyshxydm(tyshxydm);
        if (list.size() == 0){
            return DataResult.FAILURE_NODATA();
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }

    @PostMapping("/archivesQC")
    @ApiOperation(value = "档案库房-档案迁移-档案迁出-迁出",tags = "档案库房-档案迁移-档案迁出-迁出" )
    public DataResult ArchivesQC(@RequestParam("tyshxydm[]")List<String> tyshxydm,String dwcode){
        return  qrqcService.ArchivesQC(tyshxydm,dwcode);
    }
    @PostMapping("/archivesQR")
    @ApiOperation(value = "档案库房-档案迁移-档案迁出-迁入",tags = "档案库房-档案迁移-档案迁出-迁入" )
    public DataResult ArchivesQR(@RequestParam("tyshxydm[]")List<String> tyshxydm){
        return  qrqcService.ArchivesQR(tyshxydm);
    }
    @PostMapping("/selectqrqcinfo")
    @ApiOperation(value = "档案库房-档案迁移-档案迁入-根据条件查询企业信息",tags = "档案库房-档案迁移-档案迁入-根据条件查询企业信息")
    public DataResult SelectQrqcInfo(String condition,String content){
        //System.out.println(condition+"--"+content);
        String code = GetUserCode.getCode(userService,rolesService);
        if (code == null){
            return DataResult.FAILURE();
        }
        List<Qrqc> list = qrqcService.SelectInfoByConditon(condition,"%"+content+"%",code);
        if (list.size() == 0){
            return DataResult.FAILURE_NODATA();
        }
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,list);
    }
}
