package com.cheng.beans;


public class Userallinfo {

  private Integer id;
  private String username;
  private String realname;
  private String sex;
  private String dwname;
  private String dwcode;
  private String bmname;
  private String bmcode;
  private Integer bmid;
  private String phone;
  private String registdate;
  private Integer delflag;
  private String userid;
  private String rolename;
  private String prifilephoto;


  public Userallinfo() {
  }

  public Userallinfo(Integer id, String username, String realname, String sex, String dwname, String dwcode, String bmname, String bmcode, Integer bmid, String phone, String registdate, Integer delflag, String userid, String rolename) {
    this.id = id;
    this.username = username;
    this.realname = realname;
    this.sex = sex;
    this.dwname = dwname;
    this.dwcode = dwcode;
    this.bmname = bmname;
    this.bmcode = bmcode;
    this.bmid = bmid;
    this.phone = phone;
    this.registdate = registdate;
    this.delflag = delflag;
    this.userid = userid;
    this.rolename = rolename;
  }

  public Userallinfo(Integer id, String username, String realname, String sex, String dwname, String dwcode, String bmname, String bmcode, Integer bmid, String phone, String registdate, Integer delflag, String userid, String rolename, String prifilephoto) {
    this.id = id;
    this.username = username;
    this.realname = realname;
    this.sex = sex;
    this.dwname = dwname;
    this.dwcode = dwcode;
    this.bmname = bmname;
    this.bmcode = bmcode;
    this.bmid = bmid;
    this.phone = phone;
    this.registdate = registdate;
    this.delflag = delflag;
    this.userid = userid;
    this.rolename = rolename;
    this.prifilephoto = prifilephoto;
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

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getDwname() {
    return dwname;
  }

  public void setDwname(String dwname) {
    this.dwname = dwname;
  }

  public String getDwcode() {
    return dwcode;
  }

  public void setDwcode(String dwcode) {
    this.dwcode = dwcode;
  }

  public String getBmname() {
    return bmname;
  }

  public void setBmname(String bmname) {
    this.bmname = bmname;
  }

  public String getBmcode() {
    return bmcode;
  }

  public void setBmcode(String bmcode) {
    this.bmcode = bmcode;
  }

  public Integer getBmid() {
    return bmid;
  }

  public void setBmid(Integer bmid) {
    this.bmid = bmid;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getDelflag() {
    return delflag;
  }

  public void setDelflag(Integer delflag) {
    this.delflag = delflag;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getRegistdate() {
    return registdate;
  }

  public void setRegistdate(String registdate) {
    this.registdate = registdate;
  }

  public String getRolename() {
    return rolename;
  }

  public void setRolename(String rolename) {
    this.rolename = rolename;
  }

  public String getPrifilephoto() {
    return prifilephoto;
  }

  public void setPrifilephoto(String prifilephoto) {
    this.prifilephoto = prifilephoto;
  }

  @Override
  public String toString() {
    return "Userallinfo{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", realname='" + realname + '\'' +
            ", sex='" + sex + '\'' +
            ", dwname='" + dwname + '\'' +
            ", dwcode='" + dwcode + '\'' +
            ", bmname='" + bmname + '\'' +
            ", bmcode='" + bmcode + '\'' +
            ", bmid=" + bmid +
            ", phone='" + phone + '\'' +
            ", registdate='" + registdate + '\'' +
            ", delflag=" + delflag +
            ", userid='" + userid + '\'' +
            ", rolename='" + rolename + '\'' +
            ", prifilephoto='" + prifilephoto + '\'' +
            '}';
  }
}
