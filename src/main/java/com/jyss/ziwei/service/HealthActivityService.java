package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.HealthActivity;
import com.jyss.ziwei.entity.HealthActivityReport;
import com.jyss.ziwei.entity.ResponseEntity;
import com.jyss.ziwei.entity.ResponseResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface HealthActivityService {
    int insertHealthActivity(HealthActivity healthActivity);

    int updateByPrimaryKey(HealthActivity healthActivity);

    int deleteHealthActivity(@Param("id") String id);

    List<HealthActivity> getHealthActivity(@Param("status") String status, @Param("type") String type, @Param("id") String id);

    List<HealthActivity> getHealthActivityDetials(@Param("status") String status, @Param("type") String type, @Param("id") String id);

    List<HealthActivity> getMyActivityReport(@Param("status") String status, @Param("uId") String uId, @Param("id") String id);

    /**
     * 健康活动浏览
     * @param token
     * @param page
     * @param limit
     * @return
     */
    ResponseResult browseHealthActivity(String token,int page,int limit);

    /**
     * 我的活动  type 1=待审核；2=待支付；3=待参加；4=已结束；
     * @param token
     * @param bz
     * @return
     */
    ResponseResult browseMyActivity(String token, String bz,int page,int limit);

    /**
     * 健康活动浏览
     * @param token
     * @param hId
     * @return
     */
    ResponseResult browseDetailHealthActivity(String token, String hId,Integer isFaliure);

    /**
     * 参加健康活动
     * @param token
     * @param hr
     * @return
     */
    ResponseResult joinHealthActivity(String token, HealthActivityReport hr);

    /**
     * 活动付费
     * @param token
     * @param rId
     * @return
     */
    ResponseResult payHealthActivity(String token, String rId,String type);
    /**
     * 活动付费===线下
     * @param token
     * @param rId
     * @return
     */
    ResponseResult payPzHealthActivity(String token, String rId,String pzPic);



    /////////////web//////////////

    /**
     * 活动审核
     * @param token
     * @param rId
     * @return
     */
    ResponseEntity checkHealthActivity(String token, String rId);

    /**
     * 添加健康活动
     * @param token
     * @param ha
     * @return
     */
    ResponseEntity insertHealthActivity(String token, HealthActivity ha);

    /**
     * 修改健康活动
     * @param token
     * @param ha
     * @return
     */
    ResponseEntity updateHealthActivity(String token, HealthActivity ha);

    /**
     * 删除健康活动
     * @param token
     * @param hId
     * @return
     */
    ResponseEntity deleteHealthActivity(String token, String hId);


}