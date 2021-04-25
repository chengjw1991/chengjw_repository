package com.cheng.controller;

import com.cheng.beans.Roles;
import com.cheng.beans.UserInfo;
import com.cheng.beans.indexVO;
import com.cheng.service.UserService;
import com.cheng.token.JwtData;
import com.cheng.token.JwtUtil;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ChengJW
 * 2020/11/29/029
 */
@RestController("IndexController")
@Api(value = "首页数据相关接口")
@RequestMapping("/index")
public class IndexController {

    @Resource(name = "UserServiceImpl")
    private UserService service;

    @PostMapping("/data")
    public DataResult IndexData(HttpServletRequest request){
        String jwtToken = request.getHeader(JwtData.JWT_NAME);
        String userid = JwtUtil.ParseUserId(jwtToken);
        UserInfo info = service.SelectUserInfoByUserid(userid);
        List<Roles> roles = info.getRolesList();
        String rolename = "";
        for (Roles role : roles){
            rolename += role.getName();
        }
        String photo = info.getPrifilephoto();
        indexVO vo = new indexVO(info.getUsername(),photo,rolename);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,vo);
    }
}
