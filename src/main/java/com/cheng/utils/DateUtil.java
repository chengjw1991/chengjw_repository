package com.cheng.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chengjw
 * @date 2020-12-18 10:11
 * 自定义日期工具类
 */
public class DateUtil {

    public static String getDateToLongStr(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
    public static String getDateToShortStr(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
