package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.MobileLogin;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MobileLoginService {

    //添加登陆记录
    int insert(MobileLogin mobileLogin);

    List<MobileLogin> findUserByToken(@Param("token") String token);


    //添加登陆记录
    int insertMobileLogin(MobileLogin mobileLogin);

    List<MobileLogin> selectWebUserByToken(@Param("token") String token);


}
