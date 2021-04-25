package com.cheng.beans;

import java.util.List;

/**
 * @author chengjw
 * @date 2020-12-10 11:29
 * 查询本单位所有的角色信息及其对应的权限信息
 * 由于前端tree渲染数据源有固定的格式
 * 故自定义此类
 */
public class RolePermInfo {

    private Integer id;
    private String title;
    private List<Object> children;

    public RolePermInfo() {
    }

    public RolePermInfo(Integer id, String title, List<Object> children) {
        this.id = id;
        this.title = title;
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Object> getChildren() {
        return children;
    }

    public void setChildren(List<Object> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "RolePermInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", children=" + children +
                '}';
    }
}
