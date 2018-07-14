package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.Drug;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DrugMapper {

    int insert(Drug drug);

    int updateByPrimaryKey(Drug drug);

    //查询日期
    List<String> selectDrugName(@Param("name")String name, @Param("drugType")Integer drugType);


    List<Drug> selectDrug(@Param("drugName")String drugName, @Param("name")String name,
                          @Param("drugType")Integer drugType);

}