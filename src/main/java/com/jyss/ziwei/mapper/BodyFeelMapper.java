package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.BodyFeel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BodyFeelMapper {

    int insert(BodyFeel bodyFeel);

    int updateByPrimaryKey(BodyFeel bodyFeel);

    //查询日期
    List<String> selectCreated(@Param("uId")Long uId);

    //
    List<BodyFeel> selectBodyFeel(@Param("uId")Long uId, @Param("created")String created);
}