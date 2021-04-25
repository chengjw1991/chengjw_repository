package com.cheng.service.Impl;

import com.cheng.beans.Borrowread;
import com.cheng.beans.Companyinfo;
import com.cheng.beans.UserInfo;
import com.cheng.mapper.BorrowreadMapper;
import com.cheng.mapper.CompanyinfoMapper;
import com.cheng.service.BorrowreadService;
import com.cheng.service.CompanyinfoService;
import com.cheng.token.JwtUtil;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-17 12:00
 */
@Service("BorrowreadServiceImpl")
public class BorrowreadServiceImpl implements BorrowreadService {

    @Autowired
    private BorrowreadMapper mapper;
    @Autowired
    private CompanyinfoMapper companyinfoMapper;
    @Resource(name = "CompanyinfoServiceImpl")
    private CompanyinfoService compservice;
    @Resource(name = "UserServiceImpl")
    private UserServiceImpl userService;


    @Override
    public int InsertBorrowread(Borrowread borr) {
        return mapper.InsertBorrowread(borr);
    }

    @Override
    public List<Borrowread> SelectBorrowInfo(String condition, String content, String startdate, String enddate, String code, Integer pageindex, Integer pagecount) {
        return mapper.SelectBorrowInfo(condition, content, startdate, enddate, code, pageindex, pagecount);
    }

    @Override
    public int SelectCountBorrowInfo(String condition, String content, String startdate, String enddate, String code) {
        return mapper.SelectCountBorrowInfo(condition, content, startdate, enddate, code);
    }
    //借阅
    @Override
    @Transactional(rollbackFor = Exception.class)
    public DataResult InsertBorrowInfo(String jyr,String ghrq,String jyrq,String slh,String tyshxydm,String qymc,String bz,String code){
        if (StringUtils.isEmpty(jyr)  || StringUtils.isEmpty(ghrq) || StringUtils.isEmpty(jyrq) || StringUtils.isEmpty(slh) || StringUtils.isEmpty(tyshxydm) || StringUtils.isEmpty(qymc)){
            return DataResult.getDataResult(CodeAndMsgImpl.FAILURE_NULLDATA);
        }
        //查看该档案是否已经借阅
        Companyinfo companyinfo = compservice.SelectCompanyInfoBySlh(slh);
        if (companyinfo.getIsborrow()==1){
            return DataResult.getDataResult(205,"该卷档案已经借出，无法再次出借！");
        }
        int companycount = compservice.UpdateCompanyIsBorrow(slh,1);
        String token = (String) SecurityUtils.getSubject().getPrincipal();
        String userid = JwtUtil.ParseUserId(token);
        UserInfo info = userService.SelectUserInfoByUserid(userid);
        String username = info.getUsername();
        Borrowread borrowread = new Borrowread(0,jyr,username,jyrq,ghrq,1,slh,tyshxydm,qymc,bz,code,"");
        int count = InsertBorrowread(borrowread);
        if(count>0){
            return DataResult.SUCCESS();
        }
        return DataResult.FAILURE();
    }

    @Override
    @Transactional
    public DataResult UpdateIsBackBySlh(List<String> slh) {
        if (slh.size() == 0){
            return DataResult.FAILURE_NULLDATA();
        }
        int companyinfocount = companyinfoMapper.UpdateCompanyIsBorrowBySlh(slh);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        int borrowreadcount = mapper.UpdateIsBackBySlh(slh,date);
        if (companyinfocount == 0 || borrowreadcount == 0){
            return DataResult.FAILURE();
        }
        return DataResult.SUCCESS();
    }

    @Override
    public DataResult UpdateBzBySlh(String slh,String bz){
        if (StringUtils.isEmpty(slh)){
            return  DataResult.FAILURE_NULLDATA();
        }
        int count = mapper.UpdateBzBySlh(slh, bz);
        if (count == 0){
            return  DataResult.FAILURE();
        }
        return DataResult.SUCCESS();
    }
}
