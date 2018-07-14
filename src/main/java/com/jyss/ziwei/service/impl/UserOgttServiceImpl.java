package com.jyss.ziwei.service.impl;

import com.jyss.ziwei.entity.UserOgtt;
import com.jyss.ziwei.mapper.UserOgttMapper;
import com.jyss.ziwei.service.UserOgttService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserOgttServiceImpl implements UserOgttService {
    @Autowired
    private UserOgttMapper uoMapper;
    @Override
    public int insert(UserOgtt userOgtt) {
        return uoMapper.insert(userOgtt);
    }

    @Override
    public int updateByPrimaryKey(UserOgtt userOgtt) {
        return uoMapper.updateByPrimaryKey(userOgtt);
    }

    @Override
    public int deleteUserOgtt(@Param("id") String id) {
        return uoMapper.deleteUserOgtt(id);
    }

    @Override
    public List<UserOgtt> getUserOgtt(@Param("status") String status, @Param("uId") String uId, @Param("id") String id) {
        return uoMapper.getUserOgtt(status,uId,id);
    }
}