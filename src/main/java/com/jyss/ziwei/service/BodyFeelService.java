package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.BodyFeces;
import com.jyss.ziwei.entity.ResponseResult;
import org.apache.ibatis.annotations.Param;


public interface BodyFeelService {

    ResponseResult insertStomachReact(@Param("uId")Long uId, @Param("cwData")String cwData, @Param("note")String note);


    //查询肠胃反应
    ResponseResult selectStomachReact(@Param("uId")Long uId, @Param("page")Integer page,
                                      @Param("pageSize")Integer pageSize);

    ResponseResult insertBodyFeel(@Param("uId")Long uId, @Param("stData")String stData, @Param("note")String note);


    //查询身体感受
    ResponseResult selectBodyFeel(@Param("uId")Long uId, @Param("page")Integer page,
                                  @Param("pageSize")Integer pageSize);

    //添加大便记录
    ResponseResult insertBodyFeces(@Param("uId")Long uId, BodyFeces bodyFeces);

    //查询大便记录
    ResponseResult selectBodyFeces(@Param("uId")Long uId, @Param("page")Integer page,
                                   @Param("pageSize")Integer pageSize);

}
