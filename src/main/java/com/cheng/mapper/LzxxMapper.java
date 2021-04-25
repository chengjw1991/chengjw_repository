package com.cheng.mapper;

import com.cheng.beans.Lzxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-03 10:03
 */
@Mapper
public interface LzxxMapper {
    int InsertLzxx(Lzxx lzxx);
    List<Lzxx> SelectLzxxBySlh(@Param("slh")String slh);
}
