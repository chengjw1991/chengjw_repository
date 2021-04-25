package com.cheng.service.Impl;

import com.cheng.beans.Qyallinfo;
import com.cheng.mapper.QyallinfoMapper;
import com.cheng.service.QyallinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-06 10:03
 */
@Service("QyallinfoServiceImpl")
public class QyallinfoServiceImpl implements QyallinfoService {
    @Autowired
    private QyallinfoMapper mapper;

    @Override
    public List<Qyallinfo> SelectQyallinfo(String condition, String content, String code) {
        return mapper.SelectQyallinfo(condition,content,code);
    }
}
