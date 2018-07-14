package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.BodyDrug;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BodyDrugMapper {

    int insert(BodyDrug bodyDrug);

    int updateByPrimaryKey(BodyDrug bodyDrug);

    //查询日期
    List<String> selectCreated(@Param("uId")Long uId);


    List<BodyDrug> selectBodyDrug(@Param("uId")Long uId, @Param("created")String created);

    //查询最近一条数据
    List<BodyDrug> selectBodyDrugLately(@Param("uId")Long uId);

}