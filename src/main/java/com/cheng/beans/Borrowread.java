package com.cheng.beans;


import java.io.Serializable;

public class Borrowread implements Serializable {

  private static final long serialVersionUID = 4416536546140426349L;
  private Integer id;
  private String borrowname;//借阅人
  private String username;//借出操作人用户
  private String borrowdate; //借出日期
  private String backtime; //约定归还日期
  private Integer isback;//是否归还，0表示已经归还，1表示还在出借状态
  private String slh;//受理号
  private String tyshxydm;//统一社会信用代码
  private String qymc;//企业名称
  private String bz;//备注
  private String code; //档案单位代码
  private String actualbackdate; //实际归还日期

  public Borrowread() {
  }

  public Borrowread(Integer id, String borrowname, String username, String borrowdate, String backtime, Integer isback, String slh, String tyshxydm, String qymc,String bz,String code,String actualbackdate) {
    this.id = id;
    this.borrowname = borrowname;
    this.username = username;
    this.borrowdate = borrowdate;
    this.backtime = backtime;
    this.isback = isback;
    this.slh = slh;
    this.tyshxydm = tyshxydm;
    this.qymc = qymc;
    this.bz = bz;
    this.code = code;
    this.actualbackdate = actualbackdate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getBorrowname() {
    return borrowname;
  }

  public void setBorrowname(String borrowname) {
    this.borrowname = borrowname;
  }


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getBorrowdate() {
    return borrowdate;
  }

  public void setBorrowdate(String borrowdate) {
    this.borrowdate = borrowdate;
  }


  public String getBacktime() {
    return backtime;
  }

  public void setBacktime(String backtime) {
    this.backtime = backtime;
  }


  public Integer getIsback() {
    return isback;
  }

  public void setIsback(Integer isback) {
    this.isback = isback;
  }


  public String getSlh() {
    return slh;
  }

  public void setSlh(String slh) {
    this.slh = slh;
  }


  public String getTyshxydm() {
    return tyshxydm;
  }

  public void setTyshxydm(String tyshxydm) {
    this.tyshxydm = tyshxydm;
  }


  public String getQymc() {
    return qymc;
  }

  public void setQymc(String qymc) {
    this.qymc = qymc;
  }

  public String getBz() {
    return bz;
  }

  public void setBz(String bz) {
    this.bz = bz;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getActualbackdate() {
    return actualbackdate;
  }

  public void setActualbackdate(String actualbackdate) {
    this.actualbackdate = actualbackdate;
  }

  @Override
  public String toString() {
    return "Borrowread{" +
            "id=" + id +
            ", borrowname='" + borrowname + '\'' +
            ", username='" + username + '\'' +
            ", borrowdate='" + borrowdate + '\'' +
            ", backtime='" + backtime + '\'' +
            ", isback=" + isback +
            ", slh='" + slh + '\'' +
            ", tyshxydm='" + tyshxydm + '\'' +
            ", qymc='" + qymc + '\'' +
            ", bz='" + bz + '\'' +
            ", code='" + code + '\'' +
            ", actualbackdate='" + actualbackdate + '\'' +
            '}';
  }
}
