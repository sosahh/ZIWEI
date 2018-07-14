package com.jyss.ziwei.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class HealthNews implements Serializable {

    private Integer id;
    private Integer categoryId;    //类目id
    private String titles;      //标题
    private String pic;
    private String subtitle;        //副标题
    private String inPic;
    private Date createdAt;
    private Date lastModifyTime;
    private Integer status;
    private Integer type;     //1=公开，2=不公开
    private Integer form;     //1=文字，2=图片，3=视频
    private String contents;
    private String vedioTime;    //视频时长
    private Integer recommend;    //1=非推荐，2=推荐

    private String scCount;     //收藏数
    private String dzCount;     //点赞数
    private String plCount;     //评论数
    private String fxCount;     //分享数
    private Integer isCollection;       //是否收藏  0=未收藏，1=已收藏
    private Integer isPraise;       //是否点赞  0=未点赞，1=已点赞


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles == null ? null : titles.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getInPic() {
        return inPic;
    }

    public void setInPic(String inPic) {
        this.inPic = inPic == null ? null : inPic.trim();
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getForm() {
        return form;
    }

    public void setForm(Integer form) {
        this.form = form;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

    public String getVedioTime() {
        return vedioTime;
    }

    public void setVedioTime(String vedioTime) {
        this.vedioTime = vedioTime;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getScCount() {
        return scCount;
    }

    public void setScCount(String scCount) {
        this.scCount = scCount;
    }

    public String getDzCount() {
        return dzCount;
    }

    public void setDzCount(String dzCount) {
        this.dzCount = dzCount;
    }

    public String getPlCount() {
        return plCount;
    }

    public void setPlCount(String plCount) {
        this.plCount = plCount;
    }

    public String getFxCount() {
        return fxCount;
    }

    public void setFxCount(String fxCount) {
        this.fxCount = fxCount;
    }

    public Integer getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(Integer isCollection) {
        this.isCollection = isCollection;
    }

    public Integer getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(Integer isPraise) {
        this.isPraise = isPraise;
    }
}