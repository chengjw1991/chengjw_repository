package com.cheng.beans;

import java.io.Serializable;

/**
 * @Author ChengJW
 * 2020/12/12/012
 * 主界面上的头像，角色名称，用户名回显
 */
public class indexVO implements Serializable {
    private static final long serialVersionUID = -1474084373628970537L;
    private String username;
    private String prifilephoto;
    private String rolename;

    public indexVO() {
    }

    public indexVO(String username, String prifilephoto, String rolename) {
        this.username = username;
        this.prifilephoto = prifilephoto;
        this.rolename = rolename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrifilephoto() {
        return prifilephoto;
    }

    public void setPrifilephoto(String prifilephoto) {
        this.prifilephoto = prifilephoto;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    @Override
    public String toString() {
        return "indexVO{" +
                "username='" + username + '\'' +
                ", prifilephoto='" + prifilephoto + '\'' +
                ", rolename='" + rolename + '\'' +
                '}';
    }
}
