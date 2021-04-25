package com.cheng.mapper;

import com.cheng.beans.Bmxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-03 10:03
 */
@Mapper
public interface BmxxMapper {
    Bmxx SelectBmxxtById(Integer id);
    List<Bmxx> SelectAllBmxx(@Param("code") String code);
    List<Bmxx> SelectBmxxByDwcode(@Param("dwcode")String dwcode);
    List<Bmxx> SelectBmxxByPage(@Param("bmcode") String bmcode,Integer pageindex,Integer pagecount);
    int SelectCountBmxx(@Param("bmcode") String bmcode);
    int UpdateBmxxDelOn(Integer id);
    int UpdateBmxxDelOff(Integer id);
    int UpdateBmxxById(Bmxx bmxx);
    int InsertBmxx(Bmxx bmxx);
}
