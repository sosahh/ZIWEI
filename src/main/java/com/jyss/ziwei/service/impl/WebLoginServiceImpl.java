package com.jyss.ziwei.service.impl;

import com.jyss.ziwei.entity.MobileLogin;
import com.jyss.ziwei.mapper.WebLoginMapper;
import com.jyss.ziwei.service.WebLoginService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WebLoginServiceImpl implements WebLoginService {
    @Autowired
    private WebLoginMapper webLoginMapper;
    @Override
    public int insert(MobileLogin mobileLogin) {
        return  webLoginMapper.insert(mobileLogin);
    }

    @Override
    public int updateByPrimaryKeySelective(MobileLogin mobileLogin) {
        return  webLoginMapper.updateByPrimaryKeySelective(mobileLogin);
    }

    @Override
    public List<MobileLogin> findUserByToken(@Param("token") String token) {
        return  webLoginMapper.findUserByToken(token);
    }
}