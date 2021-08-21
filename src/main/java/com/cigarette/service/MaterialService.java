package com.cigarette.service;

import com.cigarette.mapper.OrderMapper;
import com.cigarette.service.model.MaterialModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author EdwardLee
 * @create 2021-08-15 23:51
 */
@Service
public class MaterialService {

    @Autowired
    private OrderMapper orderMapper;

    public List<MaterialModel> selectiveQuery() {
        List<MaterialModel> materials = orderMapper.selectMaterialsAndUnits();
        return materials;
        //public List<MaterialModel> selectiveQuery(MaterialModel materialModel)
    }
}
