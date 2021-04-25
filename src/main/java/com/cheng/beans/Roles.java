package com.cheng.beans;


import java.io.Serializable;
import java.util.List;

public class Roles implements Serializable {

  private static final long serialVersionUID = -4080069124780986829L;
  private Integer id;
  private String name;
  private String type;
  private Integer del;
  private String explain;
  private String code;
  private String createdate;
  private String dwmc;
  private List<Permission> perms;


  public Roles() {
  }

  public Roles(Integer id, String name, String type, Integer del, String explain, String code, String createdate, String dwmc) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.del = del;
    this.explain = explain;
    this.code = code;
    this.createdate = createdate;
    this.dwmc = dwmc;
  }

  public Roles(Integer id, String name, String type, Integer del, String explain, String code, String createdate, String dwmc, List<Permission> perms) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.del = del;
    this.explain = explain;
    this.code = code;
    this.createdate = createdate;
    this.dwmc = dwmc;
    this.perms = perms;
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


  public Integer getDel() {
    return del;
  }

  public void setDel(Integer del) {
    this.del = del;
  }


  public String getExplain() {
    return explain;
  }

  public void setExplain(String explain) {
    this.explain = explain;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public String getCreatedate() {
    return createdate;
  }

  public void setCreatedate(String cratedate) {
    this.createdate = cratedate;
  }

  public String getDwmc() {
    return dwmc;
  }

  public void setDwmc(String dwmc) {
    this.dwmc = dwmc;
  }

  public List<Permission> getPerms() {
    return perms;
  }

  public void setPerms(List<Permission> perms) {
    this.perms = perms;
  }

  @Override
  public String toString() {
    return "Roles{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", del=" + del +
            ", explain='" + explain + '\'' +
            ", code='" + code + '\'' +
            ", createdate='" + createdate + '\'' +
            ", dwmc='" + dwmc + '\'' +
            ", perms=" + perms +
            '}';
  }
}
