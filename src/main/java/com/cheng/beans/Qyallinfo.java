package com.cheng.beans;


import java.io.Serializable;

public class Qyallinfo implements Serializable {

  private static final long serialVersionUID = 5914522580672925689L;
  private Integer qyid;
  private String qymc;
  private String tyshxydm;
  private String zch;
  private String frdm;
  private String gxdw;
  private String qylx;
  private String dwmc;
  private Integer isqc;

  public Qyallinfo() {
  }

  public Qyallinfo(Integer qyid, String qymc, String tyshxydm, String zch, String frdm, String gxdw, String qylx, String dwmc,Integer isqc) {
    this.qyid = qyid;
    this.qymc = qymc;
    this.tyshxydm = tyshxydm;
    this.zch = zch;
    this.frdm = frdm;
    this.gxdw = gxdw;
    this.qylx = qylx;
    this.dwmc = dwmc;
    this.isqc = isqc;
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


  public String getDwmc() {
    return dwmc;
  }

  public void setDwmc(String dwmc) {
    this.dwmc = dwmc;
  }

  public Integer getIsqc() {
    return isqc;
  }

  public void setIsqc(Integer isqc) {
    this.isqc = isqc;
  }

  @Override
  public String toString() {
    return "Qyallinfo{" +
            "qyid=" + qyid +
            ", qymc='" + qymc + '\'' +
            ", tyshxydm='" + tyshxydm + '\'' +
            ", zch='" + zch + '\'' +
            ", frdm='" + frdm + '\'' +
            ", gxdw='" + gxdw + '\'' +
            ", qylx='" + qylx + '\'' +
            ", dwmc='" + dwmc + '\'' +
            ", isqc=" + isqc +
            '}';
  }
}
