package com.example.ckz.demo1.user;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by CKZ on 2017/7/4.
 */

public class MyUserModule extends BmobUser {
    //用户名
    private String userName;
    //用户昵称
    private String userNicheng;
    //性别
    private String userSex;
    //用户id
    private String userId;
    //出生年月
    private String userBirthday;
    //个性签名
    private String userDetail;
    //用户头像
    private BmobFile userIcon;
    //用户背景图
    private BmobFile userBg;

    public BmobFile getUserBg() {
        return userBg;
    }

    public void setUserBg(BmobFile userBg) {
        this.userBg = userBg;
    }

    public BmobFile getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(BmobFile userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNicheng() {
        return userNicheng;
    }

    public void setUserNicheng(String userNicheng) {
        this.userNicheng = userNicheng;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserId() {
        return userId;
    }


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(String userDetail) {
        this.userDetail = userDetail;
    }
}
