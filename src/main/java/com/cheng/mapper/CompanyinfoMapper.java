package com.cheng.mapper;

import com.cheng.beans.Companyinfo;
import com.cheng.beans.QRData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author ChengJW
 * 2020/11/29/029
 */
@Mapper
public interface CompanyinfoMapper {

    Companyinfo SelectInfoBySlh(@Param("qyzlx") String qyzlx,@Param("slh") String slh,@Param("code")String code);
    Companyinfo SelectCompanyInfoBySlh(@Param("slh")String slh);
    int UpdateCompanyIsBorrow(@Param("slh") String slh,Integer isborrow);
    int UpdateCompanyinfo(@Param("slh") String slh,Integer slys);

    int DeleteCompanyinfo(List<String> slh);
    int UpdateCompanyIsBorrowBySlh(List<String> slh);

    int UpateCompanyInfoOfCodeAndDel(@Param("tyshxydm")List<String> tyshxydm,String dwcode,Integer del);
    int UpateInfoOfCodeAndDel(@Param("list")List<QRData> list);
}
