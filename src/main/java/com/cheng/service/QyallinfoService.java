package com.cheng.service;

import com.cheng.beans.Qyallinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-06 10:02
 */
public interface QyallinfoService {
    List<Qyallinfo> SelectQyallinfo(String condition,String content,String code);
}
