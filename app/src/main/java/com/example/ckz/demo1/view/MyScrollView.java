package com.example.ckz.demo1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by CKZ on 2017/6/20.
 */

public class MyScrollView extends ScrollView {
    private OnScrollChanged mOnScrollChanged;
    public MyScrollView(Context context) {
        this(context,null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onScrollChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY);
        if (mOnScrollChanged != null)
            mOnScrollChanged.onScroll(scrollX, scrollY, oldScrollX, oldScrollY);
    }

    public void setOnScrollChanged(OnScrollChanged onScrollChanged){
        this.mOnScrollChanged = onScrollChanged;
    }
    public interface OnScrollChanged{
        void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }
}
