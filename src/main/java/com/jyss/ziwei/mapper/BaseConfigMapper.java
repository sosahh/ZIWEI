package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.BaseConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BaseConfigMapper {

    int insert(BaseConfig baseConfig);

    int updateByPrimaryKey(BaseConfig baseConfig);

    List<BaseConfig> selectBaseConfig(@Param("baseKey") String baseKey, @Param("status") Integer status);

}