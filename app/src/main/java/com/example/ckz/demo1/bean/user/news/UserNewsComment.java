package com.example.ckz.demo1.bean.user.news;

import com.example.ckz.demo1.user.MyUserModule;

import cn.bmob.v3.BmobObject;

/**
 * Created by CKZ on 2017/7/13.
 */

public class UserNewsComment extends BmobObject{

    private MyUserModule userModule;

    private CommentNews newsSaveNetBean;

    private String newsComment;

    private UserNewsComment preComment;

    private UserNewsComment preComment2;

    private MyUserModule preUser;

    private Integer likes;

    private Integer hates;

    private Integer commentSize;

    private int commentId;

    private String preUserName;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserNewsComment that = (UserNewsComment) o;

        if (commentId != that.commentId) return false;
        return newsComment != null ? newsComment.equals(that.newsComment) : that.newsComment == null;

    }

    @Override
    public int hashCode() {
        int result = userModule != null ? userModule.hashCode() : 0;
        result = 31 * result + commentId;
        return result;
    }

    public String getPreUserName() {
        return preUserName;
    }

    public void setPreUserName(String preUserName) {
        this.preUserName = preUserName;
    }

    public MyUserModule getPreUser() {
        return preUser;
    }

    public void setPreUser(MyUserModule preUser) {
        this.preUser = preUser;
    }

    public Integer getCommentSize() {
        return commentSize;
    }

    public void setCommentSize(Integer commentSize) {
        this.commentSize = commentSize;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public UserNewsComment getPreComment() {
        return preComment;
    }

    public void setPreComment(UserNewsComment preComment) {
        this.preComment = preComment;
    }

    public UserNewsComment getPreComment2() {
        return preComment2;
    }

    public void setPreComment2(UserNewsComment preComment2) {
        this.preComment2 = preComment2;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getHates() {
        return hates;
    }

    public void setHates(Integer hates) {
        this.hates = hates;
    }

    public MyUserModule getUserModule() {
        return userModule;
    }

    public void setUserModule(MyUserModule userModule) {
        this.userModule = userModule;
    }

    public CommentNews getCommentNews() {
        return newsSaveNetBean;
    }

    public void setCommentNews(CommentNews newsSaveNetBean) {
        this.newsSaveNetBean = newsSaveNetBean;
    }

    public String getNewsComment() {
        return newsComment;
    }

    public void setNewsComment(String newsComment) {
        this.newsComment = newsComment;
    }
}
