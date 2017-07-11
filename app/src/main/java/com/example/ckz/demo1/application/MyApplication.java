package com.example.ckz.demo1.application;

import android.app.Application;
import android.content.Context;


import com.example.ckz.demo1.bean.KeyClass;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

import org.litepal.LitePalApplication;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by CKZ on 2017/5/5.
 */

public class MyApplication extends LitePalApplication{
    private static MyApplication myApplication = null;


    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        initBugly();
        initJpush();
        initUMeng();
       initBmobsdk();
        initBmobSMS();
    }

    public static MyApplication getInstance(){
        return myApplication;
    }


    /**
     * 初始化jPush
     */
    public void initJpush(){
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    /**
     * 初始化友盟sdk
     */
    public void initUMeng(){
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);

    }
    /**
     * 初始化腾讯bugly
     */
    public void initBugly(){
        CrashReport.initCrashReport(getApplicationContext(), KeyClass.BUGLY_KEY, true);
    }


    /**
     * 初始化BmobSDK
     */
    public void initBmobsdk(){
        Bmob.initialize(this, KeyClass.BMOB_KEY);
    }

    public void initBmobSMS(){
        BmobSMS.initialize(getApplicationContext(),KeyClass.BMOB_KEY);
    }
}
