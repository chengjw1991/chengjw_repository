package com.cheng.beans;

import java.io.Serializable;

/**
 * @author chengjw
 * @date 2020-12-29 15:08
 * 自定义统计程序返回值类
 */
public class TongJiData implements Serializable {
    private static final long serialVersionUID = 7162179564742880171L;
    private String qylx;
    private String ysl;
    private String yjs;
    private String szhz;
    private String yszh;
    private String ck;
    private String rk;
    private String qr;
    private String qc;
    private Integer count;

    public TongJiData() {
    }

    public String getQylx() {
        return qylx;
    }

    public void setQylx(String qylx) {
        this.qylx = qylx;
    }

    public String getYsl() {
        return ysl;
    }

    public void setYsl(String ysl) {
        this.ysl = ysl;
    }

    public String getYjs() {
        return yjs;
    }

    public void setYjs(String yjs) {
        this.yjs = yjs;
    }

    public String getSzhz() {
        return szhz;
    }

    public void setSzhz(String szhz) {
        this.szhz = szhz;
    }

    public String getYszh() {
        return yszh;
    }

    public void setYszh(String yszh) {
        this.yszh = yszh;
    }

    public String getCk() {
        return ck;
    }

    public void setCk(String ck) {
        this.ck = ck;
    }

    public String getRk() {
        return rk;
    }

    public void setRk(String rk) {
        this.rk = rk;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getQc() {
        return qc;
    }

    public void setQc(String qc) {
        this.qc = qc;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TongJiData{" +
                "qylx='" + qylx + '\'' +
                ", ysl='" + ysl + '\'' +
                ", yjs='" + yjs + '\'' +
                ", szhz='" + szhz + '\'' +
                ", yszh='" + yszh + '\'' +
                ", ck='" + ck + '\'' +
                ", rk='" + rk + '\'' +
                ", qr='" + qr + '\'' +
                ", qc='" + qc + '\'' +
                ", count=" + count +
                '}';
    }
}
