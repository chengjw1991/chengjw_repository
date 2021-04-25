package com.cheng.service;

import com.cheng.beans.Qrqc;
import com.cheng.utils.data.DataResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-06 17:16
 */
public interface QrqcService {
    //迁出
    DataResult ArchivesQC(List<String> tyshxydm,String dwcode);
    //迁出
    DataResult ArchivesQR(List<String> tyshxydm);
    List<Qrqc> SelectInfoByConditon(String condition,String content,String code);
}
