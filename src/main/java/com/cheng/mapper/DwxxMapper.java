package com.cheng.mapper;

import com.cheng.beans.Bmxx;
import com.cheng.beans.Dwxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/6/006
 */
@Mapper
public interface DwxxMapper {
    //根据用户code，决定能回显的单位
    List<Dwxx> SelectAllDwxx(@Param("code") String code);
    Dwxx SelectById(Integer id);
    Dwxx SelectByCode(@Param("code") String code);
    List<Dwxx> SelectInfo();
}
