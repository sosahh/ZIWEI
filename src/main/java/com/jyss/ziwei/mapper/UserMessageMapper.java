package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.UserMessage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMessageMapper {

    int insert(UserMessage userMessage);

    int updateByPrimaryKey(UserMessage userMessage);

    List<UserMessage> selectUserMessage(@Param("userId")Long userId);

    int updateUserMessage(@Param("userId")Long userId);
}