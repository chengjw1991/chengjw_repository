package com.cheng.service;

import com.cheng.beans.Bmxx;
import com.cheng.beans.Dwxx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/6/006
 */
public interface DwxxService {
    List<Dwxx> SelectAllDwxx(String code);
    Dwxx SelectById(Integer id);
    Dwxx SelectByCode(String code);
    List<Dwxx> SelectInfo();
}
