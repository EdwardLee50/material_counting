package com.cigarette.mapper;

import com.cigarette.domain.UserPassword;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author EdwardLee
 * @create 2021-08-10 20:47
 */
@Mapper
public interface UserPasswordMapper {
    /**
     * delete by primary key
     *
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     *
     * @param record the record
     * @return insert count
     */
    int insert(UserPassword record);

    int insertOrUpdate(UserPassword record);

    int insertOrUpdateSelective(UserPassword record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(UserPassword record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    UserPassword selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(UserPassword record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(UserPassword record);

    int updateBatch(List<UserPassword> list);

    int batchInsert(@Param("list") List<UserPassword> list);

    int updateBatchSelective(List<UserPassword> list);

    UserPassword selectByUserId(Integer userId);
}