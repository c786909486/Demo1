package com.example.ckz.demo1.bean.cookbook;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by CKZ on 2017/6/6.
 */

public class ListBean extends DataSupport implements Serializable{
    /**
     * classid : 2
     * name : 减肥
     * parentid : 1
     */

    @SerializedName("classid")
    private String classid;
    @SerializedName("name")
    private String name;
    @SerializedName("parentid")
    private String parentid;

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
}
