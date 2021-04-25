package com.cheng.service;

import com.cheng.beans.Bmxx;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-03 10:06
 */
public interface BmxxService {
    Bmxx SelectBmxxById(Integer id);
    List<Bmxx> SelectAllBmxx(String code);
    List<Bmxx> SelectBmxxByDwcode(String dwcode);
    List<Bmxx> SelectBmxxByPage(String bmcode,Integer pageindex,Integer pagecount);
    int SelectCountBmxx(String bmcode);
    int UpdateBmxxDelOn(Integer id);
    int UpdateBmxxDelOff(Integer id);
    int UpdateBmxxById(Bmxx bmxx);
    int InsertBmxx(Bmxx bmxx);

}
