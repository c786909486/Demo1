package com.example.ckz.demo1.view;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by CKZ on 2017/8/6.
 */

public class LengthShowEditText extends RelativeLayout implements TextWatcher {
    private EditText mEdit;
    private TextView mText;

    private int inputLength = 30;
    private int num = 30;

    public LengthShowEditText(Context context) {
        this(context,null);
    }

    public LengthShowEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public String getText(){
        return mEdit.getText().toString();
    }

    private void init(Context context){
        this.setMinimumHeight(20);
        mEdit = new EditText(context);
        mEdit.setBackgroundColor(Color.WHITE);
        mEdit.setEnabled(true);
        mEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputLength)});
        RelativeLayout.LayoutParams editParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        this.addView(mEdit,editParams);
        mText = new TextView(context);
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        textParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        textParams.setMargins(0,0,dip2px(context,16),dip2px(context,10));
        this.addView(mText,textParams);
        mEdit.addTextChangedListener(this);
        mText.setText(String.valueOf(num));
        setPadding(dip2px(context,16),dip2px(context,10),dip2px(context,16),dip2px(context,10));
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        num = inputLength - charSequence.length();
        mText.setText(String.valueOf(num));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public int dip2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
