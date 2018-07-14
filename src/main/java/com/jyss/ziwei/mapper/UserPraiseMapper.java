package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.UserPraise;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPraiseMapper {

    int insert(UserPraise userPraise);

    int updateByPrimaryKey(UserPraise userPraise);

    //查询点赞
    List<UserPraise> selectUserPraise(@Param("uId")Long uId, @Param("knowId")Integer knowId);

}