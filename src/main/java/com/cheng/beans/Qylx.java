package com.cheng.beans;


import java.io.Serializable;

public class Qylx implements Serializable {

  private static final long serialVersionUID = -8095792802579107322L;
  private Integer id;
  private String type;
  private Integer del;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public Qylx() {
  }

  public Qylx(Integer id, String type, Integer del) {
    this.id = id;
    this.type = type;
    this.del = del;
  }

  @Override
  public String toString() {
    return "Qylx{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", del=" + del +
            '}';
  }
}
