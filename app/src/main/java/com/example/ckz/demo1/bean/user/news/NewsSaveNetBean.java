package com.example.ckz.demo1.bean.user.news;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by CKZ on 2017/7/6.
 */

public class NewsSaveNetBean  extends BmobObject implements Serializable {
    /**
     * title : 中国开闸放水27天解救越南旱灾
     * time : 2016-03-16 07:23
     * src : 中国网
     * category : mil
     * pic : http://api.jisuapi.com/news/upload/20160316/105123_31442.jpg
     * content : 　原标题：防总：应越南请求 中方启动澜沧江水电站水量应急调度　记者从国家防总获悉，应越南社会主义共和国请求，我方启动澜沧江梯级水电站水量应急调度，缓解湄公河流域严重旱情。3月15日8时，澜沧江景洪水电站下泄流量已加大至2190立方米每秒，标志着应越方请求，由我方实施的澜沧江梯级水电站水量应急调度正式启动。
     * url : http://mil.sina.cn/zgjq/2016-03-16/detail-ifxqhmve9235380.d.html?vt=4&pos=108
     * weburl : http://mil.news.sina.com.cn/china/2016-03-16/doc-ifxqhmve9235380.shtml
     */

    @com.google.gson.annotations.SerializedName("title")
    private String title;
    @com.google.gson.annotations.SerializedName("time")
    private String time;
    @com.google.gson.annotations.SerializedName("src")
    private String src;
    @com.google.gson.annotations.SerializedName("category")
    private String category;
    @com.google.gson.annotations.SerializedName("pic")
    private String pic;
    @com.google.gson.annotations.SerializedName("content")
    private String content;
    @com.google.gson.annotations.SerializedName("url")
    private String url;
    @com.google.gson.annotations.SerializedName("weburl")
    private String weburl;
    @com.google.gson.annotations.SerializedName("userPhone")
    private String userPhone;

    private String userId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsSaveNetBean that = (NewsSaveNetBean) o;

        return url != null ? url.equals(that.url) : that.url == null;

    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }


}
