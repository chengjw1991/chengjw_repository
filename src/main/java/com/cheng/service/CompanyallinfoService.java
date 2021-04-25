package com.cheng.service;

import com.cheng.beans.Companyallinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author ChengJW
 * 2020/12/18/018
 */
public interface CompanyallinfoService {
    List<Companyallinfo> SelectAllCompanyInfo(String code, String condition,  String content, String startdate,  String enddate,Integer pageindex,Integer pagecount);
    int SelectCountAllinfo(String code, String condition,String content, String startdate,String enddate);
    List<Companyallinfo> SelectAllCompanyInfoByCondition(String code,String condition, String content,Integer pageindex,Integer pagecount);
    int SelectCountAllinfoByCondition(String code, String condition, String content);
    List<Companyallinfo> SelectCompanyInfoBySlzt(String code,String slzt,String slrbm,String startdate,String enddate,Integer pageindex,Integer pagecount);
    int SelectCountBySlzt(String code,String slzt,String slrbm,String startdate,String enddate);
    Companyallinfo SelectInfoBySlh(String slh,String code,String slzt1,String slzt2);
    List<Companyallinfo> SelectInfoOfYFP(String code, String condition, String content,String startdate, String enddate,Integer pageindex,Integer pagecount);
    int SelectCountInfoOfYFP(String code, String condition,String content, String startdate, String enddate);
    List<Companyallinfo> SelectInfoByTyshxydm(String tyshxydm);
}
