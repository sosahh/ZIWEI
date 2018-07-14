package com.jyss.ziwei.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.jyss.ziwei.entity.BaseConfig;
import com.jyss.ziwei.entity.Page;
import com.jyss.ziwei.entity.ResponseResult;
import com.jyss.ziwei.entity.UserMessage;
import com.jyss.ziwei.mapper.BaseConfigMapper;
import com.jyss.ziwei.mapper.UserMessageMapper;
import com.jyss.ziwei.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private UserMessageMapper userMessageMapper;
    @Autowired
    private BaseConfigMapper baseConfigMapper;




    /**
     * 我的消息
     */
    @Override
    public ResponseResult selectUserMessage(Long userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<UserMessage> messages = userMessageMapper.selectUserMessage(userId);
        PageInfo<UserMessage> pageInfo = new PageInfo<>(messages);
        Page<UserMessage> result = new Page<>(pageInfo);
        return ResponseResult.ok(result);
    }


    /**
     * 读取消息
     */
    @Override
    public ResponseResult updateUserMessage(Long userId) {
        userMessageMapper.updateUserMessage(userId);
        return ResponseResult.ok(new HashMap<>());
    }


    /**
     * 查询常量信息
     */
    @Override
    public List<BaseConfig> selectBaseConfig(String baseKey, Integer status) {
        return baseConfigMapper.selectBaseConfig(baseKey,status);
    }

    @Override
    public int updateByPrimaryKey(BaseConfig baseConfig) {
        return baseConfigMapper.updateByPrimaryKey(baseConfig);
    }


}
