package com.cigarette.common.utils.ExcelUtils;

import com.alibaba.excel.EasyExcel;

import java.util.List;

/**
 * @author EdwardLee
 */
public class ExcelUtils<T> {

    public static <T> void readToList(String filePath, Class<T> clazz,  List<T> returnList) {
        EasyExcel.read(filePath, clazz, new DataListener(returnList)).sheet().doRead();
    }
}
