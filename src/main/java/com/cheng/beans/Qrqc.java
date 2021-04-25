package com.cheng.beans;


import java.io.Serializable;

public class Qrqc implements Serializable {

  private static final long serialVersionUID = 6163629566251719274L;
  private Integer id;
  private String qymc;
  private String tyshxydm;
  private String zch;
  private String frxm;
  private String qrcode;
  private String qccode;
  private String qyrq;
  private Integer isqr;

  public Qrqc() {
  }

  public Qrqc(Integer id, String qymc, String tyshxydm, String zch, String frxm, String qrcode, String qccode, String qyrq,Integer isqr) {
    this.id = id;
    this.qymc = qymc;
    this.tyshxydm = tyshxydm;
    this.zch = zch;
    this.frxm = frxm;
    this.qrcode = qrcode;
    this.qccode = qccode;
    this.qyrq = qyrq;
    this.isqr = isqr;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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


  public String getFrxm() {
    return frxm;
  }

  public void setFrxm(String frxm) {
    this.frxm = frxm;
  }


  public String getQrcode() {
    return qrcode;
  }

  public void setQrcode(String qrcode) {
    this.qrcode = qrcode;
  }


  public String getQccode() {
    return qccode;
  }

  public void setQccode(String qccode) {
    this.qccode = qccode;
  }


  public String getQyrq() {
    return qyrq;
  }

  public void setQyrq(String qyrq) {
    this.qyrq = qyrq;
  }

  public Integer getIsqr() {
    return isqr;
  }

  public void setIsqr(Integer isqr) {
    this.isqr = isqr;
  }

  @Override
  public String toString() {
    return "Qrqc{" +
            "id=" + id +
            ", qymc='" + qymc + '\'' +
            ", shtyxydm='" + tyshxydm + '\'' +
            ", zch='" + zch + '\'' +
            ", frxm='" + frxm + '\'' +
            ", qrcode='" + qrcode + '\'' +
            ", qccode='" + qccode + '\'' +
            ", qyrq='" + qyrq + '\'' +
            ", isqr='" + isqr + '\'' +
            '}';
  }
}
