package com.cheng.service;

import com.cheng.beans.Borrowread;
import com.cheng.utils.data.DataResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-17 11:59
 */
public interface BorrowreadService{
        int InsertBorrowread(Borrowread borr);
        List<Borrowread> SelectBorrowInfo( String condition, String content,String startdate, String enddate,  String code, Integer pageindex, Integer pagecount);
        int SelectCountBorrowInfo(String condition,String content, String startdate,String enddate,String code);
        DataResult InsertBorrowInfo(String jyr, String ghrq, String jyrq, String slh, String tyshxydm, String qymc, String bz, String code);
        //归还档案，1、修改companyinfo表里的isborrow字段，2、修改 borrowread表里的isback字段
        DataResult UpdateIsBackBySlh(List<String> slh);
        //修改备注, 1、修改borrowread表里的bz字段
        DataResult UpdateBzBySlh(String slh,String bz);
}
