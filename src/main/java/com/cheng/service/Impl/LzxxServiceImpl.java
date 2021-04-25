package com.cheng.service.Impl;

import com.cheng.beans.Lzxx;
import com.cheng.mapper.LzxxMapper;
import com.cheng.service.LzxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chengjw
 * @date 2020-12-31 13:56
 */
@Service("LzxxServiceImpl")
public class LzxxServiceImpl implements LzxxService {
    @Autowired
    private LzxxMapper mapper;

    @Override
    public int InsertLzxx(Lzxx lzxx) {
        return mapper.InsertLzxx(lzxx);
    }
}
