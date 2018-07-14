package com.jyss.ziwei.entity;

/**
 * Created by lixh on 2018/3/26.
 */
public class ChildBean {
    private String code;//标题排序码
    private String href;//点击链接
    private String title;//UI显示列表标题
    private String icon;//图标
    private boolean spread;//默认false;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getSpresd() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }
}
