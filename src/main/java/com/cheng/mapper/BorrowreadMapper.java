package com.cheng.mapper;

import com.cheng.beans.Borrowread;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-17 11:55
 */
@Mapper
public interface BorrowreadMapper {
    int InsertBorrowread(Borrowread borr);
    List<Borrowread> SelectBorrowInfo(@Param("condition") String condition,@Param("content") String content,@Param("startdate") String startdate,@Param("enddate") String enddate,@Param("code") String code, Integer pageindex, Integer pagecount);
    int SelectCountBorrowInfo(@Param("condition") String condition,@Param("content") String content,@Param("startdate") String startdate,@Param("enddate") String enddate,@Param("code") String code);
    int UpdateIsBackBySlh(@Param("slh") List<String> slh,@Param("date")String date);
    int UpdateBzBySlh(@Param("slh")String slh,@Param("bz")String bz);
}
