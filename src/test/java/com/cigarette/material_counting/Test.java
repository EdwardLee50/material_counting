package com.cigarette.material_counting;

import com.alibaba.fastjson.JSON;
import com.cigarette.common.utils.ConvertUtils;
import com.cigarette.common.utils.ExcelUtils.ExcelUtils;
import com.cigarette.common.utils.OrderIdGenerater;
import com.cigarette.common.utils.TimeUtils;
import com.cigarette.controller.viewObject.OrderCreateVo;
import com.cigarette.domain.Order;
import com.cigarette.domain.UserInfo;
import com.cigarette.service.model.OrderModel;
import org.joda.time.DateTime;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author EdwardLee
 * @create 2021-08-10 23:07
 */
public class Test {

    @org.junit.Test
    public void test() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setName("fdsdfs");
        String s = JSON.toJSONString(userInfo);
        System.out.println(s);
        UserInfo info = JSON.parseObject(s, userInfo.getClass());
        System.out.println(info);
        System.out.println(info.getClass());
    }

    @org.junit.Test
    public void test1() {
        //Date date = new Date();
        //long time = date.getTime();
        //DateTime now = DateTime.now();
        //long millis = now.getMillis();
        //System.out.println(time);
        //System.out.println(millis);
        //
        //long millis1 = TimeUtils.getNow().getMillis();
        //System.out.println(millis1);
        //
        //long maxWorkerId = 1L << 5L;
        //long maxWorkerId2 = ~(-1L << 5L);
        //System.out.println(maxWorkerId);
        //System.out.println(maxWorkerId2);
        //
        //OrderIdGenerater orderIdGenerater = new OrderIdGenerater();
        //long l = orderIdGenerater.snowflakeId();
        //System.out.println(l);

        List<OrderModel> orderModels = new ArrayList<>();

        OrderModel orderModel = new OrderModel();
        orderModel.getEmployee().setId(0);
        //orderModel.getShopSeller().setId(00);

        OrderModel orderModel1 = new OrderModel();
        orderModel1.getEmployee().setId(1);
        orderModel1.getShopSeller().setId(11);

        OrderModel orderModel2 = new OrderModel();
        orderModel2.getEmployee().setId(2);
        orderModel2.getShopSeller().setId(22);

        orderModels.add(orderModel);
        orderModels.add(orderModel1);
        orderModels.add(orderModel2);

        List<Order> collect = orderModels.stream().map(ConvertUtils::convertModelToDo).collect(Collectors.toList());
        List<Integer> collect1 = collect.stream()
                .map(order -> new Integer[]{order.getEmployeeId(), order.getShopSellerId()})
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        System.out.println(collect1);
    }

    @org.junit.Test
    public void test2() {

        List<OrderModel> orderModels = new ArrayList<>();

        OrderModel orderModel = new OrderModel();
        orderModel.getEmployee().setId(0);
        //orderModel.getShopSeller().setId(00);

        OrderModel orderModel1 = new OrderModel();
        orderModel1.getEmployee().setId(1);
        orderModel1.getShopSeller().setId(11);

        OrderModel orderModel2 = new OrderModel();
        orderModel2.getEmployee().setId(2);
        orderModel2.getShopSeller().setId(22);

        orderModels.add(orderModel);
        orderModels.add(orderModel1);
        orderModels.add(orderModel2);

        //List<Order> collect = orderModels.stream().map(ConvertUtils::convertModelToDo).collect(Collectors.toList());
        long start = System.currentTimeMillis();
        List<Order> collect = orderModels.stream().map(ConvertUtils::convertModelToDo).collect(Collectors.toList());
        //List<Integer> collect1 = collect.stream()
        //        .map(order -> new Integer[]{order.getEmployeeId(), order.getShopSellerId()})
        //        .flatMap(Arrays::stream)
        //        .filter(Objects::nonNull)
        //        .collect(Collectors.toList());
        long end = System.currentTimeMillis();
        System.out.println("stream:" + (end - start));

        start = System.currentTimeMillis();
        //ArrayList<Integer> list = new ArrayList<>(5);
        //for (Order order : collect) {
        //    if(order.getEmployeeId() != null){
        //        list.add(order.getEmployeeId());
        //    }
        //    if(order.getShopSellerId() != null){
        //        list.add(order.getShopSellerId());
        //    }
        //}
        ArrayList<Order> list = new ArrayList<>();
        for (OrderModel model : orderModels) {
            Order order = ConvertUtils.convertModelToDo(model);
            list.add(order);
        }
        end = System.currentTimeMillis();
        System.out.println("foreach:" + (end - start));
    }

    @org.junit.Test
    public void test3() {
        String path = "E:\\example.xlsx";
        List<OrderCreateVo> result = new LinkedList<>();
        ExcelUtils.readToList(path, OrderCreateVo.class,result);
        result.forEach(System.out::println);
        System.out.println(result.size());
    }

}
