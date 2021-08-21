package com.cigarette.common.utils.ExcelUtils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author EdwardLee
 * @create 2021-08-16 20:58
 */
public class DataListener<T> extends AnalysisEventListener<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataListener.class);

    List<T> list;

    public DataListener(List<T> list) {
        this.list = list;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        if(data != null){
            LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
            list.add(data);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        this.list = null;
        LOGGER.info("所有数据解析完成！");
    }
}
