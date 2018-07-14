package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.PhysicalTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PhysicalTestService {
    int insert(PhysicalTest physicalTest);

    int updateByPrimaryKey(PhysicalTest physicalTest);


    int deletePhysicalTest(@Param("id") String id);

    List<PhysicalTest> getPhysicalTest(@Param("status") String status,
                                       @Param("uId") String uId, @Param("id") String id);
}