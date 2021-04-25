package com.cheng.beans;


import java.io.Serializable;

public class Qyinfo implements Serializable {

  private static final long serialVersionUID = -8203383289663447832L;
  private Integer qyid;
  private String qymc;
  private String tyshxydm;
  private String zch;
  private String frdm;
  private String gxdw;
  private String qylx;

  public Qyinfo() {
  }

  public Qyinfo(Integer qyid, String qymc, String tyshxydm, String zch, String frdm, String gxdw, String qylx) {
    this.qyid = qyid;
    this.qymc = qymc;
    this.tyshxydm = tyshxydm;
    this.zch = zch;
    this.frdm = frdm;
    this.gxdw = gxdw;
    this.qylx = qylx;
  }

  public Integer getQyid() {
    return qyid;
  }

  public void setQyid(Integer qyid) {
    this.qyid = qyid;
  }


  public String getQymc() {
    return qymc;
  }

  public void setQymc(String qymc) {
    this.qymc = qymc;
  }


  public String getTyshxydm() {
    return tyshxydm;
  }

  public void setTyshxydm(String tyshxydm) {
    this.tyshxydm = tyshxydm;
  }


  public String getZch() {
    return zch;
  }

  public void setZch(String zch) {
    this.zch = zch;
  }


  public String getFrdm() {
    return frdm;
  }

  public void setFrdm(String frdm) {
    this.frdm = frdm;
  }


  public String getGxdw() {
    return gxdw;
  }

  public void setGxdw(String gxdw) {
    this.gxdw = gxdw;
  }


  public String getQylx() {
    return qylx;
  }

  public void setQylx(String qylx) {
    this.qylx = qylx;
  }

  @Override
  public String toString() {
    return "Qyinfo{" +
            "qyid=" + qyid +
            ", qymc='" + qymc + '\'' +
            ", tyshxydm='" + tyshxydm + '\'' +
            ", zch='" + zch + '\'' +
            ", frdm='" + frdm + '\'' +
            ", gxdw='" + gxdw + '\'' +
            ", qylx='" + qylx + '\'' +
            '}';
  }
}
