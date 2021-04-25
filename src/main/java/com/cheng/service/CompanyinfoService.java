package com.cheng.service;

import com.cheng.beans.Companyinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/11/29/029
 */
public interface CompanyinfoService {
    Companyinfo SelectInfoBySlh(String qyzlx,String slh,String code);
    Companyinfo SelectCompanyInfoBySlh(String slh);
    int UpdateCompanyIsBorrow(String slh,Integer isborrow);
    int UpdateCompanyinfo(String slh,Integer slys);
    int DeleteCompanyinfo(List<String> slh);
}
