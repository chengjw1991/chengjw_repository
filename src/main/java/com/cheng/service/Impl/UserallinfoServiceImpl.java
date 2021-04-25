package com.cheng.service.Impl;

import com.cheng.beans.Userallinfo;
import com.cheng.mapper.UserallinfoMapper;
import com.cheng.service.UserallinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-10 17:18
 */
@Service("UserallinfoServiceImpl")
public class UserallinfoServiceImpl implements UserallinfoService {
    @Autowired
    private UserallinfoMapper mapper;

    @Override
    public List<Userallinfo> SelectAllInfo(String dwcode, String bmname, String realname, Integer pageindex, Integer pagecount) {
        return mapper.SelectAllInfo(dwcode,bmname,realname,pageindex,pagecount);
    }

    @Override
    public int SelectCountInfo(String dwcode, String bmname, String realname) {
        return mapper.SelectCountInfo(dwcode,bmname,realname);
    }

    @Override
    public Userallinfo SelectUserInfoById(Integer id) {
        return mapper.SelectUserInfoById(id);
    }
}
