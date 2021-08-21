package com.cigarette.controller;

import com.cigarette.common.annotation.NoRepeatSubmit;
import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.response.ApiRestResponse;
import com.cigarette.common.utils.ConvertUtils;
import com.cigarette.common.utils.ExcelUtils.ExcelUtils;
import com.cigarette.common.utils.JwtUtils;
import com.cigarette.controller.viewObject.OrderCreateVo;
import com.cigarette.controller.viewObject.UserRegisterVo;
import com.cigarette.controller.viewObject.ValidList;
import com.cigarette.service.OrderService;
import com.cigarette.service.UserService;
import com.cigarette.service.model.OrderModel;
import com.cigarette.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * @author EdwardLee
 * @create 2021-08-13 20:55
 */
@RestController
@RequestMapping("/batch")
public class BatchController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    /**
     * 批量注册emp
     * 权限：emp（admin也可）
     *
     * @param userRegisterVos 注册个人信息 name password tel
     * @param subId 为防止多次重复提交生成的subId
     * @throws BusinessException
     */
    @NoRepeatSubmit
    @PostMapping("/add/emps/{subId}")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void batchRegisterEmp(@RequestBody @Valid ValidList<UserRegisterVo> userRegisterVos,
                                 @PathVariable String subId) throws BusinessException {
        //  鉴权，仅emp可操作，实现批量添加employee的方法，暂时由emp filter实现

        // 设置role为1,employee
        List<UserModel> userModels = new ArrayList<>(userRegisterVos.size());
        for (int i = 0; i < userRegisterVos.size(); i++) {
            UserRegisterVo userRegisterVo = userRegisterVos.get(i);
            UserModel userModel = ConvertUtils.convertVoToModel(userRegisterVo);
            userModel.setRole(1);
            userModels.add(userModel);
        }
        userService.batchRegister(userModels);
    }

    /**
     * 批量注册seller，
     * 权限：emp（admin也可）
     *
     * @param userRegisterVos 注册个人信息 name password tel
     * @throws BusinessException
     */
    @NoRepeatSubmit
    @PostMapping("/add/sellers")
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void batchRegisterSeller(@RequestBody @Valid ValidList<UserRegisterVo> userRegisterVos) throws BusinessException {
        //  鉴权，仅emp可操作，实现批量添加seller的方法，暂时由emp filter实现

        // 设置role为2,seller
        List<UserModel> userModels = new ArrayList<>(userRegisterVos.size());
        for (int i = 0; i < userRegisterVos.size(); i++) {
            UserRegisterVo userRegisterVo = userRegisterVos.get(i);
            UserModel userModel = ConvertUtils.convertVoToModel(userRegisterVo);
            userModel.setRole(2);
            userModels.add(userModel);
        }
        userService.batchRegister(userModels);
    }

    /**
     * 批量生成order
     *
     * @param orderCreateVos
     * @param empId
     * @return
     * @throws BusinessException
     */
    @NoRepeatSubmit
    @PostMapping(value = "/add/orders")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ApiRestResponse batchCreateOrders(@RequestBody @Valid ValidList<OrderCreateVo> orderCreateVos,
                                             @RequestParam Integer empId) throws BusinessException {
        // 鉴权，仅emp可操作，实现批量添加seller的方法，暂时由emp filter实现

        // 入参校验，由@valid实现

        if (empId == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        int addOrders = addOrders(orderCreateVos, empId);

        return ApiRestResponse.success();
    }

    @NoRepeatSubmit
    @PostMapping(value = "/add/byExcel")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ApiRestResponse createByExcel(@RequestBody Map<String, String> map,
                                         HttpServletRequest request) throws BusinessException, IOException {
        //  鉴权，仅emp可操作，实现批量添加seller的方法，暂时由emp filter实现

        // 获取token中的id
        Integer empId = (Integer) JwtUtils.getInfoByJwtToken(request);
        if (empId == null || map == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 获取服务器上的文件路径
        String timeStamp = map.get("timeStamp");
        String uuid = map.get("uuid");
        String fileName = map.get("fileName");
        if (timeStamp == null || uuid == null || fileName == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        String filePath = timeStamp + uuid + fileName;

        // 读取文件到List
        List<OrderCreateVo> orderCreateVos = new LinkedList<>();
        ExcelUtils.readToList(filePath, OrderCreateVo.class, orderCreateVos);

        // 调用自身的私有方法保存
        int addOrders = addOrders(orderCreateVos, empId);

        return ApiRestResponse.success(addOrders);
    }

    /**
     * 批量添加 order，为 batchCreateOrders 和 createByExcel 服务
     *
     * @param orderCreateVos
     * @param empId
     * @return
     * @throws BusinessException
     */
    public int addOrders(List<OrderCreateVo> orderCreateVos, Integer empId) throws BusinessException {

        if (orderCreateVos == null || empId == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 转化为model
        List<OrderModel> orderModels = new ArrayList<>();
        for (int i = 0; i < orderCreateVos.size(); i++) {
            OrderCreateVo orderCreateVo = orderCreateVos.get(i);
            // 转换为model
            if (orderCreateVo != null) {
                orderModels.add(ConvertUtils.convertVoToModel(orderCreateVo));
            }
        }
        // 添加
        int insert = orderService.batchInsert(orderModels, empId);

        return insert;
    }
}
