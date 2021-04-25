package com.cheng.service.Impl;

import com.cheng.beans.Loginlog;
import com.cheng.mapper.LoginLogMapper;
import com.cheng.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/14/014
 */
@Service("LoginLogServiceImpl")
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper mapper;

    @Override
    public int InsertLoginLog(Loginlog log) {
        return mapper.InsertLoginLog(log);
    }

    @Override
    public List<Loginlog> SelectLoginLog(String startdate, String enddate, String dwcode, String username,Integer pageindex, Integer pagecount) {
        return mapper.SelectLoginLog(startdate, enddate, dwcode,username, pageindex, pagecount);
    }

    @Override
    public int SelectLoginLogByCode(String startdate, String enddate, String dwcode,String username) {
        return mapper.SelectLoginLogByCode(startdate, enddate, dwcode,username);
    }


}
