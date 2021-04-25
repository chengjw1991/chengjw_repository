package com.cheng.utils.exception;

/**
 * @Author ChengJW
 * 2020/11/20/020
 *
 * 自定义异常类
 */
public class MyException extends RuntimeException{

    private Integer code;
    private String msg;

    public MyException(){
        super();
    }
    public MyException(Integer code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
