package com.cheng.aop;

import com.cheng.beans.Operationlog;
import com.cheng.beans.UserInfo;
import com.cheng.service.OperationLogService;
import com.cheng.service.UserService;
import com.cheng.token.JwtUtil;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author ChengJW
 * 2020/12/15/015
 */
@Aspect
@Component
public class OperationAOP {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationAOP.class);

    @Resource(name = "UserServiceImpl")
    public UserService service;
    @Resource(name ="OperationLogServiceImpl")
    public OperationLogService logService;


    //携带token的请求，登出删除所有信息，无法取得用户信息
    @Pointcut("execution(* com.cheng.controller.*.*(..))  && !execution(* com.cheng.controller.WebPageController.*()) && !execution(* com.cheng.controller.UserController.Login(..)) && !execution(* com.cheng.controller.LogoutController.*()) &&!execution(* com.cheng.controller.UserInfoController.home())")
    public void pointcutWithToken(){

    }
    @AfterReturning("pointcutWithToken()")
    public void InsertSuccessLogWithToken(JoinPoint joinPoint){
        InsertOperationLogWithToken(joinPoint,"正常");
    }
    @AfterThrowing("pointcutWithToken()")
    public void InsertFailureLogWithToken(JoinPoint joinPoint){
        InsertOperationLogWithToken(joinPoint,"异常");
    }

    //页面跳转,登录页面不做记录，因为无法确定用户名称
    @Pointcut("execution(* com.cheng.controller.WebPageController.*()) && !execution(* com.cheng.controller.WebPageController.SkipToLogin())")
    public void pointcutSkipToPage(){

    }
    @AfterReturning("pointcutSkipToPage()")
    public void InsertSuccessLogSkipToPage(JoinPoint joinPoint){
        InsertOperationLogSkipToPage(joinPoint,"正常");
    }
    @AfterThrowing("pointcutSkipToPage()")
    public void InsertFailureLogSkipToPage(JoinPoint joinPoint){
        InsertOperationLogSkipToPage(joinPoint,"异常");
    }

    //携带token请求
    public void InsertOperationLogWithToken(JoinPoint joinPoint,String status){
        //获取方法对象
        Signature signature = joinPoint.getSignature();
        //获取方法名
        String name = signature.getName();
        //获取类名
        String classname = signature.getDeclaringTypeName();
        //获取参数
        String argsstr = Arrays.toString(joinPoint.getArgs());
        //获取请求request
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        //获取请求路径
        String path = request.getServletPath().toString();
        //获取请求ip
        String clientip = request.getRemoteAddr();
        //获取用户
        //System.out.println(classname+"-*-*-*-*-*-*-*-"+name);
        try {
            String token = (String) SecurityUtils.getSubject().getPrincipal();
            String userid = JwtUtil.ParseUserId(token);
            UserInfo info = service.SelectByUserId(userid);
            String username = info.getUsername();
            String realname = info.getRealname();
            String dwcode = info.getDwcode();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            //插入日志
            Operationlog log = new Operationlog(0,username,realname,clientip,classname,name,date,status,dwcode,argsstr);
            System.out.println("-----WithTokenAop-----");
            logService.InsertLog(log);
        } catch (Exception exception) {
            LOGGER.error("OperationAOP类记录的错误：java.lang.ClassCastException: com.cheng.beans.UserInfo cannot be cast to java.lang.String");
        }
    }

    //页面跳转
    public void InsertOperationLogSkipToPage(JoinPoint joinPoint,String status){
        //获取方法对象
        Signature signature = joinPoint.getSignature();
        //获取方法名
        String name = signature.getName();
        //获取类名
        String classname = signature.getDeclaringTypeName();
        //获取参数
        String argsstr = Arrays.toString(joinPoint.getArgs());
        //获取请求request
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        //获取请求路径
        String path = request.getServletPath().toString();
        //获取请求ip
        String clientip = request.getRemoteAddr();
        //获取用户
        //System.out.println(classname+"-*-*-*-*-*-*-*-"+name);
        try {
            UserInfo info = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            String username = info.getUsername();
            String realname = info.getRealname();
            String dwcode = info.getDwcode();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            //插入日志
            Operationlog log = new Operationlog(0,username,realname,clientip,classname,name,date,status,dwcode,argsstr);
            System.out.println("-----NoTokenAop-----");
            logService.InsertLog(log);
        } catch (Exception exception) {
            LOGGER.error("OperationAOP类记录的错误：java.lang.ClassCastException: java.lang.String cannot be cast to com.cheng.beans.UserInfo");
        }
    }
}
