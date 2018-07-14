package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.MobileLogin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WebLoginMapper {

    //添加登陆记录
    int insert(MobileLogin mobileLogin);

    int updateByPrimaryKeySelective(MobileLogin mobileLogin);

    //根据token查询用户
    List<MobileLogin> findUserByToken(@Param("token") String token);


}