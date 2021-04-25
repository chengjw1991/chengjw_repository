package com.cheng.beans;

import java.io.Serializable;

/**
 * 档案流转过程类
 */

public class Lzxx implements Serializable {

  private static final long serialVersionUID = 8576612029723279564L;
  private Integer id;  //id
  private String slh; //受理号
  private String qymc; //企业名称
  private String yh; // 操作用户名称
  private String code; //单位代码
  private String date;  //日期
  private String bz; // 操作步骤

  public Lzxx() {
  }

  public Lzxx(Integer id, String slh, String qymc, String yh, String code, String date, String bz) {
    this.id = id;
    this.slh = slh;
    this.qymc = qymc;
    this.yh = yh;
    this.code = code;
    this.date = date;
    this.bz = bz;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getSlh() {
    return slh;
  }

  public void setSlh(String slh) {
    this.slh = slh;
  }


  public String getQymc() {
    return qymc;
  }

  public void setQymc(String qymc) {
    this.qymc = qymc;
  }


  public String getYh() {
    return yh;
  }

  public void setYh(String yh) {
    this.yh = yh;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }


  public String getBz() {
    return bz;
  }

  public void setBz(String bz) {
    this.bz = bz;
  }

  @Override
  public String toString() {
    return "Lzxx{" +
            "id=" + id +
            ", slh='" + slh + '\'' +
            ", qymc='" + qymc + '\'' +
            ", yh='" + yh + '\'' +
            ", code='" + code + '\'' +
            ", date='" + date + '\'' +
            ", bz='" + bz + '\'' +
            '}';
  }
}
