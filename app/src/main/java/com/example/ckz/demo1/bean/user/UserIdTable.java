package com.example.ckz.demo1.bean.user;

import cn.bmob.v3.BmobObject;

/**
 * Created by CKZ on 2017/7/7.
 */

public class UserIdTable extends BmobObject {

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
