package com.cheng.beans;


import java.io.Serializable;

public class Lzxxinfo implements Serializable {

  private static final long serialVersionUID = 1051906660637050711L;
  private Integer id;
  private String slh;
  private String qymc;
  private String yh;
  private String code;
  private String date;
  private String bz;
  private String name;

  public Lzxxinfo() {
  }

  public Lzxxinfo(Integer id, String slh, String qymc, String yh, String code, String date, String bz, String name) {
    this.id = id;
    this.slh = slh;
    this.qymc = qymc;
    this.yh = yh;
    this.code = code;
    this.date = date;
    this.bz = bz;
    this.name = name;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Lzxxinfo{" +
            "id=" + id +
            ", slh='" + slh + '\'' +
            ", qymc='" + qymc + '\'' +
            ", yh='" + yh + '\'' +
            ", code='" + code + '\'' +
            ", date='" + date + '\'' +
            ", bz='" + bz + '\'' +
            ", name='" + name + '\'' +
            '}';
  }
}
