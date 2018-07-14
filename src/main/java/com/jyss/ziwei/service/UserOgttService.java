package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.UserOgtt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserOgttService {
    int insert(UserOgtt userOgtt);

    int updateByPrimaryKey(UserOgtt userOgtt);


    int deleteUserOgtt(@Param("id") String id);

    List<UserOgtt> getUserOgtt(@Param("status") String status,
                               @Param("uId") String uId, @Param("id") String id);
}