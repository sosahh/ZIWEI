package com.jyss.ziwei.service;

import com.jyss.ziwei.entity.HealthActivityReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HealthActivityReportService {

    int insertReport(HealthActivityReport healthActivityReport);

    int updateByPrimaryKey(HealthActivityReport healthActivityReport);

    int deleteActivityReport(@Param("id") String id);

    List<HealthActivityReport> getActivityReport(@Param("status") String status,  @Param("zfType") String zfType,
                                                 @Param("uId") String uId, @Param("rId") String rId);
    List<HealthActivityReport> getActivityReportRefuseSh(@Param("zfType") String zfType,
                                                         @Param("uId") String uId, @Param("rId") String rId);


    List<HealthActivityReport> getPayActivityReport(@Param("status") String status,@Param("zfType") String zfType,
                                                    @Param("uId") String uId, @Param("rId") String rId, @Param("orderSn") String orderSn);
}