package com.cheng.utils.data;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author ChengJW
 * 2020/11/20/020
 *
 * 统一格式的响应数据类
 */
public class DataResult<T> {
    @ApiModelProperty(value = "响应代码")
    private Integer code;
    @ApiModelProperty(value = "响应信息")
    private String msg;
    @ApiModelProperty(value = "响应数据")
    private T data; //返回的数据多样，故写成泛型

    public DataResult(){

    }
    public DataResult(Integer code,String msg){
        this.code = code;
        this.msg = msg;
        this.data =null;
    }
    public DataResult(Integer code,String msg,T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public DataResult(T data){
        this.code = CodeAndMsgImpl.SUCCESS.getCode();
        this.msg = CodeAndMsgImpl.SUCCESS.getMsg();
        this.data = data;
    }
    public DataResult(CodeAndMsg mess){
        this.code = mess.getCode();
        this.msg = mess.getMsg();
        this.data=null;
    }
    public DataResult(CodeAndMsg mess,T data){
        this.code = mess.getCode();
        this.msg = mess.getMsg();
        this.data = data;
    }
    //静态方法创建类对象
    public static DataResult getDataResult(Integer code,String msg){
        return new DataResult(code,msg);
    }
    public static <T>DataResult getDataResult(Integer code,String msg,T data){
        return new DataResult(code,msg,data);
    }
    public static <T>DataResult getDataResult(CodeAndMsg mess){
        return new DataResult(mess.getCode(), mess.getMsg());
    }
    public static <T>DataResult getDataResult(CodeAndMsg mess,T data){
        return new DataResult(mess.getCode(), mess.getMsg(),data);
    }
    // 枚举实例创建对象
    public static DataResult SUCCESS(){
        return new DataResult(CodeAndMsgImpl.SUCCESS);
    }

    public static DataResult FAILURE(){
        return  new DataResult(CodeAndMsgImpl.FAILURE.getCode(),CodeAndMsgImpl.FAILURE.getMsg());
    }
    //210 重复数据
    public static DataResult FAILURE_DUPLICATIONDATA(){
        return new DataResult(CodeAndMsgImpl.FAILURE_DuplicateData);
    }
    //220 未能查到数据
    public static DataResult FAILURE_NODATA(){
        return new DataResult(CodeAndMsgImpl.FAILURE_NODATA.getCode(),CodeAndMsgImpl.FAILURE_NULLDATA.getMsg());
    }
    //230 传入空数据
    public static DataResult FAILURE_NULLDATA(){
        return  new DataResult(CodeAndMsgImpl.FAILURE_NULLDATA);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
