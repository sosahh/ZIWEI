package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.StomachReact;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StomachReactMapper {

    int insert(StomachReact stomachReact);

    int updateByPrimaryKey(StomachReact stomachReact);

    //查询日期
    List<String> selectCreated(@Param("uId")Long uId);

    //
    List<StomachReact> selectStomachReact(@Param("uId")Long uId,@Param("created")String created);

}