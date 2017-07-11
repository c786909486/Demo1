package com.example.ckz.demo1.activity.cookbook;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.fragment.cookbook.CookbookCollectFragment;
import com.example.ckz.demo1.fragment.cookbook.CookbookMainFragment;
import com.example.ckz.demo1.fragment.cookbook.CookbookTypeFragment;
import com.example.ckz.demo1.util.LeftDrawableUtils;
import com.example.ckz.demo1.util.StatuBarColorUtil;

public class CookbookMainActivity extends BaseActivity implements View.OnClickListener{
    /**
     * bottomBarUI
     */
    private TextView mMainBtn;
    private TextView mLikeBtn;
    private TextView mMineBtn;
    private TextView mTypeBtn;



    /**
     * Fragment
     */
    private FragmentManager fm;
    private CookbookMainFragment mainFragment;
    private CookbookTypeFragment typeFragment;
    private CookbookCollectFragment collectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook_main);
        StatuBarColorUtil.setStatuBarColor(this,R.color.darkWhilte);
        fm = getSupportFragmentManager();
        initView();
    }

    private void initView() {
        mMainBtn = (TextView) findViewById(R.id.main_btn);
        mLikeBtn = (TextView) findViewById(R.id.like_btn);
        mMineBtn = (TextView) findViewById(R.id.mine_btn);
        mTypeBtn = (TextView) findViewById(R.id.type_btn);
        LeftDrawableUtils.setDrawbleTop(this,mMainBtn,R.drawable.main_select);
        LeftDrawableUtils.setDrawbleTop(this,mLikeBtn,R.drawable.like_select);
        LeftDrawableUtils.setDrawbleTop(this,mMineBtn,R.drawable.mine_select);
        LeftDrawableUtils.setDrawbleTop(this,mTypeBtn,R.drawable.type_selected);
        mMainBtn.setOnClickListener(this);
        mLikeBtn.setOnClickListener(this);
        mMineBtn.setOnClickListener(this);
        mTypeBtn.setOnClickListener(this);
        mMainBtn.setSelected(true);
        mMainBtn.performClick();
    }
    /**
     * 重置selected
     */
    private void resetSelected(){
        mMainBtn.setSelected(false);
        mLikeBtn.setSelected(false);
        mMineBtn.setSelected(false);
        mTypeBtn.setSelected(false);
    }



    /**
     * 隐藏fragment
     */
    private void hideAllFragment(FragmentTransaction transation){
        if (mainFragment != null) transation.hide(mainFragment);
        if (typeFragment != null) transation.hide(typeFragment);
        if (collectFragment!=null) transation.hide(collectFragment);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction transation = fm.beginTransaction();
        hideAllFragment(transation);
        switch (v.getId()){
            case R.id.main_btn:
                resetSelected();
                mMainBtn.setSelected(true);
                if (mainFragment == null){
                    mainFragment = new CookbookMainFragment();
                    transation.add(R.id.content_fragment,mainFragment);
                }else {
                    transation.show(mainFragment);
                }
                break;
            case R.id.like_btn:
                resetSelected();
                mLikeBtn.setSelected(true);
                if (collectFragment == null){
                    collectFragment = new CookbookCollectFragment();
                    transation.add(R.id.content_fragment,collectFragment);
                }else {
                    transation.show(collectFragment);
                }

                break;
            case R.id.mine_btn:
                resetSelected();
                mMineBtn.setSelected(true);

                break;
            case R.id.type_btn:
                resetSelected();
                mTypeBtn.setSelected(true);
                if (typeFragment == null){
                    typeFragment = new CookbookTypeFragment();
                    transation.add(R.id.content_fragment,typeFragment);
                }else {
                    transation.show(typeFragment);
                }
                break;
        }
        transation.commit();
    }

    @Override
    public void onBackPressed() {
        if (mainFragment!=null&!mainFragment.isVisible()){
            FragmentTransaction transaction = fm.beginTransaction();
            hideAllFragment(transaction);
            transaction.show(mainFragment);
            transaction.commit();
            resetSelected();
            mMainBtn.setSelected(true);
        }else {
            super.onBackPressed();
        }
    }
}
