package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.HealthActivity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthActivityMapper {

    int insertHealthActivity(HealthActivity healthActivity);

    int updateByPrimaryKey(HealthActivity healthActivity);

    int deleteHealthActivity(@Param("id") String id);

    List<HealthActivity> getHealthActivity(@Param("status") String status, @Param("type") String type, @Param("id") String id);


    List<HealthActivity> getHealthActivityDetials(@Param("status") String status, @Param("type") String type, @Param("id") String id);

    List<HealthActivity> getMyActivityReport(@Param("status") String status, @Param("uId") String uId, @Param("id") String id);
}