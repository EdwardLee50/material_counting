package com.cigarette.service;

import com.cigarette.common.error.BusinessException;
import com.cigarette.common.error.EnumBusinessError;
import com.cigarette.common.utils.ConvertUtils;
import com.cigarette.common.utils.OrderIdGenerater;
import com.cigarette.common.utils.TimeUtils;
import com.cigarette.domain.UserInfo;
import com.cigarette.mapper.UserInfoMapper;
import com.cigarette.mapper.UserTelsMapper;
import com.cigarette.service.model.OrderModel;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import com.cigarette.mapper.OrderMapper;
import com.cigarette.domain.Order;
import com.cigarette.service.model.OrderQueryModel;
import com.cigarette.service.model.Page;
import com.cigarette.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author EdwardLee
 * @create 2021-08-09 11:04
 */
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Autowired
    private OrderIdGenerater orderIdGenerater;

    @Autowired
    private UserTelsMapper userTelsMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void insert(OrderModel orderModel, Integer empId) throws BusinessException {

        // 判断employee是否存在
        UserInfo employeeDo = userInfoMapper.selectEnabledInfoByPrimaryKey(empId);
        if (employeeDo == null) {
            throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS);
        }
        // 判断seller是否存在
        Integer sellrId = userInfoMapper.selectEnabledInfoIdByTel(orderModel.getShopSeller().getTel());
        if (sellrId == null) {
            throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS);
        }

        // 设置order状态为3配送中
        orderModel.setStatus(3);

        // 添加修改和创建时间
        Date now = TimeUtils.getNowOfDateObj();
        orderModel.setGmtCreate(now);
        orderModel.setGmtModify(now);

        // 生成并设置id
        long snowflakeId = orderIdGenerater.snowflakeId();
        orderModel.setId(String.valueOf(snowflakeId));

        // 插入
        Order order = ConvertUtils.convertModelToDo(orderModel, sellrId, empId);
        orderMapper.insert(order);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public int batchInsert(List<OrderModel> orderModels, Integer empId) throws BusinessException {
        if (orderModels == null || orderModels.size() == 0) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 获取创建时间
        Date now = TimeUtils.getNowOfDateObj();

        // 判断seller是否都合法，即相应的用户都存在
        //      获取empId和sellerTel
        //      为减少循环次数，将设置orderId和时间放在循环中
        List<String> tels = new ArrayList<>(orderModels.size());
        for (int i = 0; i < orderModels.size(); i++) {
            OrderModel orderModel = orderModels.get(i);
            String tel = orderModel.getShopSeller().getTel();
            tels.add(tel);
        }

        // 判断tels是否都合法，并获取对应的userId，生成相应的order
        Map<String, Integer> idByTels = userTelsMapper.selectEnabledInfoIdByTels(tels);
        List<Order> orders = new ArrayList<>(orderModels.size());
        for (int i = 0; i < orderModels.size(); i++) {
            OrderModel orderModel = orderModels.get(i);
            // 判断tels是否都在 查询map的key中，
            if (orderModel.getEmployee() == null || orderModel.getShopSeller().getTel() == null) {
                throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
            } else {
                String tel = orderModel.getShopSeller().getTel();
                // 若不存在，说明用户不存在，抛出异常
                if (!idByTels.containsKey(tel)) {
                    throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS);
                }else{
                    // 若存在，生成order
                    // 转化为order，设置sellerId，orderId、empId，时间和
                    Order order = ConvertUtils.convertModelToDo(orderModel);
                    long id = orderIdGenerater.snowflakeId();
                    order.setId(String.valueOf(id));
                    order.setGmtCreate(now);
                    order.setGmtModify(now);
                    order.setEmployeeId(empId);
                    order.setShopSellerId(idByTels.get(tel));
                    orders.add(order);
                }
            }
        }
        // 若插入记录中涉及用户均存在，则执行插入操作
        int insert = orderMapper.batchInsert(orders);
        return insert;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void updateBatchSelective(List<OrderModel> orderModels) throws BusinessException {
        if (orderModels == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 获取更新时间
        Date now = TimeUtils.getNowOfDateObj();
        // 判断更新的记录是否存在或有效
        //      获取所有的orderId，
        //      根据orderIds查找，
        //      判断结果集大小是否与传入的orderIds大小一致
        List<String> ids = new ArrayList<>(orderModels.size());
        List<Order> orders = new ArrayList<>(orderModels.size());
        for (int i = 0; i < orderModels.size(); i++) {
            OrderModel orderModel = orderModels.get(i);
            if (orderModel != null) {
                if (orderModel.getId() == null) {
                    throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
                }
                ids.add(orderModel.getId());
                // 设置更新时间
                Order order = ConvertUtils.convertModelToDo(orderModel);
                order.setGmtModify(now);
                orders.add(order);
            }
        }
        List<String> orderIds = orderMapper.selectByPrimaryKeys(ids);
        if (orderIds.size() != ids.size()) {
            throw new BusinessException(EnumBusinessError.MATERIAL_ORDER_NOT_EXIT);
        }
        // 执行更新
        orderMapper.updateBatchSelective(orders);
    }

    public int countBySelective(OrderQueryModel orderQueryModel) {
        return orderMapper.countSelective(orderQueryModel);
    }

    /**
     * @param userId          查找者的id，会根据role的区别添加到传入的modle中，作为查询的限制条件
     * @param orderQueryModel 查找的条件，其属性代表了查找的限制条件
     * @return
     * @throws BusinessException
     */
    public List<OrderModel> selectiveQuery(Integer userId, OrderQueryModel orderQueryModel) throws BusinessException {

        if (userId == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 判断id是否有效
        UserInfo userInfo = userInfoMapper.selectEnabledInfoByPrimaryKey(userId);
        if (userInfo == null) {
            throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS);
        }
        // 查询，保证仅可查到自己相关的信息，
        // admin empId和sellerId条件为null，即不加用户限制可查看所有，emp 和 seller 可查看自己相关内容，其他用户暂抛异常
        // emp
        if (userInfo.getRole() == 1) {
            orderQueryModel.setEmployeeId(userId);
        }
        // seller
        else if (userInfo.getRole() == 2) {
            orderQueryModel.setShopSellerId(userId);
        }
        // admin
        else {
            if (userInfo.getRole() != 3) {
                throw new BusinessException(EnumBusinessError.USER_PERMISSION_DENIED);
            }
        }

        List<OrderModel> orderModels = orderMapper.selectiveQuery(orderQueryModel);
        // 返回结果
        return orderModels;
    }

    public Page<OrderModel> selectiveQuery(Integer userId, OrderQueryModel orderQueryModel, Integer currentPage, Integer pageSize) throws BusinessException {
        if (userId == null) {
            throw new BusinessException(EnumBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 判断id是否有效
        UserInfo userInfo = userInfoMapper.selectEnabledInfoByPrimaryKey(userId);
        if (userInfo == null) {
            throw new BusinessException(EnumBusinessError.USER_NOT_EXISTS);
        }
        // 查询，保证仅可查到自己相关的信息，
        // admin empId和sellerId条件为null，即不加用户限制可查看所有，emp 和 seller 可查看自己相关内容，其他用户暂抛异常
        // emp
        if (userInfo.getRole() == 1) {
            orderQueryModel.setEmployeeId(userId);
        }
        // seller
        else if (userInfo.getRole() == 2) {
            orderQueryModel.setShopSellerId(userId);
        }
        // admin
        else {
            if (userInfo.getRole() != 3) {
                throw new BusinessException(EnumBusinessError.USER_PERMISSION_DENIED);
            }
        }

        int totalCount = orderMapper.countSelective(orderQueryModel);
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

        List<OrderModel> orderModels = orderMapper.selectiveQueryPage(orderQueryModel, (currentPage - 1) * pageSize, pageSize);

        // 返回结果
        Page<OrderModel> page = new Page<>(orderModels, totalCount,totalPage);
        return page;
    }
}









