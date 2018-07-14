package com.jyss.ziwei.mapper;

import com.jyss.ziwei.entity.HealthActivityReport;
import com.jyss.ziwei.entity.UserDevices;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDevicesMapper {

    int insert(UserDevices userDevices);

    int updateByPrimaryKey(UserDevices userDevices);

    int deleteUserDevice(@Param("id") String id);

    List<UserDevices> getUserDevice(@Param("status") String status, @Param("uId") String uId);
}