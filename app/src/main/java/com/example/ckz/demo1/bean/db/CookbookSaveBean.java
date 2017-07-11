package com.example.ckz.demo1.bean.db;

import org.litepal.crud.DataSupport;

/**
 * Created by CKZ on 2017/6/14.
 */

public class CookbookSaveBean extends DataSupport{
    private String cookId;
    private String picUrl;
    private String cookName;
    private String tag;

    public String getCookId() {
        return cookId;
    }

    public void setCookId(String cookId) {
        this.cookId = cookId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCookName() {
        return cookName;
    }

    public void setCookName(String cookName) {
        this.cookName = cookName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
