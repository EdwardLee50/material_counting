package com.cigarette.mapper;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author EdwardLee
 * @create 2021-08-22 19:51
 */
@Repository
public class UserTelsMapper extends SqlSessionDaoSupport{

    @Override
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @SuppressWarnings("unchecked")
    public Map<String,Integer> selectEnabledInfoIdByTels(List<String> tels) {
        //MyBatis的XML文件查询条件，直接用map，有几个条件map里面放几个，其中map的key相当于dao层中@Param里面的内容，也就是说用了这个时候不需要写dao层。
        HashMap<String, Object> param = new HashMap<>();

        // 快速获取hashMap写法（xml的mysql查询条件吗，多条件直接往里面put），
        // 与mapper接口参数相对应，即xml的条件参数：param.put("param1", xxx);param.put("param2", xxx);param.put("param3", xxx);
        param.put("list", tels);

        // 填写键值对（xml文件中mysql的返回值，前面一个作为返回map的key后面的作为value）
        MapResultHandler hander = new MapResultHandler("tel", "id");

        // 原mybatis自定义返回值查询（全限定方法名,带上mybatis的namespace，getWkDocAmountDao为xml文件中查询语句的id）
        this.getSqlSession().select("com.cigarette.mapper.UserInfoMapper" + ".mapEnabledInfoIdByTels", param, hander);
        //返回映射好的键值对。可以用键值对做想做的操作
        Map<String,Integer> results = hander.getMappedResults();
        return results;
    }
    //<resultMap id="telIdMap" type="java.util.HashMap">
    //    <result property="tel" column="tel" javaType="java.lang.String"/>
    //    <result property="id" column="id" javaType="java.lang.Integer" />
    //</resultMap>
    //<select id="mapEnabledInfoIdByTels" resultMap="telIdMap" parameterType="java.util.List">
    //select tel,id
    //from mc_user_info
    //where is_disabled = 0
    //and tel in
    //        <foreach collection="list" item="tel" separator="," open="(" close=")" index="">
    //        #{tel,jdbcType=VARCHAR}
    //    </foreach>
    //</select>
}
