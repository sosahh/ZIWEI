package com.jyss.ziwei.service;


import com.jyss.ziwei.entity.BodyDiet;
import com.jyss.ziwei.entity.BodyDrug;
import com.jyss.ziwei.entity.ResponseResult;
import org.apache.ibatis.annotations.Param;


public interface BodyDietService {

    //查询食物大卡
    ResponseResult selectFood(@Param("name")String name, @Param("page") Integer page,
                              @Param("pageSize")Integer pageSize);

    //添加饮食
    ResponseResult insertBodyDiet(BodyDiet bodyDiet);

    //添加饮食
    ResponseResult insertPLBodyDiet(@Param("uId")Long uId, @Param("diets")String diets);

    //查询用户饮食
    ResponseResult selectBodyDiet(@Param("uId")Long uId, @Param("page") Integer page,
                                  @Param("pageSize")Integer pageSize);

    //查询药物
    ResponseResult selectDrug(@Param("name")String name, @Param("drugType") Integer drugType,
                              @Param("page") Integer page, @Param("pageSize")Integer pageSize);

    //添加药物
    ResponseResult insertBodyDrug(BodyDrug bodyDrug);

    //查询药物记录
    ResponseResult selectBodyDrug(@Param("uId")Long uId, @Param("page") Integer page,
                                  @Param("pageSize")Integer pageSize);

    //查询最近的饮食和药物记录
    ResponseResult selectDrugAndDiet(@Param("uId")Long uId);

}
