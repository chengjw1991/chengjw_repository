package com.cheng.utils.exception;

import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.io.UnsupportedEncodingException;

/**
 * @Author ChengJW
 * 2020/11/20/020
 *
 * 自定义全局异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public DataResult HandlerException(Exception e){
        LOGGER.error("HandlerException...{}",e);
        return DataResult.getDataResult(CodeAndMsgImpl.EXCEPTION);
    }

    @ExceptionHandler(MyException.class)
    public DataResult HandlerException(MyException e){
        LOGGER.error("HandlerException...{}",e);
        return DataResult.getDataResult(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(UnsupportedEncodingException.class)
    public DataResult HandlerException(UnsupportedEncodingException e){
        LOGGER.error("HandlerException...{}",e);
        return DataResult.getDataResult(CodeAndMsgImpl.UnsupportedEncodingException);
    }
    // shiro 登录
    @ExceptionHandler(UnauthenticatedException.class)
    public DataResult  HandlerException(UnauthenticatedException e){
        LOGGER.error("UnauthenticatedException...{}",e);
        return DataResult.getDataResult(CodeAndMsgImpl.UnauthenticatedException);
    }
    // shiro 登录
    @ExceptionHandler(AuthenticationException.class)
    public DataResult  HandlerException(AuthenticationException e){
        LOGGER.error("AuthenticationException...{}",e);
        return DataResult.getDataResult(CodeAndMsgImpl.AuthenticationException);
    }
    // shiro 授权
    @ExceptionHandler(UnauthorizedException.class)
    public DataResult  HandlerException(UnauthorizedException e){
        LOGGER.error("UnauthorizedException...{}",e);
        return DataResult.getDataResult(CodeAndMsgImpl.UnauthorizedException);
    }
    // shiro 登录
    @ExceptionHandler(AuthorizationException.class)
    public DataResult  HandlerException(AuthorizationException e){
        LOGGER.error("UnauthenticatedException...{}",e);
        return DataResult.getDataResult(CodeAndMsgImpl.AuthorizationException);
    }
}
