package com.jyss.ziwei.entity;

import java.io.Serializable;
import java.util.Date;

public class UserOgtt implements Serializable{
    private Integer id;
    private Integer uId;
    private String ogttTime;
    private Date createdAt;
    private Date lastModifyTime;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getOgttTime() {
        return ogttTime;
    }

    public void setOgttTime(String ogttTime) {
        this.ogttTime = ogttTime;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}