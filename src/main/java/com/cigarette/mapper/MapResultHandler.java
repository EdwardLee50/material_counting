package com.cigarette.mapper;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author EdwardLee
 * @create 2021-08-22 19:48
 */
@Component
public class MapResultHandler implements ResultHandler {

    private final Map mapResults = new HashMap<>();

    @SuppressWarnings("rawtypes")
    private final Map mappedResults = new HashMap();

    private String key;
    private String value;

    @SuppressWarnings("unchecked")
    @Override
    public void handleResult(ResultContext context) {
        @SuppressWarnings("rawtypes")
        Map map = (Map) context.getResultObject();
        // xml配置里面的property的值，对应的列
        mappedResults.put(map.get(key), map.get(value));
    }

    @SuppressWarnings("rawtypes")
    public Map getMappedResults() {
        return mappedResults;
    }

    public MapResultHandler(String key,String value) {
        this.key = key;
        this.value = value;
    }

    public MapResultHandler() {
    }
}
