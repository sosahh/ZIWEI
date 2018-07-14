package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.BaseConfig;
import com.jyss.ziwei.entity.ResponseResult;
import org.apache.ibatis.annotations.Param;
import java.util.List;


public interface ItemService {

    //我的消息
    ResponseResult selectUserMessage(@Param("userId") Long userId, @Param("page") Integer page,
                                     @Param("pageSize") Integer pageSize);

    //读取消息
    ResponseResult updateUserMessage(@Param("userId") Long userId);


    //查询常量信息
    List<BaseConfig> selectBaseConfig(@Param("baseKey") String baseKey, @Param("status") Integer status);

    //修改常量信息
    int updateByPrimaryKey(BaseConfig baseConfig);

}
