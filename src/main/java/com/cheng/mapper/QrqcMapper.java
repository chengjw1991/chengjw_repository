package com.cheng.mapper;

import com.cheng.beans.Qrqc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-06 17:10
 * 迁入迁出
 */
@Mapper
public interface QrqcMapper {
    int InsertInfo(@Param("qrqc")List<Qrqc> list);
    List<Qrqc> SelectInfoByConditon(@Param("condition")String condition,@Param("content")String content,@Param("code")String code);
    Qrqc SelectInfoByTyshxydm(@Param("tyshxydm")String tyshxydm);
    int UpdateInfoByTyshxydm(@Param("tyshxydm")List<String> tyshxydm);
}
