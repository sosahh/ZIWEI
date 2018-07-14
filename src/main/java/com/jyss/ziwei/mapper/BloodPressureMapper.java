package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.BloodPressure;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodPressureMapper {

    int insert(BloodPressure bloodPressure);

    int updateByPrimaryKey(BloodPressure bloodPressure);
    
}