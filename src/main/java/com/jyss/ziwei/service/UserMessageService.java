package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.UserMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMessageService {
    int insert(UserMessage userMessage);

}