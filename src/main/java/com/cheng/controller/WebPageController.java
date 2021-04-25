package com.cheng.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author ChengJW
 * 2020/11/24/024
 */
@Controller
@Api(value = "页面跳转接口")
public class WebPageController {


    @ApiOperation(value = "系统登录页面",tags = "系统登录页面")
    @GetMapping("/login")
    public String SkipToLogin(){
        return "login";
    }

    @GetMapping("/business/{PageStr}")
    @ApiOperation(value = "首页页面跳转")
    public String To_archivesAccept(@PathVariable("PageStr") String PageStr){
        return "business_"+PageStr;
    }

    @ApiOperation(value = "系统首页",tags = "系统首页")
    @GetMapping("/index")
    public String SkipToIndex(){
        return "index";
    }

    @ApiOperation(value = "系统维护模块跳转接口")
    @GetMapping("/systemDefend/{str}")
    public String systemDefend(@PathVariable("str") String str){
        return "systemDefend_"+str;
    }

    @ApiOperation(value = "角色管理跳转接口")
    @GetMapping("/rolesAdmin/{str}")
    public String RolesAdd(@PathVariable("str")String str){
        return "systemDefend_"+str;
    }

    @ApiOperation(value = "用户管理跳转接口")
    @GetMapping("/userAdmin/{str}")
    public String UserAdd(@PathVariable("str")String str){
        return "systemDefend_"+str;
    }

    @ApiOperation(value = "日志管理跳转接口")
    @GetMapping("/systemLog/{str}")
    public String SystemLog(@PathVariable("str")String str){
        return  "systemDefend_"+str;
    }

    @ApiOperation(value = "档案借阅接口跳转",tags = "档案借阅接口跳转")
    @GetMapping("/read/{str}")
    public String Read(@PathVariable("str")String str){
        return "archivesRead_"+str;
    }

    @ApiOperation(value = "档案借阅接口跳转",tags = "档案借阅接口跳转")
    @GetMapping("/archivesRead/{str}")
    public String ArchivesRead(@PathVariable("str")String str){
        return "archivesRead_"+str;
    }

    @ApiOperation(value = "受理管理相关页面跳转",tags = "受理管理相关页面跳转")
    @GetMapping("/acceptAdmin/{str}")
    public String AcceptAdmin(@PathVariable("str")String str){
        return "acceptAdmin_"+str;
    }

    @ApiOperation(value = "库房管理相关页面跳转",tags = "库房管理相关页面跳转")
    @GetMapping("/archivesKuFang/{str}")
    public String ArchivesKuFang(@PathVariable("str")String str){
        return "archivesKuFang_"+str;
    }

    @GetMapping("/circulation/{str}")
    @ApiOperation(value = "流转过程相关页面跳转接口",tags = "流转过程相关页面跳转接口")
    public String Circulation(@PathVariable("str")String str){
        return "circulation_"+str;
    }

    @GetMapping("/archivesStatistics/{str}")
    @ApiOperation(value = "档案统计相关页面跳转接口",tags = "档案统计相关页面跳转接口")
    public String archivesStatistics(@PathVariable("str")String str){
        return "archivesStatistics_"+str;
    }

    @GetMapping("/archivesAdmin/{str}")
    @ApiOperation(value = "操作流程跳转接口",tags = "操作流程跳转接口")
    public String archivesAdmin(@PathVariable("str")String str){
        return "archivesAdmin_" +str;
    }

    @GetMapping("/archivesMove/{str}")
    @ApiOperation(value = "迁移相关跳转接口",tags = "迁移相关跳转接口")
    public String archivesMove(@PathVariable("str")String str){
        return "archivesMove_" +str;
    }

}
