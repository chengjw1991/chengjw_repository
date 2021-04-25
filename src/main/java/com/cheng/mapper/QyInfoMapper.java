package com.cheng.mapper;

import com.cheng.beans.Bmxx;
import com.cheng.beans.QRData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-03 10:03
 */
@Mapper
public interface QyInfoMapper {
   int UpdateQyinfo(@Param("tyshxydm")List<String> tyshxydm,@Param("dwcode")String dwcode);
   int UpdateQyinfoOfCode(@Param("list")List<QRData> list);
}
