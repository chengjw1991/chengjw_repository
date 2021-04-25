package com.cheng.service;

import com.cheng.beans.Userallinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-10 17:17
 */

public interface UserallinfoService {
    List<Userallinfo> SelectAllInfo( String dwcode,String bmname,String realname,Integer pageindex,Integer pagecount);
    int SelectCountInfo(String dwcode,String bmname, String realname);
    Userallinfo SelectUserInfoById(Integer id);

}
