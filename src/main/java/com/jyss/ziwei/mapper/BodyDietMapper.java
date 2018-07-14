package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.BodyDiet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface BodyDietMapper {

    int insertBodyDiet(BodyDiet bodyDiet);

    int updateByPrimaryKey(BodyDiet bodyDiet);

    //查询日期
    List<String> selectCreated(@Param("uId")Long uId);

    //
    List<BodyDiet> selectBodyDiet(@Param("uId")Long uId, @Param("meal")String meal, @Param("created")String created);

    //查询单日总能量
    int selectTotalEnergy(@Param("uId")Long uId, @Param("created")Date created);

}