package com.cheng.service.Impl;

import com.cheng.beans.Companyallinfo;
import com.cheng.mapper.CompanyallinfoMapper;
import com.cheng.service.CompanyallinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author ChengJW
 * 2020/12/18/018
 */
@Service("CompanyallinfoServiceImpl")
public class CompanyallinfoServiceImpl implements CompanyallinfoService {

    @Autowired
    private CompanyallinfoMapper mapper;


    @Override
    public List<Companyallinfo> SelectAllCompanyInfo(String code,String condition, String content, String startdate, String enddate,Integer pageindex,Integer pagecount) {
        return mapper.SelectAllCompanyInfo(code,condition, content, startdate, enddate,pageindex,pagecount);
    }

    @Override
    public int SelectCountAllinfo(String code,String condition, String content, String startdate, String enddate) {
        return mapper.SelectCountAllinfo(code,condition, content, startdate, enddate);
    }

    @Override
    public List<Companyallinfo> SelectAllCompanyInfoByCondition(String code, String condition, String content, Integer pageindex, Integer pagecount) {
        return mapper.SelectAllCompanyInfoByCondition(code, condition, content, pageindex, pagecount);
    }

    @Override
    public int SelectCountAllinfoByCondition(String code, String condition, String content) {
        return mapper.SelectCountAllinfoByCondition(code, condition, content);
    }

    @Override
    public List<Companyallinfo> SelectCompanyInfoBySlzt(String code, String slzt, String slrbm, String startdate, String enddate, Integer pageindex, Integer pagecount) {
        return mapper.SelectCompanyInfoBySlzt(code, slzt, slrbm, startdate, enddate, pageindex, pagecount);
    }

    @Override
    public int SelectCountBySlzt(String code, String slzt, String slrbm, String startdate, String enddate) {
        return mapper.SelectCountBySlzt(code, slzt, slrbm, startdate, enddate);
    }

    @Override
    public Companyallinfo SelectInfoBySlh(String slh, String code,String slzt1,String slzt2) {
        return mapper.SelectInfoBySlh(slh,code,slzt1,slzt2);
    }

    @Override
    public List<Companyallinfo> SelectInfoOfYFP(String code, String condition, String content, String startdate, String enddate, Integer pageindex, Integer pagecount) {
        return mapper.SelectInfoOfYFP(code, condition, content, startdate, enddate, pageindex, pagecount);
    }

    @Override
    public int SelectCountInfoOfYFP(String code, String condition, String content, String startdate, String enddate) {
        return mapper.SelectCountInfoOfYFP(code, condition, content, startdate, enddate);
    }

    @Override
    public List<Companyallinfo> SelectInfoByTyshxydm(String tyshxydm) {
        return mapper.SelectInfoByTyshxydm(tyshxydm);
    }
}
