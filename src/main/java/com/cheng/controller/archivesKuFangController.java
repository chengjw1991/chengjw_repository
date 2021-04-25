package com.cheng.controller;

import com.cheng.beans.Companyallinfo;
import com.cheng.service.CompanyallinfoService;
import com.cheng.service.RolesService;
import com.cheng.service.UserService;
import com.cheng.utils.GetUserCode;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-05 13:51
 */
@RestController("archivesKuFangController")
@RequestMapping("/archivesKuFang")
@Api(value = "档案库房相关的接口",tags = "档案库房相关的接口")
public class archivesKuFangController {

    @Resource(name = "CompanyallinfoServiceImpl")
    private CompanyallinfoService comservice;
    @Resource(name = "UserServiceImpl")
    private UserService userService;
    @Resource(name = "RolesServiceImpl")
    private RolesService rolesService;

    @PostMapping("/selectinfobyslh")
    @ApiOperation(value = "档案库房-查询档案信息",tags = "档案库房-查询档案信息")
    public DataResult SelectInfoBySlh(String slh){
        String code = GetUserCode.getCode(userService,rolesService);
        Companyallinfo info  = comservice.SelectInfoBySlh(slh,code,"已数字化","入库");
        if (info == null){
            return DataResult.FAILURE_NODATA();
        }
        return  DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,info);
    }

}
