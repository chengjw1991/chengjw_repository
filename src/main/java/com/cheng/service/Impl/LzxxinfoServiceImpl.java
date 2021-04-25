package com.cheng.service.Impl;

import com.cheng.beans.Lzxxinfo;
import com.cheng.mapper.LzxxinfoMapper;
import com.cheng.service.LzxxinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-05 17:23
 */
@Service("LzxxinfoServiceImpl")
public class LzxxinfoServiceImpl implements LzxxinfoService {
    @Autowired
    private LzxxinfoMapper mapper;
    @Override
    public List<Lzxxinfo> SelectLzxxBySlh(String slh) {
        return mapper.SelectLzxxBySlh(slh);
    }
}
