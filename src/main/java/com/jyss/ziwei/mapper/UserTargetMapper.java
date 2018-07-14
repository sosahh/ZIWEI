package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.UserTarget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTargetMapper {

    int insert(UserTarget userTarget);

    //查询目标
    List<UserTarget> selectUserTarget(@Param("uId")Long uId);

    //插入或更新
    int updateUserTarget(UserTarget userTarget);


}