package com.cheng.beans;


import java.io.Serializable;

public class Dwxx implements Serializable {

  private static final long serialVersionUID = -6518885283107402894L;
  private Integer id;
  private String name;
  private String code;
  private Integer del;

  public Dwxx() {
  }

  public Dwxx(Integer id, String name, String code, Integer del) {
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
    return "Dwxx{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", code='" + code + '\'' +
            ", del=" + del +
            '}';
  }
}
