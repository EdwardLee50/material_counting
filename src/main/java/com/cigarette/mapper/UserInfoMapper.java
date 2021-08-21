package com.cigarette.mapper;

import com.cigarette.domain.UserInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author EdwardLee
 * @create 2021-08-10 20:55
 */
@Mapper
public interface UserInfoMapper {
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
    int insert(UserInfo record);

    int insertOrUpdate(UserInfo record);

    int insertOrUpdateSelective(UserInfo record);

    /**
     * insert record to table selective
     *
     * @param record the record
     * @return insert count
     */
    int insertSelective(UserInfo record);

    /**
     * select by primary key
     *
     * @param id primary key
     * @return object by primary key
     */
    UserInfo selectByPrimaryKey(Integer id);

    /**
     * update record selective
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(UserInfo record);

    /**
     * update record
     *
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(UserInfo record);

    int updateBatch(List<UserInfo> list);

    int batchInsert(@Param("list") List<UserInfo> list);

    int updateBatchSelective(List<UserInfo> list);

    UserInfo selectEnabledInfoByPrimaryKey(Integer id);

    Integer selectEnabledInfoIdByTel(String tel);

    UserInfo selectEnabledInfoByTel(String tel);

    List<Integer> selectEnabledInfoIdByTels(List<String> tels);

    List<UserInfo> selectEnabledInfoByTels(List<String> tels);

    List<Integer> selectEnabledInfoIdByPrimaryKeys(List<Integer> ids);

    List<UserInfo> selectEnabledInfoByPrimaryKeys(List<Integer> ids);

    List<UserInfo> selectEnabledSellers();
}