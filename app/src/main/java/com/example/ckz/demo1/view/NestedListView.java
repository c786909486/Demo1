package com.example.ckz.demo1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.List;

/**
 * Created by CKZ on 2017/5/16.
 */

public class NestedListView extends ListView {
    public NestedListView(Context context) {
        super(context);
    }

    public NestedListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 3, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
