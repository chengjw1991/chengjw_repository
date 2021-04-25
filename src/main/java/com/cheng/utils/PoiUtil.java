package com.cheng.utils;

import com.cheng.beans.Companyinfo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ChengJW
 * 2020/11/29/029
 * 自定义excel导出工具类
 */

public class PoiUtil {

    /**
     * 导出 companyinfo 信息的 poi
     * @param info
     * @return
     */
    public static HSSFWorkbook getworkbook(Companyinfo info){
        // 创建 一个HSSFWorkbook对象，对应一个execl文件
        HSSFWorkbook book = new HSSFWorkbook();
        // 创建一个 sheet表，可以选择设置表明
        HSSFSheet sheet = book.createSheet();
        // 设置表的第一行
        HSSFRow row0 = sheet.createRow(0);
        /**
         * 第一行第一列
         */

        HSSFCell cell0 = row0.createCell(0);
        // 设置第一列的内容
        cell0.setCellValue(info.getQymc());
        // 合并单元格 参数：起始行号，终止行号，起始列号，终止列号
        CellRangeAddress range = new CellRangeAddress(0,0,0,3);
        //设置第一行内容居中
        HSSFCellStyle style = book.createCellStyle();
        // 水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //自动换行
        style.setWrapText(true);
        cell0.setCellStyle(style);
        /**
         * 第二行
         */

        return null;

    }
}
