package com.jyss.ziwei.service.impl;

import com.jyss.ziwei.entity.UserMessage;
import com.jyss.ziwei.mapper.UserMessageMapper;
import com.jyss.ziwei.service.UserMessageService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserMessageServiceImpl implements UserMessageService{
    @Autowired
    private UserMessageMapper umMapper;

    @Override
    public int insert(UserMessage userMessage) {
        return umMapper.insert(userMessage);
    }


}