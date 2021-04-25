package com.cheng.beans;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/14/014
 * 自定义分页查询响应数据类
 */
public class PageDataVO<T> {
    private Integer sumcount;//数据总条数
    private Integer pagecurr;//当前页数
    private Integer pagecount;//每页的行数
    private T datalist;//数据集合
    //根据需要，可以进行自定义参数的回显
    //查询登录日志时，参数回显
    private String startdate;
    private String enddate;
    private String username;

    public PageDataVO() {
    }

    //最基本的分页数据回显
    public PageDataVO(Integer sumcount, Integer pagecurr, Integer pagecount, T datalist) {
        this.sumcount = sumcount;
        this.pagecurr = pagecurr;
        this.pagecount = pagecount;
        this.datalist = datalist;
    }

    //登录日志分页数据回显
    public PageDataVO(Integer sumcount, Integer pagecurr, Integer pagecount, T datalist, String startdate, String enddate, String username) {
        this.sumcount = sumcount;
        this.pagecurr = pagecurr;
        this.pagecount = pagecount;
        this.datalist = datalist;
        this.startdate = startdate;
        this.enddate = enddate;
        this.username = username;
    }

    public Integer getSumcount() {
        return sumcount;
    }

    public void setSumcount(Integer sumcount) {
        this.sumcount = sumcount;
    }

    public Integer getPagecurr() {
        return pagecurr;
    }

    public void setPagecurr(Integer pagecurr) {
        this.pagecurr = pagecurr;
    }

    public Integer getPagecount() {
        return pagecount;
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    public T getDatalist() {
        return datalist;
    }

    public void setDatalist(T datalist) {
        this.datalist = datalist;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "PageDataVO{" +
                "sumcount=" + sumcount +
                ", pagecurr=" + pagecurr +
                ", pagecount=" + pagecount +
                ", datalist=" + datalist +
                '}';
    }
}
