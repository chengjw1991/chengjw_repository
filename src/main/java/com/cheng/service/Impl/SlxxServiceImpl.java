package com.cheng.service.Impl;

import com.cheng.beans.Slxx;
import com.cheng.beans.TongJDataCount;
import com.cheng.beans.TongJiData;
import com.cheng.mapper.QylxMapper;
import com.cheng.mapper.SlxxMaper;
import com.cheng.service.SlxxService;
import com.cheng.utils.data.CodeAndMsgImpl;
import com.cheng.utils.data.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.Name;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/16/016
 */
@Service("SlxxServiceImpl")
public class SlxxServiceImpl implements SlxxService {

    @Autowired
    private SlxxMaper mapper;
    @Autowired
    private QylxMapper qylxMapper;

    @Override
    public int InsertSlxx(Slxx slxx) {
        return mapper.InsertSlxx(slxx);
    }

    @Override
    public Slxx SelectSlxx(String slh) {
        return mapper.SelectSlxx(slh);
    }

    @Override
    public int UpdateSlxx(String slh, String kh) {
        return mapper.UpdateSlxx(slh,kh);
    }

    @Override
    public int UpdateSlxxOfslzt(List<String> slh,Integer slzt) {
        return mapper.UpdateSlxxOfslzt(slh,slzt);
    }

    //统计
    @Override
    public DataResult SelectCount(String code, String startdate, String enddate) {
        //System.out.println(code+"----");
        List<String> qylx = qylxMapper.SelectAllType();
        if (qylx.size()==0){
            return DataResult.FAILURE_NODATA();
        }
        String startdate_ = null;
        String enddate_ = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        //从系统有数据开始算起
        if (startdate == "" && enddate == ""){
            startdate_ = "2020-11-01";
            //startdate_ = "2020-01-01 00:00:00";
            enddate_ = date;
        }else if (startdate == "" && enddate != ""){
            //起始日期为空，截止日期不为空
            startdate_ ="2020-11-01";
            enddate_ = enddate;
        }else if (startdate != "" && enddate == ""){
            startdate_ = startdate;
            enddate_ = date;
        }else if (startdate != "" && enddate != ""){
            startdate_ = startdate;
            enddate_ = enddate;
        }
        List<TongJiData> data = mapper.SelectCount(qylx,"%"+code+"%",startdate_,enddate_);
        if (data.size()==0){
            return DataResult.FAILURE_NODATA();
        }
//        List<TongJDataCount> dataCounts = maper.SelectCountOfSLZT("%"+code+"%",startdate_,enddate_);
//        //统计每一行
//        for (TongJDataCount tc : dataCounts){
//            for (TongJiData td : data){
//                if (tc.getYwlx().equals(td.getQylx())){
//                    td.setCount(tc.getZj());
//                }
//            }
//        }
        //System.out.println(data);
        return DataResult.getDataResult(CodeAndMsgImpl.SUCCESS,data);
    }

    @Override
    public int UpdateSLRQ(String slh, String slrq) {
        return mapper.UpdateSLRQ(slh,slrq);
    }

    @Override
    public int UpdateYfp(List<String> slh, Integer yfp) {
        return mapper.UpdateYfp(slh,yfp);
    }

    @Override
    public int UpdateInfoOfZrdd(List<String> slh) {
        return mapper.UpdateInfoOfZrdd(slh);
    }
}
