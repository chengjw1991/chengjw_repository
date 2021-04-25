package com.cheng.beans;


import java.io.Serializable;

public class Permission implements Serializable {

  private static final long serialVersionUID = -6547464404701092403L;
  private Integer id;
  private String name;
  private String type;
  private String code;
  private Integer del;
  private String url;
  private String parentname;
  private String permname;
  private Integer permid;

  public Permission() {
  }

  public Permission(Integer id, String name, String type, String code, Integer del, String url, String parentname, String permname, Integer permid) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.code = code;
    this.del = del;
    this.url = url;
    this.parentname = parentname;
    this.permname = permname;
    this.permid = permid;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public Integer getDel() {
    return del;
  }

  public void setDel(Integer del) {
    this.del = del;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getParentname() {
    return parentname;
  }

  public void setParentname(String parentname) {
    this.parentname = parentname;
  }

  public Integer getPermid() {
    return permid;
  }

  public void setPermid(Integer permid) {
    this.permid = permid;
  }

  public String getPermname() {
    return permname;
  }

  public void setPermname(String permname) {
    this.permname = permname;
  }

  @Override
  public String toString() {
    return "Permission{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", code='" + code + '\'' +
            ", del=" + del +
            ", url='" + url + '\'' +
            ", parentname='" + parentname + '\'' +
            ", permname='" + permname + '\'' +
            ", permid=" + permid +
            '}';
  }
}
