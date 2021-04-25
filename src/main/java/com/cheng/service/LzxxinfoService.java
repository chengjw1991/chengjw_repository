package com.cheng.service;

import com.cheng.beans.Lzxxinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-05 17:22
 */
public interface LzxxinfoService {
    List<Lzxxinfo> SelectLzxxBySlh(String slh);
}
