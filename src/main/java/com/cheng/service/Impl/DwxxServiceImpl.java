package com.cheng.service.Impl;

import com.cheng.beans.Bmxx;
import com.cheng.beans.Dwxx;
import com.cheng.mapper.DwxxMapper;
import com.cheng.service.DwxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/6/006
 */
@Service("DwxxServiceImpl")
public class DwxxServiceImpl implements DwxxService {

    @Autowired
    private DwxxMapper mapper;
    @Override
    public List<Dwxx> SelectAllDwxx(String code) {
        return mapper.SelectAllDwxx(code);
    }

    @Override
    public Dwxx SelectById(Integer id) {
        return mapper.SelectById(id);
    }

    @Override
    public Dwxx SelectByCode(String code) {
        return mapper.SelectByCode(code);
    }

    @Override
    public List<Dwxx> SelectInfo() {
        return mapper.SelectInfo();
    }
}
