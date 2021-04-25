package com.cheng.mapper;

import com.cheng.beans.Qyallinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-03 10:03
 */
@Mapper
public interface QyallinfoMapper {
    List<Qyallinfo> SelectQyallinfo(@Param("condition")String condition,@Param("content")String content,@Param("code")String code);
    Qyallinfo SelectInfoByTyshxydm(@Param("tyshxydm") String tyshxydm);
}
