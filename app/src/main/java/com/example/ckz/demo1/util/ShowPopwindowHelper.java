package com.example.ckz.demo1.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.ckz.demo1.R;

/**
 * Created by CKZ on 2017/6/25.
 */

public class ShowPopwindowHelper {
    private Context mContext;
    private LayoutInflater inflater;
    private View popView;
    private PopupWindow popupWindow;
    public ShowPopwindowHelper(Context mContext){
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    public void showBottomPopwindow(int layoutId, View parent){
        popView = inflater.inflate(layoutId,null);
        popupWindow = new PopupWindow(popView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.Animation);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
    }

    public View getPopView(){
        if (popView!=null){
            return popView;
        }
        return null;
    }
    public void setOnClick(int resId,View.OnClickListener listener){
        popView.findViewById(resId).setOnClickListener(listener);
    }
    public void dismiss(){
       if (popupWindow !=null){
           popupWindow.dismiss();
       }
    }

    public void setText(int viewId,CharSequence text){
        View view = popView.findViewById(viewId);
        if (view instanceof TextView){
            ((TextView)view).setText(text);
        }
    }


}
