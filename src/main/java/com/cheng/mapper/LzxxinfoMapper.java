package com.cheng.mapper;

import com.cheng.beans.Lzxxinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-05 17:18
 */
@Mapper
public interface LzxxinfoMapper {

    List<Lzxxinfo> SelectLzxxBySlh(@Param("slh")String slh);
}
