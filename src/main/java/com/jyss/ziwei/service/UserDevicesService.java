package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.ResponseResult;
import com.jyss.ziwei.entity.UserDevices;


public interface UserDevicesService {

    int insert(UserDevices userDevices);

    int updateByPrimaryKey(UserDevices userDevices);



    /**
     * 添加设备
     * @param token
     * @param ud
     * @return
     */
    ResponseResult addDevice(String token, UserDevices ud);

    /**
     * 修改设备
     * @param token
     * @param ud
     * @return
     */
    ResponseResult upDevice(String token, UserDevices ud);
    /**
     * 删除设备
     * @param token
     * @param udId
     * @return
     */
    ResponseResult delDevice(String token, String udId);

    /**
     * 获取我的设备
     * @param token
     * @return
     */
    ResponseResult getMyDevice(String token);


}