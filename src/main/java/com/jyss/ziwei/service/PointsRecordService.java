package com.jyss.ziwei.service;


import org.apache.ibatis.annotations.Param;

public interface PointsRecordService {


    //添加数据录入积分
    boolean updateUserPointsRecord(@Param("uId")Long uId, @Param("knowId")Integer knowId,
                                   @Param("detail")String detail, @Param("type")Integer type);

}
