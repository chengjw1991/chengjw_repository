package com.cheng.service;

import com.cheng.beans.Operationlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-15 15:08
 */
public interface OperationLogService {
    int InsertLog(Operationlog log);
    List<Operationlog> SelectLog( String startdate, String enddate, String dwcode,String username, Integer pageindex, Integer pagecount);
    int SelectCountLog( String startdate, String enddate, String dwcode, String username);
}
