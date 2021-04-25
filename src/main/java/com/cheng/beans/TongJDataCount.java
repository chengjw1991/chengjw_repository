package com.cheng.beans;

import java.io.Serializable;

/**
 * @author chengjw
 * @date 2020-12-29 18:13
 * 每个企业类型总计类
 */
public class TongJDataCount implements Serializable {

    private static final long serialVersionUID = -620330292637676590L;
    private String ywlx;
    private Integer zj;

    public TongJDataCount() {
    }

    public TongJDataCount(String ywlx, Integer zj) {
        this.ywlx = ywlx;
        this.zj = zj;
    }

    public String getYwlx() {
        return ywlx;
    }

    public void setYwlx(String ywlx) {
        this.ywlx = ywlx;
    }

    public Integer getZj() {
        return zj;
    }

    public void setZj(Integer zj) {
        this.zj = zj;
    }

    @Override
    public String toString() {
        return "TongJDataCount{" +
                "ywlx='" + ywlx + '\'' +
                ", zj=" + zj +
                '}';
    }
}
