package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.HealthNewsCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthNewsCategoryMapper {

    int insert(HealthNewsCategory healthNewsCategory);

    int updateByPrimaryKey(HealthNewsCategory healthNewsCategory);

    //查询所有类目
    List<HealthNewsCategory> selectNewsCategory();


}