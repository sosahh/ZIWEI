package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.BloodSugar;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodSugarMapper {


    int insert(BloodSugar bloodSugar);

    int updateByPrimaryKey(BloodSugar bloodSugar);

}