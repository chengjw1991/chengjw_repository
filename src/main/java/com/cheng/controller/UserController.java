package com.cheng.controller;

import com.cheng.beans.*;
import com.cheng.service.LoginLogService;
import com.cheng.service.UserService;
import com.cheng.service.UserallinfoService;
import com.cheng.token.JwtData;
import com.cheng.token.JwtUtil;
import com.cheng.utils.EncryptPassword;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author ChengJW
 * 2020/11/22/022
 */
@Api(value = "用户相关接口")
@Controller("UserController")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "UserServiceImpl")
    private UserService service;
    @Resource(name = "UserallinfoServiceImpl")
    private UserallinfoService userallinfoService;
    @Resource(name = "LoginLogServiceImpl")
    private LoginLogService logService;

    @ResponseBody
    @PostMapping("/UserLogin")
    @ApiOperation(value = "登录接口",tags = "用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名"),
            @ApiImplicitParam(name = "password",value = "用户密码")
    })
    public DataResult Login( String username, String password, HttpServletResponse response,HttpServletRequest request){
        //System.out.println(username+"--"+password);
        //验证用户名是否存在
        int checkedcount = service.CheckUserByUsername(username);
        if (checkedcount == 0){
            return DataResult.FAILURE();
        }
        //先验证是否是被禁止的用户
        int delflag = service.SelectDelflagByName(username);
        if (delflag == 1){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_ACCOUNT_IS_DISABLED);
        }
        String password_secret = EncryptPassword.Encrypt(password);
        // 获取登录主体
        try {
            Subject subject = SecurityUtils.getSubject();
            // 创建登录token
            UsernamePasswordToken token = new UsernamePasswordToken(username,password_secret);
            //登录
            subject.login(token);
            // 登录成功之后，shiro会将user 放入subject中，并生成token
            UserInfo user = (UserInfo) subject.getPrincipal();
            //保存登录信息
            InsertLoginInfo(user,request);
            //创建token
            String acceptToken = JwtUtil.CreateAccepteToken(user.getUserid(),getTokenClaimMap(user));
            //System.out.println(user.getSex());
            //将token放入响应文件header
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader(JwtData.JWT_NAME,acceptToken);

           return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,new LoginVO(user));
        } catch (UnauthenticatedException e) {
           LOGGER.error("UnauthenticatedException...{}");
           return DataResult.getDataResult(CodeAndMsgImpl.UnauthenticatedException);
        } catch (UnknownAccountException e){
            LOGGER.error("UnknownAccountException...{}");
            return DataResult.getDataResult(CodeAndMsgImpl.UnknownAccountException);
        } catch (AuthenticationException e){
            LOGGER.error("AuthenticationException...{}");
            return DataResult.getDataResult(CodeAndMsgImpl.AuthenticationException);
        }
    }
    //保存登录信息
    public  void InsertLoginInfo(UserInfo user, HttpServletRequest request){
        Integer id = user.getId();
        Userallinfo info = userallinfoService.SelectUserInfoById(id);
        String username = info.getUsername();
        String realname = info.getRealname();
        String dwname = info.getDwname();
        String dwcode = info.getDwcode();
        String bmname = info.getBmname();
        //获取ip
        String clientip = request.getRemoteAddr();
        String userid = info.getUserid();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = format.format(new Date());
        Loginlog log = new Loginlog(0,username,realname,dwname,dwcode,bmname,clientip,userid,date);
        int count = logService.InsertLoginLog(log);
        if (count>0){
            System.out.println("-----"+username+":logSuccess"+"-----");
        }else {
            System.out.println("-----"+username+":logFailure"+"-----");
        }
    }

    public static Map<String,Object> getTokenClaimMap(UserInfo user){
        List<Roles> roles = user.getRolesList();
        List<Permission> permissions = user.getPermList();
        List<String> roles_type = new ArrayList<>();
        for (Roles role : roles){
            String role_type = role.getType();
            roles_type.add(role_type);
        }
        List<String> permissions_code = new ArrayList<>();
        for (Permission perm : permissions){
            String perm_code = perm.getCode();
            permissions_code.add(perm_code);
        }
        Map<String,Object> map = new ConcurrentHashMap<>();
        map.put(JwtData.JWT_ROLES,roles_type);
        map.put(JwtData.JWT_PERMISSION,permissions_code);
        map.put(JwtData.JWT_USERID,user.getUserid());
        return map;
    }
    /**
     *
     */
}
