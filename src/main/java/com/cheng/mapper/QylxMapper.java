package com.cheng.mapper;

import com.cheng.beans.Dwxx;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/6/006
 */
@Mapper
public interface QylxMapper {
  List<String> SelectAllType();
}
