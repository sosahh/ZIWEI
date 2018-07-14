package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.BodyFeces;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyFecesMapper {

    int insert(BodyFeces bodyFeces);

    int updateByPrimaryKey(BodyFeces bodyFeces);

    List<BodyFeces> selectBodyFeces(@Param("uId")Long uId, @Param("createTime")String createTime);
}