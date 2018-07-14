package com.jyss.ziwei.entity;

import java.io.Serializable;
import java.util.Date;

public class BloodSugar implements Serializable {

    private Integer id;
    private Long uId;
    private Integer bzType;
    private String stData;
    private Date testTime;
    private String note;
    private Integer status;
    private Integer type;
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

    public Integer getBzType() {
        return bzType;
    }

    public void setBzType(Integer bzType) {
        this.bzType = bzType;
    }

    public String getStData() {
        return stData;
    }

    public void setStData(String stData) {
        this.stData = stData == null ? null : stData.trim();
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}