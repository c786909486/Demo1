package com.example.ckz.demo1.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import com.example.ckz.demo1.adapter.pagerAdapter.MyPagerAdapter;
import com.example.ckz.demo1.fragment.user.UserNewsCollectFragment;
import com.example.ckz.demo1.fragment.user.UserNewsCommentFragment;
import com.example.ckz.demo1.interfaces.OnRefreshSucess;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.util.LogUtils;
import com.example.ckz.demo1.view.CircleImageView;
import com.example.ckz.demo1.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

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
    private MyPagerAdapter myPagerAdapter;

    private List<Fragment> mList;
    private List<String> mTitle;

    private MyUserModule user;
    private LoadingDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        initView();
        setClick();
        setScroll();

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = BmobUser.getCurrentUser(MyUserModule.class);
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
        mTab.setTabMode(TabLayout.MODE_FIXED);
        //viewpager
        mPager = (ViewPager) findViewById(R.id.user_view_pager);
        setViewPager();
        loading = new LoadingDialog(this);
    }

    /**
     * 设置viewpager
     */
    private void setViewPager(){
        mTitle = new ArrayList<>();
        mList = new ArrayList<>();
        mTitle.add("新闻收藏");
        mTitle.add("我的评论");
        UserNewsCollectFragment userNewsCollectFragment = new UserNewsCollectFragment();
        userNewsCollectFragment.setOnRefreshSucessListener(new OnRefreshSucess() {
            @Override
            public void onRefreshStaet() {
                LogUtils.d("UserCenterActivity","开始刷新");
                loading.show();
            }

            @Override
            public void onRefreshSucess(boolean sucess) {
                if (sucess){
                    LogUtils.d("UserCenterActivity","刷新成功");
                }else {
                    LogUtils.d("UserCenterActivity","刷新失败");
                }
                loading.cancel();
            }
        });
        mList.add(userNewsCollectFragment);
        UserNewsCommentFragment userNewsCommentFragment = new UserNewsCommentFragment();
        mList.add(userNewsCommentFragment);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(),mList,mTitle);
        mPager.setAdapter(myPagerAdapter);
        mTab.setupWithViewPager(mPager);

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
            Glide.with(this).load(user.getUserIcon().getUrl()).into(mUserIcon);
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
                            mTopUserName.setText(user.getUserNicheng());
                        }else {
                            mTopUserName.setText("");
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
                startActivity(new Intent(UserCenterActivity.this,UserSettingActivity.class));
                break;
        }
    }
}
