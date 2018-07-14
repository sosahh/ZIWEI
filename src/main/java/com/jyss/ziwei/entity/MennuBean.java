package com.jyss.ziwei.entity;

import java.util.List;

/**
 * Created by lixh on 2018/3/22.
 */
public class MennuBean {

    private String code;//标题排序码
    private String href;//点击链接
    private String title;//UI显示列表标题
    private String icon;//图标
    private boolean spread;//默认false;
    private List<ChildBean> children ;

    public void setChildren(List<ChildBean> children) {
        this.children = children;
    }

    public List<ChildBean> getChildren() {
        return children;
    }

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
