package com.cheng.beans;


import java.io.Serializable;

public class Companyallinfo implements Serializable {

  private static final long serialVersionUID = 7489348548640608379L;
  private Integer id;
  private String kh;
  private String slr;
  private String slh;
  private String ywlx;
  private String slrq;
  private Integer slys;
  private String qymc;
  private String tyshxydm;
  private String zch;
  private String qylx;
  private String gxjg;
  private String ztzt;
  private String ewm;
  private String code;
  private Integer del;
  private String clrq;
  private String slzt;
  private String dwjc;
  private String slrbm;
  private Integer isborrow;
  private Integer yfp;
  private Integer zrdd;
  private Integer qyid;

  public Companyallinfo() {
  }

  public Companyallinfo(Integer id, String kh, String slr, String slh, String ywlx, String slrq, Integer slys, String qymc, String tyshxydm, String zch, String qylx, String gxjg, String ztzt, String ewm,String code,Integer del,String clrq,String slzt,String dwjc,String slrbm,Integer isborrow,Integer yfp,Integer zrdd,Integer qyid) {
    this.id = id;
    this.kh = kh;
    this.slr = slr;
    this.slh = slh;
    this.ywlx = ywlx;
    this.slrq = slrq;
    this.slys = slys;
    this.qymc = qymc;
    this.tyshxydm = tyshxydm;
    this.zch = zch;
    this.qylx = qylx;
    this.gxjg = gxjg;
    this.ztzt = ztzt;
    this.ewm = ewm;
    this.code = code;
    this.del = del;
    this.clrq = clrq;
    this.slzt = slzt;
    this.dwjc = dwjc;
    this.slrbm = slrbm;
    this.isborrow = isborrow;
    this.yfp = yfp;
    this.zrdd = zrdd;
    this.qyid =qyid;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getKh() {
    return kh;
  }

  public void setKh(String kh) {
    this.kh = kh;
  }


  public String getSlr() {
    return slr;
  }

  public void setSlr(String slr) {
    this.slr = slr;
  }


  public String getSlh() {
    return slh;
  }

  public void setSlh(String slh) {
    this.slh = slh;
  }


  public String getYwlx() {
    return ywlx;
  }

  public void setYwlx(String ywlx) {
    this.ywlx = ywlx;
  }


  public String getSlrq() {
    return slrq;
  }

  public void setSlrq(String slrq) {
    this.slrq = slrq;
  }


  public Integer getSlys() {
    return slys;
  }

  public void setSlys(Integer slys) {
    this.slys = slys;
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


  public String getQylx() {
    return qylx;
  }

  public void setQylx(String qylx) {
    this.qylx = qylx;
  }


  public String getGxjg() {
    return gxjg;
  }

  public void setGxjg(String gxjg) {
    this.gxjg = gxjg;
  }


  public String getZtzt() {
    return ztzt;
  }

  public void setZtzt(String ztzt) {
    this.ztzt = ztzt;
  }


  public String getEwm() {
    return ewm;
  }

  public void setEwm(String ewm) {
    this.ewm = ewm;
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

  public String getClrq() {
    return clrq;
  }

  public void setClrq(String clrq) {
    this.clrq = clrq;
  }

  public String getSlzt() {
    return slzt;
  }

  public void setSlzt(String slzt) {
    this.slzt = slzt;
  }

  public String getDwjc() {
    return dwjc;
  }

  public void setDwjc(String dwjc) {
    this.dwjc = dwjc;
  }

  public String getSlrbm() {
    return slrbm;
  }

  public void setSlrbm(String slrbm) {
    this.slrbm = slrbm;
  }

  public Integer getIsborrow() {
    return isborrow;
  }

  public void setIsborrow(Integer isborrow) {
    this.isborrow = isborrow;
  }

  public Integer getYfp() {
    return yfp;
  }

  public void setYfp(Integer yfp) {
    this.yfp = yfp;
  }

  public Integer getZrdd() {
    return zrdd;
  }

  public void setZrdd(Integer zrdd) {
    this.zrdd = zrdd;
  }

  public Integer getQyid() {
    return qyid;
  }

  public void setQyid(Integer qyid) {
    this.qyid = qyid;
  }

  @Override
  public String toString() {
    return "Companyallinfo{" +
            "id=" + id +
            ", kh='" + kh + '\'' +
            ", slr='" + slr + '\'' +
            ", slh='" + slh + '\'' +
            ", ywlx='" + ywlx + '\'' +
            ", slrq='" + slrq + '\'' +
            ", slys=" + slys +
            ", qymc='" + qymc + '\'' +
            ", tyshxydm='" + tyshxydm + '\'' +
            ", zch='" + zch + '\'' +
            ", qylx='" + qylx + '\'' +
            ", gxjg='" + gxjg + '\'' +
            ", ztzt='" + ztzt + '\'' +
            ", ewm='" + ewm + '\'' +
            ", code='" + code + '\'' +
            ", del=" + del +
            ", clrq='" + clrq + '\'' +
            ", slzt='" + slzt + '\'' +
            ", dwjc='" + dwjc + '\'' +
            ", slrbm='" + slrbm + '\'' +
            ", isborrow=" + isborrow +
            ", yfp=" + yfp +
            ", zrdd=" + zrdd +
            ", qyid=" + qyid +
            '}';
  }
}
