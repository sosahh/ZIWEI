package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.MobileLogin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MobileLoginMapper {

    //添加登陆记录
    int insert(MobileLogin mobileLogin);

    //根据token查询用户
    List<MobileLogin> findUserByToken(@Param("token") String token);


    ////////////web端////////////
    //添加登陆记录
    int insertMobileLogin(MobileLogin mobileLogin);

    //根据token查询用户
    List<MobileLogin> selectWebUserByToken(@Param("token") String token);


}