package com.cheng.beans;


import java.io.Serializable;

public class Loginlog implements Serializable {

  private static final long serialVersionUID = -9021612152869176216L;
  private Integer id;
  private String username;
  private String realname;
  private String dwname;
  private String dwcode;
  private String bmname;
  private String clientip;
  private String userid;
  private String date;

  public Loginlog() {
  }

  public Loginlog(Integer id, String username, String realname, String dwname, String dwcode, String bmname, String clientip, String userid, String date) {
    this.id = id;
    this.username = username;
    this.realname = realname;
    this.dwname = dwname;
    this.dwcode = dwcode;
    this.bmname = bmname;
    this.clientip = clientip;
    this.userid = userid;
    this.date = date;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRealname() {
    return realname;
  }

  public void setRealname(String realname) {
    this.realname = realname;
  }

  public String getDwname() {
    return dwname;
  }

  public void setDwname(String dwname) {
    this.dwname = dwname;
  }

  public String getBmname() {
    return bmname;
  }

  public void setBmname(String bmname) {
    this.bmname = bmname;
  }

  public String getClientip() {
    return clientip;
  }

  public void setClientip(String clientip) {
    this.clientip = clientip;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getDwcode() {
    return dwcode;
  }

  public void setDwcode(String dwcode) {
    this.dwcode = dwcode;
  }

  @Override
  public String toString() {
    return "Loginlog{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", realname='" + realname + '\'' +
            ", dwname='" + dwname + '\'' +
            ", dwcode='" + dwcode + '\'' +
            ", bmname='" + bmname + '\'' +
            ", clientip='" + clientip + '\'' +
            ", userid='" + userid + '\'' +
            ", date='" + date + '\'' +
            '}';
  }
}
