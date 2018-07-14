package com.jyss.ziwei.mapper;

import com.jyss.ziwei.constant.Constant;
import com.jyss.ziwei.entity.PhysicalTest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PhysicalTestMapper {

    int insert(PhysicalTest physicalTest);

    int updateByPrimaryKey(PhysicalTest physicalTest);


    int deletePhysicalTest(@Param("id") String id);

    List<PhysicalTest> getPhysicalTest(@Param("status") String status,
                               @Param("uId") String uId, @Param("id") String id);
}