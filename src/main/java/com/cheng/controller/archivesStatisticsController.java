package com.cheng.controller;

import com.cheng.service.SlxxService;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chengjw
 * @date 2020-12-29 14:13
 */
@RestController("archivesStatisticsController")
@RequestMapping("/ArchivesStatistics")
@Api(value = "档案统计相关接口",tags = "档案统计相关接口")
public class archivesStatisticsController {

    @Resource(name = "SlxxServiceImpl")
    private SlxxService slxxService;

    @PostMapping("/selectCount")
    @ApiOperation(value = "统计所有类型档案",tags = "统计所有类型档案")
    public DataResult SelectCount(String code,String startdate,String enddate){
        return slxxService.SelectCount(code,startdate,enddate) ;
    }
}
