package com.example.ckz.demo1.activity.user;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.view.CircleImageView;

import cn.bmob.v3.BmobUser;

public class UserCenterActivity extends BaseActivity implements View.OnClickListener{
    /**
     * toolBar
     */
    private Toolbar mToolBar;
    private TextView mTopUserName;

    /**
     * appbar
     */
    private AppBarLayout mAppBar;
    private ImageView mUserBg;
    private CircleImageView mUserIcon;
    private TextView mUserName;
    private TabLayout mTab;
    /**
     * viewpager
     */
    private ViewPager mPager;

    private MyUserModule user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        user = BmobUser.getCurrentUser(MyUserModule.class);
        initView();
        setClick();
        setScroll();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUserData();
    }

    private void initView() {
        //toolbar
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mTopUserName = (TextView) findViewById(R.id.user_name_top);
        //appbar
        mAppBar = (AppBarLayout) findViewById(R.id.app_bars);
        mUserBg = (ImageView) findViewById(R.id.user_bg);
        mUserIcon = (CircleImageView) findViewById(R.id.user_icon);
        mUserName = (TextView) findViewById(R.id.user_name);
        mTab = (TabLayout) findViewById(R.id.user_tab);
        //viewpager
        mPager = (ViewPager) findViewById(R.id.user_view_pager);
    }

    /**
     * 设置用户头像等信息
     */
    private void setUserData(){
        mUserName.setText(user.getUserNicheng());
        //设置用户头像
        if (user.getUserIcon() == null){
            mUserIcon.setImageResource(R.mipmap.user_normal_icon);
        }else {
            Glide.with(this).load(user.getUserIcon()).into(mUserIcon);
        }
        //用户背景图
        if (user.getUserBg() == null){
            mUserBg.setImageResource(R.mipmap.bg);
        }else {
            Glide.with(this).load(user.getUserBg()).into(mUserBg);
        }
    }

    /**
     * 设置下滑显示顶部用户名
     */
    private CollapsingToolbarLayoutState state;

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }
    private void setScroll(){
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        mTopUserName.setText("");
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开

                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        mTopUserName.setText(user.getUserNicheng());
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
                            mTopUserName.setText("");
                        }else {
                            mTopUserName.setText(user.getUserNicheng());
                        }

                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }

            }
        });
    }

    private void setClick() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        mUserIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.user_icon:
                //跳转到信息设置

                break;
        }
    }
}
