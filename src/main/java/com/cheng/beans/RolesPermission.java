package com.cheng.beans;


import java.io.Serializable;

public class RolesPermission implements Serializable {

  private static final long serialVersionUID = 8975766475705785721L;
  private Integer id;
  private Integer rid;
  private Integer pid;


  public RolesPermission() {
  }

  public RolesPermission(Integer id, Integer rid, Integer pid) {
    this.id = id;
    this.rid = rid;
    this.pid = pid;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getRid() {
    return rid;
  }

  public void setRid(Integer rid) {
    this.rid = rid;
  }

  public Integer getPid() {
    return pid;
  }

  public void setPid(Integer pid) {
    this.pid = pid;
  }

  @Override
  public String toString() {
    return "RolesPermission{" +
            "id=" + id +
            ", rid=" + rid +
            ", pid=" + pid +
            '}';
  }
}
