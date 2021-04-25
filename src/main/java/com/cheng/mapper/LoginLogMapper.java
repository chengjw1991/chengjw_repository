package com.cheng.mapper;

import com.cheng.beans.Loginlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/14/014
 */
@Mapper
public interface LoginLogMapper {
    int InsertLoginLog(Loginlog log);
    List<Loginlog> SelectLoginLog(@Param("startdate") String startdate,@Param("enddate") String enddate,@Param("dwcode")String dwcode,@Param("username")String username, Integer pageindex,Integer pagecount);
    int SelectLoginLogByCode(@Param("startdate") String startdate,@Param("enddate") String enddate,@Param("dwcode")String dwcode,@Param("username")String username);
}
