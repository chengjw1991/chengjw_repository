package com.cheng.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-10 09:56
 *  用户管理查询用户信息响应类
 */
public class UserVO implements Serializable {
    private static final long serialVersionUID = -6544394210889077214L;
    private Integer sumcount;//数据总条数
    private Integer pagecurr;//当前页数
    private Integer pagecount;//每页的行数
    private List<Userallinfo> users;//数据集合

    public UserVO() {
    }

    public UserVO(Integer sumcount, Integer pagecurr, Integer pagecount, List<Userallinfo> users) {
        this.sumcount = sumcount;
        this.pagecurr = pagecurr;
        this.pagecount = pagecount;
        this.users = users;
    }

    public Integer getSumcount() {
        return sumcount;
    }

    public void setSumcount(Integer sumcount) {
        this.sumcount = sumcount;
    }

    public Integer getPagecurr() {
        return pagecurr;
    }

    public void setPagecurr(Integer pagecurr) {
        this.pagecurr = pagecurr;
    }

    public Integer getPagecount() {
        return pagecount;
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    public List<Userallinfo> getUsers() {
        return users;
    }

    public void setUsers(List<Userallinfo> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "sumcount=" + sumcount +
                ", pagecurr=" + pagecurr +
                ", pagecount=" + pagecount +
                ", users=" + users +
                '}';
    }
}
