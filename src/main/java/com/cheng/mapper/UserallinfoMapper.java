package com.cheng.mapper;

import com.cheng.beans.UserInfo;
import com.cheng.beans.Userallinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-10 17:14
 */
@Mapper
public interface UserallinfoMapper {
    List<Userallinfo> SelectAllInfo(@Param("dwcode") String dwcode,@Param("bmname") String bmname,@Param("realname") String realname,Integer pageindex,Integer pagecount);
    int SelectCountInfo(@Param("dwcode") String dwcode,@Param("bmname") String bmname,@Param("realname") String realname);
    Userallinfo SelectUserInfoById(Integer id);
}
