package com.jyss.ziwei.entity;

import java.io.Serializable;
import java.util.Date;

public class HealthActivityReport implements Serializable {

    private Integer id;
    private Integer rId;
    private Long uId;
    private String tel;
    private String reportName;
    private Float money;
    private String pzPic;
    private Date createdAt;
    private Date lastModifyTime;
    private Integer status;
    private String contents;
    private Integer zfType;
    private Integer hType;///活动类型=1=预约-审核-付钱；2=预约-付钱，无审核；
    private String orderSn;
    private String hTitle;

    public String gethTitle() {
        return hTitle;
    }

    public void sethTitle(String hTitle) {
        this.hTitle = hTitle;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer gethType() {
        return hType;
    }

    public void sethType(Integer hType) {
        this.hType = hType;
    }

    public Integer getrId() {
        return rId;
    }

    public void setrId(Integer rId) {
        this.rId = rId;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public Integer getZfType() {
        return zfType;
    }

    public void setZfType(Integer zfType) {
        this.zfType = zfType;
    }

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName == null ? null : reportName.trim();
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getPzPic() {
        return pzPic;
    }

    public void setPzPic(String pzPic) {
        this.pzPic = pzPic == null ? null : pzPic.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }
}