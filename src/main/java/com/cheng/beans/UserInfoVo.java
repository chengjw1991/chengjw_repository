package com.cheng.beans;

import java.io.Serializable;

/**
 * @author chengjw
 * @date 2020-12-03 10:17
 * 查看用户信息时返回给前端的信息
 */
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = -7689536636590532099L;
    private String username ;
    private String bmname;
    private String dwname;
    private String sex;
    private String phone;
    private String registdate;
    private String prifilephoto;

    public UserInfoVo() {
    }

    public UserInfoVo(String username, String bmname, String dwname, String sex, String phone, String registdate, String prifilephoto) {
        this.username = username;
        this.bmname = bmname;
        this.dwname = dwname;
        this.sex = sex;
        this.phone = phone;
        this.registdate = registdate;
        this.prifilephoto = prifilephoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBmname() {
        return bmname;
    }

    public void setBmname(String bmname) {
        this.bmname = bmname;
    }

    public String getDwname() {
        return dwname;
    }

    public void setDwname(String dwname) {
        this.dwname = dwname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegistdate() {
        return registdate;
    }

    public void setRegistdate(String registdate) {
        this.registdate = registdate;
    }

    public String getPrifilephoto() {
        return prifilephoto;
    }

    public void setPrifilephoto(String prifilephoto) {
        this.prifilephoto = prifilephoto;
    }

    @Override
    public String toString() {
        return "UserInfoVo{" +
                "username='" + username + '\'' +
                ", bmname='" + bmname + '\'' +
                ", dwname='" + dwname + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", registdate='" + registdate + '\'' +
                ", prifilephoto='" + prifilephoto + '\'' +
                '}';
    }
}
