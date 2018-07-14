package com.jyss.ziwei.entity;

import java.io.Serializable;

public class BConstant implements Serializable {

    private Integer id;
    private String bzType;
    private String bzId;
    private String bzValue;
    private Integer pId;
    private String bzInfo;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBzType() {
        return bzType;
    }

    public void setBzType(String bzType) {
        this.bzType = bzType == null ? null : bzType.trim();
    }

    public String getBzId() {
        return bzId;
    }

    public void setBzId(String bzId) {
        this.bzId = bzId == null ? null : bzId.trim();
    }

    public String getBzValue() {
        return bzValue;
    }

    public void setBzValue(String bzValue) {
        this.bzValue = bzValue == null ? null : bzValue.trim();
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getBzInfo() {
        return bzInfo;
    }

    public void setBzInfo(String bzInfo) {
        this.bzInfo = bzInfo == null ? null : bzInfo.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}