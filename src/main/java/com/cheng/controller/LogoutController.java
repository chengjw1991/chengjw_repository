package com.cheng.controller;

import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author ChengJW
 * 2020/11/30/030
 */
@Api(value = "登出接口")
@Controller("LogoutController")
public class LogoutController {

    @ApiOperation(value = "登出接口",tags = "登出接口")
    @PostMapping("/logout")
    @ResponseBody
    public DataResult Logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS);
    }
}
