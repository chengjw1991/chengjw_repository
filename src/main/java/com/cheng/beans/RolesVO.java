package com.cheng.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-04 14:53
 * 查询全部角色返回的数据
 */
public class RolesVO implements Serializable {

    private static final long serialVersionUID = -4048955110770816979L;
    private Integer sumcount;//数据总条数
    private Integer pagecurr;//当前页数
    private Integer pagecount;//每页的行数
    private List<Roles> roles;//数据集合

    public RolesVO() {
    }

    public RolesVO(Integer sumcount, List<Roles> roles,Integer pagecurr,Integer pagecount) {
        this.sumcount = sumcount;
        this.roles = roles;
        this.pagecurr = pagecurr;
        this.pagecount = pagecount;
    }

    public Integer getSumcount() {
        return sumcount;
    }

    public void setSumcount(Integer sumcount) {
        this.sumcount = sumcount;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
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

    @Override
    public String toString() {
        return "RolesVO{" +
                "sumcount=" + sumcount +
                ", pagecurr=" + pagecurr +
                ", pagecount=" + pagecount +
                ", roles=" + roles +
                '}';
    }
}
