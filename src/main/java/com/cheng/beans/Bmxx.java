package com.cheng.beans;


import java.io.Serializable;

public class Bmxx implements Serializable {

  private static final long serialVersionUID = -1778343084012936592L;
  private Integer id;
  private String name;
  private String code;
  private Integer del;


  public Bmxx() {
  }

  public Bmxx(Integer id, String name, String code, Integer del) {
    this.id = id;
    this.name = name;
    this.code = code;
    this.del = del;
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

  @Override
  public String toString() {
    return "Bmxx{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", code='" + code + '\'' +
            ", del=" + del +
            '}';
  }
}
