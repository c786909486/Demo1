package com.example.ckz.demo1.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by CKZ on 2017/6/21.
 */

public class MyEditText extends EditText {
    private OnDrawableListener mListener;
    private OnDrawableLeftListener mLeftListener;
    private OnDrawableRightListener mRightListener;

    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;


    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnDrawableListener(OnDrawableListener listener){
        this.mListener = listener;
    }

    public void setOnDrawableLeftListener(OnDrawableLeftListener listener){
        this.mLeftListener = listener;
    }

    public void setOnDrawableRightListener(OnDrawableRightListener listener){
        this.mRightListener = listener;
    }

    public interface OnDrawableListener {
        void onDrawableLeftClick(View view);

        void onDrawableRightClick(View view);
    }

    public interface OnDrawableLeftListener {
        void onDrawableLeftClick(View view);
    }

    public interface OnDrawableRightListener {
        void onDrawableRightClick(View view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                if (mRightListener!=null){
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight!=null&& event.getRawX()>=getRight()-drawableRight.getBounds().width()){
                        mRightListener.onDrawableRightClick(this);
                        return true;
                    }
                }
                if (mLeftListener!=null){
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft!=null&&event.getRawX()<=getLeft()+drawableLeft.getBounds().width()){
                        mLeftListener.onDrawableLeftClick(this);
                        return true;
                    }
                }
                if (mListener!=null){
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight!=null&& event.getRawX()>=getRight()-drawableRight.getBounds().width()){
                        mRightListener.onDrawableRightClick(this);
                        return true;
                    }
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft!=null&&event.getRawX()<=getLeft()+drawableLeft.getBounds().width()) {
                        mLeftListener.onDrawableLeftClick(this);
                        return true;
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
