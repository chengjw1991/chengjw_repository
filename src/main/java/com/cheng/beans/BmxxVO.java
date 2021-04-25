package com.cheng.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/13/013
 */
public class BmxxVO implements Serializable {
    private static final long serialVersionUID = 1506764711024964031L;
    private Integer sumcount;//数据总条数
    private Integer pagecurr;//当前页数
    private Integer pagecount;//每页的行数
    private List<Bmxx> bmxx;//数据集合

    public BmxxVO() {
    }

    public BmxxVO(Integer sumcount, Integer pagecurr, Integer pagecount, List<Bmxx> bmxx) {
        this.sumcount = sumcount;
        this.pagecurr = pagecurr;
        this.pagecount = pagecount;
        this.bmxx = bmxx;
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

    public List<Bmxx> getBmxx() {
        return bmxx;
    }

    public void setBmxx(List<Bmxx> bmxx) {
        this.bmxx = bmxx;
    }

    @Override
    public String toString() {
        return "BmxxVO{" +
                "sumcount=" + sumcount +
                ", pagecurr=" + pagecurr +
                ", pagecount=" + pagecount +
                ", bmxx=" + bmxx +
                '}';
    }
}
