package com.cigarette.controller;

import com.cigarette.common.annotation.NoRepeatSubmit;
import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.response.ApiRestResponse;
import com.cigarette.common.utils.ConvertUtils;
import com.cigarette.common.utils.JwtUtils;
import com.cigarette.controller.viewObject.OrderCreateVo;
import com.cigarette.controller.viewObject.OrderReturnVo;
import com.cigarette.controller.viewObject.ValidList;
import com.cigarette.service.OrderService;
import com.cigarette.service.model.OrderModel;
import com.cigarette.service.model.OrderQueryModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author EdwardLee
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @NoRepeatSubmit
    @PostMapping("/add")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ApiRestResponse createOrder(@RequestBody @Valid OrderCreateVo orderCreateVo,
                                       @RequestParam Integer empId) throws BusinessException {
        // 权限校验，非employee和管理员不可访问，暂时由emp过滤器实现

        // todo 处理重复提交的问题！！！

        // 入参校验，由@valid实现
        if (empId == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        if (orderCreateVo == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        OrderModel orderModel = ConvertUtils.convertVoToModel(orderCreateVo);

        orderService.insert(orderModel, empId);
        return ApiRestResponse.success();
    }

    @NoRepeatSubmit
    @PostMapping(value = "/orders")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public ApiRestResponse batchUpdateStatus(@RequestBody @Valid ValidList<OrderModel> orderModels) throws BusinessException, JsonProcessingException {
        if (orderModels == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        orderService.updateBatchSelective(orderModels);
        return ApiRestResponse.success();
    }

    @GetMapping(value = "/query")
    @ResponseBody
    public ApiRestResponse selectiveQueryById(@RequestParam Integer userId,
                                              @RequestParam(required = false) String orderId,
                                              @RequestParam(required = false) String material,
                                              @RequestParam(required = false) String unit,
                                              @RequestParam(required = false) Integer number,
                                              @RequestParam(required = false) Integer status,
                                              @RequestParam(required = false) Integer sellerId,
                                              @RequestParam(required = false) Integer employeeId,
                                              @RequestParam(required = false) Date start,
                                              @RequestParam(required = false) Date end,
                                              HttpServletRequest request) throws BusinessException {
        // 登录可访问，暂时由global过滤器实现

        // 入参校验
        if (userId == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // token校验，
        // 有效性由global过滤器保证，
        // 还需验证token中的id与路径所传id相同，即访问用户一致
        Object infoByJwtToken = JwtUtils.getInfoByJwtToken(request);
        if (!userId.equals(infoByJwtToken)) {
            throw new BusinessException(EnumBusinessError.USER_PERMISSION_DENIED);
        }

        // 生成查询对象
        OrderQueryModel orderQueryModel = new OrderQueryModel();
        orderQueryModel.setId(orderId);
        orderQueryModel.setMaterial(material);
        orderQueryModel.setUnit(unit);
        orderQueryModel.setStatus(status);
        orderQueryModel.setNumber(number);
        orderQueryModel.setShopSellerId(sellerId);
        orderQueryModel.setEmployeeId(employeeId);
        orderQueryModel.setStart(start);
        orderQueryModel.setEnd(end);

        List<OrderModel> orderModels = orderService.selectiveQuery(userId, orderQueryModel);

        if (orderModels == null) {
            return ApiRestResponse.success();
        }

        List<OrderReturnVo> orderReturnVos = new ArrayList<>(orderModels.size());
        for (int i = 0; i < orderModels.size(); i++) {
            OrderReturnVo orderReturnVo = ConvertUtils.convertModelToVo(orderModels.get(i));
            orderReturnVos.add(orderReturnVo);
        }

        return ApiRestResponse.success(orderReturnVos);
    }
}
