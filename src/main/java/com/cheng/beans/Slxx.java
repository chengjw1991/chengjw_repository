package com.cheng.beans;


import java.io.Serializable;

public class Slxx implements Serializable {

  private static final long serialVersionUID = 1816020436617734617L;
  private Integer id;
  private String slh;
  private String slr;
  private String hzsj;
  private String zxsj;
  private String ywlx;
  private String cywlx;
  private String kh;
  private Integer slzt;
  private String ewm;
  private String slrq;
  private Integer yfp;
  private Integer zrdd;

  public Slxx() {
  }

  public Slxx(String slh, String slr, String hzsj, String zxsj, String ywlx, String cywlx, Integer slzt, String ewm,String slrq) {
    this.slh = slh;
    this.slr = slr;
    this.hzsj = hzsj;
    this.zxsj = zxsj;
    this.ywlx = ywlx;
    this.cywlx = cywlx;
    this.slzt = slzt;
    this.ewm = ewm;
    this.slrq =slrq;
  }

  public Slxx(Integer id, String slh, String slr, String hzsj, String zxsj, String ywlx, String cywlx, String kh, Integer slzt, String ewm) {
    this.id = id;
    this.slh = slh;
    this.slr = slr;
    this.hzsj = hzsj;
    this.zxsj = zxsj;
    this.ywlx = ywlx;
    this.cywlx = cywlx;
    this.kh = kh;
    this.slzt = slzt;
    this.ewm = ewm;
  }

  public Slxx(Integer id, String slh, String slr, String hzsj, String zxsj, String ywlx, String cywlx, String kh, Integer slzt, String ewm, String slrq, Integer yfp) {
    this.id = id;
    this.slh = slh;
    this.slr = slr;
    this.hzsj = hzsj;
    this.zxsj = zxsj;
    this.ywlx = ywlx;
    this.cywlx = cywlx;
    this.kh = kh;
    this.slzt = slzt;
    this.ewm = ewm;
    this.slrq = slrq;
    this.yfp = yfp;
  }

  public Slxx(Integer id, String slh, String slr, String hzsj, String zxsj, String ywlx, String cywlx, String kh, Integer slzt, String ewm, String slrq, Integer yfp, Integer zrdd) {
    this.id = id;
    this.slh = slh;
    this.slr = slr;
    this.hzsj = hzsj;
    this.zxsj = zxsj;
    this.ywlx = ywlx;
    this.cywlx = cywlx;
    this.kh = kh;
    this.slzt = slzt;
    this.ewm = ewm;
    this.slrq = slrq;
    this.yfp = yfp;
    this.zrdd = zrdd;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getSlh() {
    return slh;
  }

  public void setSlh(String slh) {
    this.slh = slh;
  }


  public String getSlr() {
    return slr;
  }

  public void setSlr(String slr) {
    this.slr = slr;
  }


  public String getHzsj() {
    return hzsj;
  }

  public void setHzsj(String hzsj) {
    this.hzsj = hzsj;
  }


  public String getZxsj() {
    return zxsj;
  }

  public void setZxsj(String zxsj) {
    this.zxsj = zxsj;
  }


  public String getYwlx() {
    return ywlx;
  }

  public void setYwlx(String ywlx) {
    this.ywlx = ywlx;
  }


  public String getCywlx() {
    return cywlx;
  }

  public void setCywlx(String cywlx) {
    this.cywlx = cywlx;
  }


  public String getKh() {
    return kh;
  }

  public void setKh(String kh) {
    this.kh = kh;
  }


  public Integer getSlzt() {
    return slzt;
  }

  public void setSlzt(Integer slzt) {
    this.slzt = slzt;
  }


  public String getEwm() {
    return ewm;
  }

  public void setEwm(String ewm) {
    this.ewm = ewm;
  }

  public String getSlrq() {
    return slrq;
  }

  public void setSlrq(String slrq) {
    this.slrq = slrq;
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

  @Override
  public String toString() {
    return "Slxx{" +
            "id=" + id +
            ", slh='" + slh + '\'' +
            ", slr='" + slr + '\'' +
            ", hzsj='" + hzsj + '\'' +
            ", zxsj='" + zxsj + '\'' +
            ", ywlx='" + ywlx + '\'' +
            ", cywlx='" + cywlx + '\'' +
            ", kh='" + kh + '\'' +
            ", slzt=" + slzt +
            ", ewm='" + ewm + '\'' +
            ", slrq='" + slrq + '\'' +
            ", yfp=" + yfp +
            ", zrdd=" + zrdd +
            '}';
  }
}
