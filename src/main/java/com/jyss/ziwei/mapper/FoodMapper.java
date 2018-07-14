package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.Food;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FoodMapper {

    int insertFood(Food food);

    int updateByPrimaryKey(Food food);


    List<Food> selectFood(@Param("name")String name);

}