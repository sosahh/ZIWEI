package com.jyss.ziwei.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by lixh on 2018/3/29.
 */
public class TreeBean {
    private  String id;
    private  String text;
    private  Map<String,Boolean> state;//包括opened，selected
    private  Boolean checked;
    private  Boolean hasParent;
    private  Boolean hasChildren;
    private  String parentId;
    private List<TreeBean> children;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getHasChildren() {
        return hasChildren;
    }
    public void setHasChildren(Boolean hasChildren) {
        this.hasChildren = hasChildren;
    }


    public void setChildren(List<TreeBean> children) {
        this.children = children;
    }
    public List<TreeBean> getChildren() {
        return children;
    }

    public Boolean getHasParent() {
        return hasParent;
    }
    public void setHasParent(Boolean hasParent) {
        this.hasParent = hasParent;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getParentId() {
        return parentId;
    }

    public Map<String, Boolean> getState() {
        return state;
    }

    public void setState(Map<String, Boolean> state) {
        this.state = state;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


}
