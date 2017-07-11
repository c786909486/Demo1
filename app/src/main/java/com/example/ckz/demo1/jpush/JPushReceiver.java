package com.example.ckz.demo1.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.activity.news.MainActivity;
import com.example.ckz.demo1.activity.news.NewsDetilActivity;
import com.example.ckz.demo1.bean.news.NewsBean;
import com.example.ckz.demo1.bean.push.PushMessage;
import com.example.ckz.demo1.util.LogUtils;
import com.example.ckz.demo1.util.Util;
import com.example.vuandroidadsdk.adutil.Utils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by CKZ on 2017/6/26.
 * @fuction 用来接受极光SDK推送给app的消息
 */

public class JPushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)){
            //不跳转
            LogUtils.d("receiver","不跳转");
        }else if (intent.getAction().equals(JPushInterface.ACTION_NOTIFICATION_OPENED)){
            //跳转
            LogUtils.d("receiver","跳转");
            //NewsBean newsBean = JSON.parseObject(bundle.getString(JPushInterface.EXTRA_EXTRA), NewsBean.class);

            if (Util.getCurrentTask(context)){
                //应用已经启动
                Intent pushIntent = new Intent(context,PushMessageActivity.class);
                pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //pushIntent.putExtra("NewsData",newsBean.getResult().getList().get(0));
                context.startActivity(pushIntent);
            }else {
                //应用未启动
                Intent mainIntent = new Intent(context, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent pushIntent = new Intent(context,PushMessageActivity.class);
                pushIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               // pushIntent.putExtra("NewsData",newsBean.getResult().getList().get(0));
                context.startActivities(new Intent[]{mainIntent,pushIntent});
            }
        }
    }
}
