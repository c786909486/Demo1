package com.example.ckz.demo1.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by CKZ on 2017/6/30.
 */

public class LeftDrawableUtils {
    /**
     * 设置drawableTop
     */
    public static void setDrawbleTop(Context context,TextView textView, int resId){
        float density = context.getResources().getDisplayMetrics().density;
        int size = (int) (20*density);

        Drawable drawable = context.getResources().getDrawable(resId);
        Rect rect = new Rect();
        rect.set(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.setBounds(rect);
        textView.setCompoundDrawables(null,drawable,null,null);
    }

    /**
     * 设置drawableLeft
     */
    public static void setDrawbleLeft(Context context,TextView textView, int resId){
        float density = context.getResources().getDisplayMetrics().density;
        int size = (int) (20*density);

        Drawable drawable = context.getResources().getDrawable(resId);
        Rect rect = new Rect();
        rect.set(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.setBounds(rect);
        textView.setCompoundDrawables(drawable,null,null,null);
    }
    /**
     * 设置drawableRight
     */
    public static void setDrawbleRight(Context context,TextView textView, int resId){
        float density = context.getResources().getDisplayMetrics().density;
        int size = (int) (20*density);
        Rect rect = new Rect();
        rect.set(0,0,size,size);
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(rect);
        textView.setCompoundDrawables(null,null,drawable,null);
    }

    /**
     * 设置drawableBottom
     */
    public static void setDrawbleBottom(Context context,TextView textView, int resId){
        float density = context.getResources().getDisplayMetrics().density;
        int size = (int) (20*density);
        Rect rect = new Rect();
        rect.set(0,0,size,size);
        Drawable drawable = context.getResources().getDrawable(resId);
        drawable.setBounds(rect);
        textView.setCompoundDrawables(null,null,null,drawable);
    }
    /**
     * 设置带清除按钮的drawableLeft
     */
    public static void setDrawbleLeftWithClear(Context context,TextView textView, int resId, int clearId){
        float density = context.getResources().getDisplayMetrics().density;
        int size = (int) (20*density);
        Rect rect = new Rect();
        rect.set(0,0,size,size);
        Drawable drawable = context.getResources().getDrawable(resId);
        Drawable clearDrawable = context.getResources().getDrawable(clearId);
        drawable.setBounds(rect);
        textView.setCompoundDrawables(drawable,null,clearDrawable,null);
    }
}
