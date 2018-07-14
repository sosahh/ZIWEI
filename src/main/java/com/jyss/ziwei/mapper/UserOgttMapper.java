package com.jyss.ziwei.mapper;

import com.jyss.ziwei.constant.Constant;
import com.jyss.ziwei.entity.PhysicalTest;
import com.jyss.ziwei.entity.UserOgtt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserOgttMapper {

    int insert(UserOgtt userOgtt);

    int updateByPrimaryKey(UserOgtt userOgtt);


    int deleteUserOgtt(@Param("id") String id);

    List<UserOgtt> getUserOgtt(@Param("status") String status,
                               @Param("uId") String uId, @Param("id") String id);
}