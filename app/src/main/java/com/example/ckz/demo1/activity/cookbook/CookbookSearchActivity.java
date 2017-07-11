package com.example.ckz.demo1.activity.cookbook;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.db.CookbookHistoryBean;
import com.example.ckz.demo1.fragment.cookbook.CookbookHistoryFragment;
import com.example.ckz.demo1.fragment.cookbook.CookbookResultFragment;
import com.example.ckz.demo1.util.StatuBarColorUtil;
import com.example.ckz.demo1.view.ClearEditText;

public class CookbookSearchActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher,CookbookHistoryFragment.CallBackValue{
    private ImageView mBack;
    private ClearEditText mInput;
    private final String TAG = "CookbookSearchActivity";
    private FragmentManager fm;

    private CookbookHistoryFragment history = new CookbookHistoryFragment();
    private CookbookResultFragment result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook_search);
        StatuBarColorUtil.setStatuBarColor(this,R.color.darkWhilte);
        fm = getSupportFragmentManager();
        initView();
        fm.beginTransaction().replace(R.id.search_fragment,history).commit();
        setSearch();
    }

    private void setSearch() {
        mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    startSearch(mInput.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back_btn);
        mInput = (ClearEditText) findViewById(R.id.search_input);
        mBack.setOnClickListener(this);
        mInput.addTextChangedListener(this);
        setDrawbleLeft(mInput,R.drawable.search);
    }

    /**
     * 设置drawableTop
     */
    private void setDrawbleLeft(TextView textView, int resId){
        float density = getResources().getDisplayMetrics().density;
        int size = (int) (25*density);
        Rect rect = new Rect();
        rect.set(0,0,size,size);
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(rect);
        textView.setCompoundDrawables(drawable,null,null,null);
    }

    /**
     * 开始搜索
     */
    private void startSearch(String keyword){
        result = new CookbookResultFragment();
        result.setKeyword(keyword);
        fm.beginTransaction().replace(R.id.search_fragment,result).commit();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0){
            Log.d(TAG,"显示历史纪录");

            fm.beginTransaction().replace(R.id.search_fragment,history).commit();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void getMessage(String value) {
        mInput.setText(value);
        startSearch(value);
    }
}
