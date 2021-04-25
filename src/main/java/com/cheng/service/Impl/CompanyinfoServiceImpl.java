package com.cheng.service.Impl;

import com.cheng.beans.Companyinfo;
import com.cheng.mapper.CompanyinfoMapper;
import com.cheng.service.CompanyinfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/11/29/029
 */
@Service("CompanyinfoServiceImpl")
public class CompanyinfoServiceImpl implements CompanyinfoService {

    @Resource
    CompanyinfoMapper mapper;

    @Override
    public Companyinfo SelectInfoBySlh(String qyzlx,String slh,String code) {
        return mapper.SelectInfoBySlh(qyzlx,slh,code);
    }

    @Override
    public Companyinfo SelectCompanyInfoBySlh(String slh) {
        return mapper.SelectCompanyInfoBySlh(slh);
    }

    @Override
    public int UpdateCompanyIsBorrow(String slh, Integer isborrow) {
        return mapper.UpdateCompanyIsBorrow(slh,isborrow);
    }

    @Override
    public int UpdateCompanyinfo(String slh, Integer slys) {
        return mapper.UpdateCompanyinfo(slh,slys);
    }


    @Override
    public int DeleteCompanyinfo(List<String> slh) {
        return mapper.DeleteCompanyinfo(slh);
    }
}
