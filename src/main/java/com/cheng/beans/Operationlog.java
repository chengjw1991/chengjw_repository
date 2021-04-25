package com.cheng.beans;


import java.io.Serializable;

public class Operationlog implements Serializable {

  private static final long serialVersionUID = 1540961667744879234L;
  private Integer id;
  private String username;
  private String realname;
  private String clientip;
  private String classname;
  private String fangfaname;
  private String operationdate;
  private String status;
  private String dwcode;
  private String params;

  public Operationlog() {
  }

  public Operationlog(Integer id, String username, String realname, String clientip, String classname, String fangfaname, String operationdate, String status, String dwcode, String params) {
    this.id = id;
    this.username = username;
    this.realname = realname;
    this.clientip = clientip;
    this.classname = classname;
    this.fangfaname = fangfaname;
    this.operationdate = operationdate;
    this.status = status;
    this.dwcode = dwcode;
    this.params = params;
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


  public String getClientip() {
    return clientip;
  }

  public void setClientip(String clientip) {
    this.clientip = clientip;
  }


  public String getClassname() {
    return classname;
  }

  public void setClassname(String classname) {
    this.classname = classname;
  }


  public String getFangfaname() {
    return fangfaname;
  }

  public void setFangfaname(String fangfaname) {
    this.fangfaname = fangfaname;
  }


  public String getOperationdate() {
    return operationdate;
  }

  public void setOperationdate(String operationdate) {
    this.operationdate = operationdate;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDwcode() {
    return dwcode;
  }

  public void setDwcode(String dwcode) {
    this.dwcode = dwcode;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  @Override
  public String toString() {
    return "Operationlog{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", realname='" + realname + '\'' +
            ", clientip='" + clientip + '\'' +
            ", classname='" + classname + '\'' +
            ", fangfaname='" + fangfaname + '\'' +
            ", operationdate='" + operationdate + '\'' +
            ", status='" + status + '\'' +
            ", dwcode='" + dwcode + '\'' +
            ", params='" + params + '\'' +
            '}';
  }
}
