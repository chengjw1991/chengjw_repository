package com.cheng.beans;

import java.io.Serializable;
import java.util.List;

/**
 * @Author ChengJW
 * 2020/12/6/006
 */
public class permissionVO implements Serializable {

    private static final long serialVersionUID = -8962244041214347334L;
    private Integer id;
    private String title;

    private List<permissionVO> children;

    public permissionVO() {
    }

    public permissionVO(Integer id, String title) {
        this.id = id;
        this.title = title;
    }



    public permissionVO(Integer id, String title, List<permissionVO> children) {
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

    public List<permissionVO> getChildren() {
        return children;
    }

    public void setChildren(List<permissionVO> children) {
        this.children = children;
    }



    @Override
    public String toString() {
        return "permissionVO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", children=" + children +
                '}';
    }
}
