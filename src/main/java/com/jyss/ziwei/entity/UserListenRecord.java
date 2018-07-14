package com.jyss.ziwei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class UserListenRecord implements Serializable {

    private Integer id;
    private Long uId;
    private Integer knowId;
    private String listenTime;   //已看时长
    private Integer status;
    private Date createTime;
    private Date modifyTime;

    private String titles;    //知识主题
    private String inPic;     //视频路径
    private String vedioTime;    //时长

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

    public Integer getKnowId() {
        return knowId;
    }

    public void setKnowId(Integer knowId) {
        this.knowId = knowId;
    }

    public String getListenTime() {
        return listenTime;
    }

    public void setListenTime(String listenTime) {
        this.listenTime = listenTime == null ? null : listenTime.trim();
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getInPic() {
        return inPic;
    }

    public void setInPic(String inPic) {
        this.inPic = inPic;
    }

    public String getVedioTime() {
        return vedioTime;
    }

    public void setVedioTime(String vedioTime) {
        this.vedioTime = vedioTime;
    }

}