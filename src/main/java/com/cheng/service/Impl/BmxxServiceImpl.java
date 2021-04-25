package com.cheng.service.Impl;

import com.cheng.beans.Bmxx;
import com.cheng.mapper.BmxxMapper;
import com.cheng.service.BmxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-03 10:07
 */
@Service("BmxxServiceImpl")
public class BmxxServiceImpl implements BmxxService {

    @Autowired
    private BmxxMapper mapper;


    @Override
    public Bmxx SelectBmxxById(Integer id) {
        return mapper.SelectBmxxtById(id);
    }

    @Override
    public List<Bmxx> SelectAllBmxx(String code) {
        return mapper.SelectAllBmxx(code);
    }

    @Override
    public List<Bmxx> SelectBmxxByDwcode(String dwcode) {
        return mapper.SelectBmxxByDwcode(dwcode);
    }

    @Override
    public List<Bmxx> SelectBmxxByPage(String bmcode,Integer pageindex, Integer pagecount) {
        return mapper.SelectBmxxByPage(bmcode,pageindex,pagecount);
    }

    @Override
    public int SelectCountBmxx(String bmcode) {
        return mapper.SelectCountBmxx(bmcode);
    }

    @Override
    public int UpdateBmxxDelOn(Integer id) {
        return mapper.UpdateBmxxDelOn(id);
    }

    @Override
    public int UpdateBmxxDelOff(Integer id) {
        return mapper.UpdateBmxxDelOff(id);
    }

    @Override
    public int UpdateBmxxById(Bmxx bmxx) {
        return mapper.UpdateBmxxById(bmxx);
    }

    @Override
    public int InsertBmxx(Bmxx bmxx) {
        return mapper.InsertBmxx(bmxx);
    }


}
