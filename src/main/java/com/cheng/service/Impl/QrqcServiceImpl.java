package com.cheng.service.Impl;

import com.cheng.beans.Companyallinfo;
import com.cheng.beans.QRData;
import com.cheng.beans.Qrqc;
import com.cheng.beans.Qyallinfo;
import com.cheng.mapper.*;
import com.cheng.service.QrqcService;
import com.cheng.utils.data.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chengjw
 * @date 2021-01-06 17:18
 */
@Service("QrqcServiceImpl")
public class QrqcServiceImpl implements QrqcService {

    @Autowired
    private QrqcMapper  qrqcMapper;
    @Autowired
    private CompanyallinfoMapper compmapper;
    @Autowired
    private QyallinfoMapper qyallinfoMapper;
    @Autowired
    private QyInfoMapper qyInfoMapper;
    @Autowired
    private CompanyinfoMapper companyinfoMapper;


    @Transactional
    @Override
    public DataResult ArchivesQC(List<String> tyshxydm, String dwcode) {
        if (tyshxydm.size() ==0 || StringUtils.isEmpty(dwcode)){
            return DataResult.FAILURE_NULLDATA();
        }
        //获取当前日期
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        List<Qrqc> qrqcList = new ArrayList<>();
        //根据统一社会信用代码获取企业信息
        for (String str : tyshxydm){
            Qyallinfo info = qyallinfoMapper.SelectInfoByTyshxydm(str);
            if (info == null){
                continue;
            }
            String qymc = info.getQymc();
            String tyshxydm_ = str;
            String zch = info.getZch();
            String frxm = info.getFrdm();
            String qccode = info.getGxdw();
            String qrcode = dwcode;
            String qyrq = date;
            Qrqc qrqc = new Qrqc(0,qymc,tyshxydm_,zch,frxm,qrcode,qccode,qyrq,0);
            qrqcList.add(qrqc);
        }
        //插入数据到qrqc表中
        int count = qrqcMapper.InsertInfo(qrqcList);
        if (count == 0){
            return  DataResult.FAILURE();
        }
        //修改qyinfo表中的企业单位代码
        int qyinfoCount = qyInfoMapper.UpdateQyinfo(tyshxydm,dwcode);
        //修改档案卷表companinfo中档案卷的状态del值跟单位code值
        int compCount = companyinfoMapper.UpateCompanyInfoOfCodeAndDel(tyshxydm,dwcode,1);
        return DataResult.SUCCESS();
    }

    @Transactional
    @Override
    public DataResult ArchivesQR(List<String> tyshxydm) {
        if (tyshxydm.size() == 0){
            return DataResult.FAILURE_NODATA();
        }
        //查出各个企业需要迁入的单位
        List<QRData> list = new ArrayList<>();
        for (String str : tyshxydm){
            Qrqc qrqc =qrqcMapper.SelectInfoByTyshxydm(str);
            String dwcode = qrqc.getQrcode();
            list.add(new QRData(str,dwcode));
        }
        //批量修改qrqc表中迁移状态isqr的值（0表示未迁入，1表示已经迁入）
        int count = qrqcMapper.UpdateInfoByTyshxydm(tyshxydm);
        //修改qyinfo表中的企业单位代码
        int qyinfoCount = qyInfoMapper.UpdateQyinfoOfCode(list);
        //修改档案卷表companinfo中档案卷的状态del值跟单位code值
        int compCount = companyinfoMapper.UpateInfoOfCodeAndDel(list);
        return DataResult.SUCCESS();
    }

    @Override
    public List<Qrqc> SelectInfoByConditon(String condition, String content, String code) {
        return qrqcMapper.SelectInfoByConditon(condition, content, code);
    }
}
