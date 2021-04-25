package com.cheng.mapper;

import com.cheng.beans.Operationlog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-15 14:49
 */
@Mapper
public interface OperationLogMapper {
    int InsertLog(Operationlog log);
    List<Operationlog> SelectLog(@Param("startdate") String startdate,@Param("enddate") String enddate,@Param("dwcode")String dwcode,@Param("username")String username, Integer pageindex,Integer pagecount);
    int SelectCountLog(@Param("startdate") String startdate, @Param("enddate") String enddate, @Param("dwcode")String dwcode, @Param("username")String username);
}
