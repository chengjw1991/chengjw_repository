package com.cheng.utils.data;

/**
 * @Author ChengJW
 * 2020/11/20/020
 */
public enum CodeAndMsgImpl implements CodeAndMsg{

    SUCCESS(100,"操作执行成功"),
    FAILURE(200,"操作失败"),
    FAILURE_DuplicateData(210,"重复数据"),
    FAILURE_NODATA(220,"没有您查询的数据"),
    FAILURE_NULLDATA(230,"输入出现空数据"),
    FAILURE_ACCOUNT_IS_DISABLED(240,"用户已被禁用，请联系管理员"),
    EXCEPTION(300,"出现异常"),
    UnsupportedEncodingException(310,"UnsupportedEncodingException"), // 编码格式异常
    UnauthenticatedException(320,"UnauthenticatedException"),  //shiro 登录验证异常
    AuthenticationException(330,"账号或密码错误，请重新登录"),  //shiro 登录验证异常
    TokenIsNull(320,"UnauthenticatedException/TokenIsNull"),
    ExpiredJwtException(250,"ExpiredJwtException/TokenIsInvalid"),
    UnknownAccountException(320,"UnknownAccountException"),
    UnauthorizedException(340,"UnauthorizedException"), // shiro 授权验证异常
    AuthorizationException(350,"AuthorizationException");

    private Integer code;
    private String msg;

    private CodeAndMsgImpl(){};
    private CodeAndMsgImpl(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
