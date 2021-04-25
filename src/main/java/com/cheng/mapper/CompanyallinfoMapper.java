package com.cheng.mapper;

import com.cheng.beans.Companyallinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author ChengJW
 * 2020/12/18/018
 */
@Mapper
public interface CompanyallinfoMapper {
    List<Companyallinfo> SelectAllCompanyInfo(@Param("code")String code,@Param("condition") String condition,@Param("content") String content,@Param("startdate") String startdate,@Param("enddate") String enddate,Integer pageindex,Integer pagecount);
    int SelectCountAllinfo(@Param("code")String code,@Param("condition") String condition,@Param("content") String content,@Param("startdate") String startdate,@Param("enddate") String enddate);

    List<Companyallinfo> SelectAllCompanyInfoByCondition(@Param("code")String code,@Param("condition") String condition,@Param("content") String content,Integer pageindex,Integer pagecount);
    int SelectCountAllinfoByCondition(@Param("code")String code,@Param("condition") String condition,@Param("content") String content);

    List<Companyallinfo> SelectCompanyInfoBySlzt(@Param("code")String code,@Param("slzt")String slzt,@Param("slrbm")String slrbm,@Param("startdate")String startdate,@Param("enddate")String enddate,Integer pageindex,Integer pagecount);
    int SelectCountBySlzt(@Param("code")String code,@Param("slzt")String slzt,@Param("slrbm")String slrbm,@Param("startdate")String startdate,@Param("enddate")String enddate);

    Companyallinfo SelectInfoBySlh(@Param("slh")String slh,@Param("code")String code,@Param("slzt1") String slzt1,@Param("slzt2")String slzt2);

    List<Companyallinfo> SelectInfoOfYFP(@Param("code")String code,@Param("condition") String condition,@Param("content") String content,@Param("startdate") String startdate,@Param("enddate") String enddate,Integer pageindex,Integer pagecount);
    int SelectCountInfoOfYFP(@Param("code")String code,@Param("condition") String condition,@Param("content") String content,@Param("startdate") String startdate,@Param("enddate") String enddate);

    List<Companyallinfo> SelectInfoByTyshxydm(@Param("tyshxydm")String tyshxydm);
}
