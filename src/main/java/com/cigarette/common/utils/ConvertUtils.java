package com.cigarette.common.utils;

import com.cigarette.controller.viewObject.OrderCreateVo;
import com.cigarette.controller.viewObject.OrderReturnVo;
import com.cigarette.controller.viewObject.UserRegisterVo;
import com.cigarette.controller.viewObject.UserReturnVo;
import com.cigarette.domain.Order;
import com.cigarette.domain.UserInfo;
import com.cigarette.domain.UserPassword;
import com.cigarette.service.model.OrderModel;
import com.cigarette.service.model.UserModel;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author EdwardLee
 * @create 2021-08-13 20:57
 */
public class ConvertUtils {

    public static UserModel convertVoToModel(UserRegisterVo userRegisterVo) {
        if (userRegisterVo == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userRegisterVo, userModel);
        return userModel;
    }

    public static UserReturnVo convertModelToVo(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserReturnVo userReturnVo = new UserReturnVo();
        BeanUtils.copyProperties(userModel, userReturnVo);
        return userReturnVo;
    }

    public static UserInfo convertModelToInfoDo(UserModel userModel, Date modify) {
        if (userModel == null) {
            return null;
        }
        UserInfo userInfo = convertModelToInfoDo(userModel);
        // 设置修改时间
        userInfo.setGmtModify(modify);
        return userInfo;
    }

    public static UserInfo convertModelToInfoDo(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userModel, userInfo);
        return userInfo;
    }

    public static UserPassword convertModelToPasswordDo(UserModel userModel, Date modify) {
        if (userModel == null) {
            return null;
        }
        UserPassword userPassword = convertModelToPasswordDo(userModel);
        // 设置修改时间
        userPassword.setGmtModify(modify);
        return userPassword;
    }

    public static UserPassword convertModelToPasswordDo(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPassword userPassword = new UserPassword();
        // 设置userid
        userPassword.setUserId(userModel.getId());
        // 设置加密密码
        String encryptedPassword = Md5Utiles.getMd5Str(userModel.getPassword());
        userPassword.setEncryptedPassword(encryptedPassword);
        // 设置创建和修改日期
        userPassword.setGmtCreate(userModel.getGmtCreate());
        userPassword.setGmtModify(userModel.getGmtModify());
        return userPassword;
    }

    public static UserModel convertInfoDoToModel(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userInfo, userModel);
        return userModel;
    }

    public static OrderModel convertVoToModel(OrderCreateVo orderCreateVo) {
        if (orderCreateVo == null) {
            return null;
        }
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderCreateVo, orderModel);
        if (orderCreateVo.getShopSellerTel() != null) {
            orderModel.getShopSeller().setTel(orderCreateVo.getShopSellerTel());
        }
        return orderModel;
    }

    public static OrderModel convertVoToModel(OrderCreateVo orderCreateVo,String orderId) {
        if (orderCreateVo == null) {
            return null;
        }
        OrderModel orderModel = new OrderModel();
        BeanUtils.copyProperties(orderCreateVo, orderModel);
        if (orderCreateVo.getShopSellerTel() != null) {
            orderModel.getShopSeller().setTel(orderCreateVo.getShopSellerTel());
        }
        orderModel.setId(orderId);
        return orderModel;
    }

    public static Order convertModelToDo(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderModel, order);
        if (orderModel.getEmployee() != null) {
            order.setEmployeeId(orderModel.getEmployee().getId());
        }
        if (orderModel.getShopSeller() != null) {
            order.setShopSellerId(orderModel.getShopSeller().getId());
        }
        return order;
    }

    public static Order convertModelToDo(OrderModel orderModel, Integer sellerId, Integer empId) {
        if (orderModel == null) {
            return null;
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderModel, order);

        order.setEmployeeId(empId);
        order.setShopSellerId(sellerId);

        return order;
    }

    public static OrderReturnVo convertModelToVo(OrderModel orderModel,
                                                 UserModel shopSeller,
                                                 UserModel employee) {
        if (orderModel == null) {
            return null;
        }
        OrderReturnVo orderReturnVo = new OrderReturnVo();
        BeanUtils.copyProperties(orderModel, orderReturnVo);

        orderReturnVo.setShopSeller(convertModelToVo(shopSeller));
        orderReturnVo.setEmployee(convertModelToVo(employee));
        return orderReturnVo;
    }

    /**
     * @param orderModel 需要 orderModel 自身含有 emp和seller的model
     * @return
     */
    public static OrderReturnVo convertModelToVo(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }
        OrderReturnVo orderReturnVo = new OrderReturnVo();
        BeanUtils.copyProperties(orderModel, orderReturnVo);
        orderReturnVo.setShopSeller(convertModelToVo(orderModel.getShopSeller()));
        orderReturnVo.setEmployee(convertModelToVo(orderModel.getEmployee()));
        return orderReturnVo;
    }
}
