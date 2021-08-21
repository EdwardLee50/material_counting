package com.cigarette.controller;

import com.cigarette.common.response.ApiRestResponse;
import com.cigarette.service.model.MaterialModel;
import com.cigarette.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author EdwardLee
 * @create 2021-08-15 22:39
 */
@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/all")
    public ApiRestResponse getAll(){
        // 鉴权，仅emp可操作，实现批量添加seller的方法，暂时由emp filter实现
        MaterialModel materialModel = new MaterialModel();
        List<MaterialModel> materials = materialService.selectiveQuery();
        return ApiRestResponse.success(materials);
    }
}
