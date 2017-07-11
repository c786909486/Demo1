package com.example.ckz.demo1.bean.push;

/**
 * Created by CKZ on 2017/6/26.
 */

public class PushMessage {

    //消息类型，类型为2代表需要登陆类型
    public String messageType = null;
    //链接，打开地url地址
    public String messageUrl = null;
    //详情内容，要在消息推送界面显示的text；
    public String messageContent = null;
}
