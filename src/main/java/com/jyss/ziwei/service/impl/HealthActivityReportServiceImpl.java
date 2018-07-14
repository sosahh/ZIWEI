package com.jyss.ziwei.service.impl;

import com.jyss.ziwei.entity.HealthActivity;
import com.jyss.ziwei.entity.HealthActivityReport;
import com.jyss.ziwei.entity.ResponseResult;
import com.jyss.ziwei.mapper.HealthActivityReportMapper;
import com.jyss.ziwei.service.HealthActivityReportService;
import com.jyss.ziwei.service.HealthActivityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HealthActivityReportServiceImpl implements HealthActivityReportService {
    @Autowired
    private HealthActivityReportMapper  hrMapper;

    @Override
    public int insertReport(HealthActivityReport healthActivityReport) {
        return hrMapper.insertReport(healthActivityReport);
    }

    @Override
    public int updateByPrimaryKey(HealthActivityReport healthActivityReport) {
        return hrMapper.updateByPrimaryKey(healthActivityReport);
    }

    @Override
    public int deleteActivityReport(@Param("id") String id) {
        return hrMapper.deleteActivityReport(id);
    }

    @Override
    public List<HealthActivityReport> getActivityReport(@Param("status") String status,@Param("zfType") String zfType, @Param("uId") String uId, @Param("rId") String rId) {
        return hrMapper.getActivityReport(status,zfType,uId,rId);
    }

    @Override
    public List<HealthActivityReport> getActivityReportRefuseSh(@Param("zfType") String zfType, @Param("uId") String uId, @Param("rId") String rId) {
        return hrMapper.getActivityReportRefuseSh(zfType,uId,rId);
    }

    @Override
    public List<HealthActivityReport> getPayActivityReport(@Param("status") String status, @Param("zfType") String zfType, @Param("uId") String uId, @Param("rId") String rId, @Param("orderSn") String orderSn) {
        return hrMapper.getPayActivityReport(status,zfType,uId,rId,orderSn);
    }
}