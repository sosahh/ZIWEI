package com.jyss.ziwei.entity;

import java.io.Serializable;
import java.util.Date;

public class BloodPressure implements Serializable{

    private Integer id;
    private Long uId;
    private String syData;
    private String diData;
    private Integer status;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getuId() {
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getSyData() {
        return syData;
    }

    public void setSyData(String syData) {
        this.syData = syData == null ? null : syData.trim();
    }

    public String getDiData() {
        return diData;
    }

    public void setDiData(String diData) {
        this.diData = diData == null ? null : diData.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}