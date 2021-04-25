package com.cheng.beans;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/13/013
 * 修改用户信息时，回显给前端的信息类
 */
public class userinfoadmVO {
    private Userallinfo info;
    private Integer roleid;
    private List<Integer> perms;

    public userinfoadmVO() {
    }

    public userinfoadmVO(Userallinfo info, Integer roleid, List<Integer> perms) {
        this.info = info;
        this.roleid = roleid;
        this.perms = perms;
    }

    public Userallinfo getInfo() {
        return info;
    }

    public void setInfo(Userallinfo info) {
        this.info = info;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public List<Integer> getPerms() {
        return perms;
    }

    public void setPerms(List<Integer> perms) {
        this.perms = perms;
    }

    @Override
    public String toString() {
        return "userinfoadmVO{" +
                "info=" + info +
                ", roleid=" + roleid +
                ", perms=" + perms +
                '}';
    }
}
