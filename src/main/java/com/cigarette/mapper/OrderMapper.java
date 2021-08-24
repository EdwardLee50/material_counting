package com.cigarette.mapper;

import com.cigarette.domain.Order;
import java.util.List;

import com.cigarette.service.model.MaterialModel;
import com.cigarette.service.model.OrderModel;
import com.cigarette.service.model.OrderQueryModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author EdwardLee
 * @create 2021-08-13 20:16
 */
@Mapper
public interface OrderMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(String id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(Order record);

    int insertOrUpdate(Order record);

    int insertOrUpdateSelective(Order record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(Order record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    Order selectByPrimaryKey(String id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(Order record);

    int updateBatch(List<Order> list);

    int updateBatchSelective(List<Order> list);

    int batchInsert(@Param("list") List<Order> list);

    List<OrderModel> selectiveQuery(OrderQueryModel orderQueryModel);

    List<String> selectByPrimaryKeys(List<String> ids);

    List<MaterialModel> selectMaterialsAndUnits();

    int countSelective(OrderQueryModel orderQueryModel);

    List<OrderModel> selectiveQueryPage(@Param("orderQueryModel") OrderQueryModel orderQueryModel,@Param("limit") int limit,@Param("offset") int offset);
}