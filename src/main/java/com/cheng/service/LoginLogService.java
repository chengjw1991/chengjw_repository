package com.cheng.service;

import com.cheng.beans.Loginlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/14/014
 */
public interface LoginLogService {
    int InsertLoginLog(Loginlog log);
    List<Loginlog> SelectLoginLog(String startdate,String enddate,String dwcode,String username,Integer pageindex,Integer pagecount);
    int SelectLoginLogByCode(String startdate, String enddate,String dwcode,String username);
}
