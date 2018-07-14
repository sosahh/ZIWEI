package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.UserComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCommentMapper {

    int insert(UserComment userComment);

    int updateByPrimaryKey(UserComment userComment);

    //查询评论
    List<UserComment> selectUserComment(@Param("knowId")Integer knowId);

    //查询评论数
    int selectTotalComment(@Param("knowId")Integer knowId);

}