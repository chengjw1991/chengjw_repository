package com.cheng.beans;


import java.io.Serializable;
import java.util.List;

public class UserInfo implements Serializable {

  private static final long serialVersionUID = 1783210334989330360L;
  private Integer id;
  private String userid;
  private String username;
  private String password;
  private String realname;
  private Integer departmentid;
  private String sex;
  private String phone;
  private String registdate;
  private Integer disableid;
  private Integer delflag;
  private String prifilephoto;
  private String dwcode;


  List<Roles> rolesList;
  List<Permission> permList;

  public UserInfo() {
  }

  public UserInfo(Integer id, String userid, String username, String password, String realname, Integer departmentid, String sex, String phone, String registdate, Integer disableid, Integer delflag, String prifilephoto, String dwcode) {
    this.id = id;
    this.userid = userid;
    this.username = username;
    this.password = password;
    this.realname = realname;
    this.departmentid = departmentid;
    this.sex = sex;
    this.phone = phone;
    this.registdate = registdate;
    this.disableid = disableid;
    this.delflag = delflag;
    this.prifilephoto = prifilephoto;
    this.dwcode = dwcode;
  }
  public UserInfo(Integer id, String userid, String username, String password, String realname, Integer departmentid, String sex, String phone, String registdate, Integer disableid, Integer delflag, String prifilephoto, String dwcode, List<Roles> rolesList, List<Permission> permList) {
    this.id = id;
    this.userid = userid;
    this.username = username;
    this.password = password;
    this.realname = realname;
    this.departmentid = departmentid;
    this.sex = sex;
    this.phone = phone;
    this.registdate = registdate;
    this.disableid = disableid;
    this.delflag = delflag;
    this.prifilephoto = prifilephoto;
    this.dwcode = dwcode;
    this.rolesList = rolesList;
    this.permList = permList;
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


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public String getRealname() {
    return realname;
  }

  public void setRealname(String realname) {
    this.realname = realname;
  }


  public Integer getDepartmentid() {
    return departmentid;
  }

  public void setDepartmentid(Integer departmentid) {
    this.departmentid = departmentid;
  }


  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }


  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getRegistdate() {
    return registdate;
  }

  public void setRegistdate(String registdate) {
    this.registdate = registdate;
  }


  public long getDisableid() {
    return disableid;
  }

  public void setDisableid(Integer disableid) {
    this.disableid = disableid;
  }


  public long getDelflag() {
    return delflag;
  }

  public void setDelflag(Integer delflag) {
    this.delflag = delflag;
  }


  public String getPrifilephoto() {
    return prifilephoto;
  }

  public void setPrifilephoto(String prifilephoto) {
    this.prifilephoto = prifilephoto;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public List<Roles> getRolesList() {
    return rolesList;
  }

  public void setRolesList(List<Roles> rolesList) {
    this.rolesList = rolesList;
  }

  public List<Permission> getPermList() {
    return permList;
  }

  public void setPermList(List<Permission> permList) {
    this.permList = permList;
  }

  public String getDwcode() {
    return dwcode;
  }

  public void setDwcode(String dwcode) {
    this.dwcode = dwcode;
  }

  @Override
  public String toString() {
    return "UserInfo{" +
            "id=" + id +
            ", userid='" + userid + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", realname='" + realname + '\'' +
            ", departmentid=" + departmentid +
            ", sex='" + sex + '\'' +
            ", phone='" + phone + '\'' +
            ", registdate='" + registdate + '\'' +
            ", disableid=" + disableid +
            ", delflag=" + delflag +
            ", prifilephoto='" + prifilephoto + '\'' +
            ", dwcode='" + dwcode + '\'' +
            ", rolesList=" + rolesList +
            ", permList=" + permList +
            '}';
  }
}
