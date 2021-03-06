package com.cheng.beans;


public class UserRoles {

  private Integer id;
  private Integer uid;
  private Integer rid;


  public UserRoles() {
  }

  public UserRoles(Integer id, Integer uid, Integer rid) {
    this.id = id;
    this.uid = uid;
    this.rid = rid;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUid() {
    return uid;
  }

  public void setUid(Integer uid) {
    this.uid = uid;
  }

  public Integer getRid() {
    return rid;
  }

  public void setRid(Integer rid) {
    this.rid = rid;
  }

  @Override
  public String toString() {
    return "UserRoles{" +
            "id=" + id +
            ", uid=" + uid +
            ", rid=" + rid +
            '}';
  }
}
