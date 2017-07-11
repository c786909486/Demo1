package com.example.ckz.demo1.util;


import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CKZ on 2017/6/23.
 */

public class MyTimeUtils {

    /**
     * 获取当前时间， 输出 yyyy年MM月dd日   HH:mm:ss
     * @return str
     */
    public static String getNowTime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter   =   new   SimpleDateFormat   ("yyyy-M-d   H:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String   str   =   formatter.format(curDate);
        return str;
    }


    public static String getYewstoday(String date){
        String d = date.substring(0,10);
        String[] arrayD = d.split("-");
        String n = getNowTime().substring(0,10);
        String[] arrayN = d.split("-");


        int time = Integer.valueOf(deletString(arrayD[2])) - Integer.valueOf(deletString(arrayN[2]));
        String str;
        if (time==0){
            str = " 今天\n"+date.substring(10);
        }else if (time==-1){
            str = " 昨天\n"+date.substring(10);
        }else if (time == -2){
            str = " 前天\n"+date.substring(10);
        } else if (time == 1) {
            str = " 明天\n"+date.substring(10);
        } else if (time == 2) {
            str = " 后天\n"+date.substring(10);
        }else {
            str = date.substring(0,10)+"\n"+date.substring(10);
        }
        return str;
    }

    private static String deletString(String s){
        String str;
        if (s.substring(1).equals("")){
            str = s.substring(0,1);
        }else {
            str = s.substring(0,2);
        }
        return str;
    }

}
