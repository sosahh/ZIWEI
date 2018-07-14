package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.MobileLogin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WebLoginService {

    //添加登陆记录
    int insert(MobileLogin mobileLogin);

    int updateByPrimaryKeySelective(MobileLogin mobileLogin);

    //根据token查询用户
    List<MobileLogin> findUserByToken(@Param("token") String token);
}