package com.cheng.service.Impl;

import com.cheng.beans.Operationlog;
import com.cheng.mapper.OperationLogMapper;
import com.cheng.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-15 15:09
 */
@Service("OperationLogServiceImpl")
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper mapper;

    @Override
    public int InsertLog(Operationlog log) {
        return mapper.InsertLog(log);
    }

    @Override
    public List<Operationlog> SelectLog(String startdate, String enddate, String dwcode, String username, Integer pageindex, Integer pagecount) {
        return mapper.SelectLog(startdate, enddate, dwcode, username, pageindex, pagecount);
    }

    @Override
    public int SelectCountLog(String startdate, String enddate, String dwcode, String username) {
        return mapper.SelectCountLog(startdate, enddate, dwcode, username);
    }
}
