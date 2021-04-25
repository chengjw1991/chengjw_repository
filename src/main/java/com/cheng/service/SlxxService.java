package com.cheng.service;

import com.cheng.beans.Slxx;
import com.cheng.utils.data.DataResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/16/016
 */
public interface SlxxService {
    int InsertSlxx(Slxx slxx);
    Slxx SelectSlxx( String slh);
    int UpdateSlxx(String slh,String kh);
    int UpdateSlxxOfslzt(List<String> slh,Integer slzt);
    //统计
    DataResult SelectCount(String code,String startdate,String enddate);
    int UpdateSLRQ(String slh,String slrq);
    int UpdateYfp(List<String> slh,Integer yfp);
    int UpdateInfoOfZrdd(List<String> slh);
}
