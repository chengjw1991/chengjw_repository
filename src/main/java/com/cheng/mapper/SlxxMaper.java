package com.cheng.mapper;

import com.cheng.beans.Slxx;
import com.cheng.beans.TongJDataCount;
import com.cheng.beans.TongJiData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/16/016
 */
@Mapper
public interface SlxxMaper {
    int InsertSlxx(Slxx slxx);
    Slxx SelectSlxx(@Param("slh") String slh);
    int UpdateSlxx(@Param("slh")String slh,@Param("kh")String kh);
    int UpdateSlxxOfslzt(@Param("list") List<String> slh,Integer slzt);
    List<TongJiData> SelectCount(@Param("qylx") List<String> qylx, @Param("code") String code,@Param("startdate") String startdate,@Param("enddate") String enddate );//统计结果
    List<TongJDataCount> SelectCountOfSLZT(@Param("code") String code, @Param("startdate") String startdate, @Param("enddate") String enddate);
    int UpdateSLRQ(@Param("slh") String slh,@Param("slrq") String slrq);
    int UpdateYfp(@Param("slh")List<String> slh,Integer yfp);
    int UpdateInfoOfZrdd(@Param("slh")List<String> slh);
}
