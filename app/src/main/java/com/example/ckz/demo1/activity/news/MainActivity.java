package com.example.ckz.demo1.activity.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.cookbook.CookbookMainActivity;
import com.example.ckz.demo1.activity.express.ExpressMainActivity;
import com.example.ckz.demo1.activity.user.LoginAcrivity;
import com.example.ckz.demo1.activity.user.UserCenterActivity;
import com.example.ckz.demo1.fragment.news.MainFragment;
import com.example.ckz.demo1.fragment.news.NewsCollectionFragment;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.view.CircleImageView;

import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    /**
     * UI
     */
    public static DrawerLayout mDrawer;
    private NavigationView mNavigation;
    /**
     * fragment
     */
    private MainFragment mainFragment;
    private NewsCollectionFragment collectionFragment;

    private FragmentManager fm;
    private FragmentTransaction mTransaction;

    /**
     * headerView
     */
    private View headerView;
    private CircleImageView mUserIcon;
    private TextView mUserName;

    private  MyUserModule userModule;

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
       setUserData();
    }

    private void setUserData() {
         userModule = BmobUser.getCurrentUser(MyUserModule.class);
        if (userModule == null){
            mUserIcon.setImageResource(R.mipmap.user_normal_icon);
            mUserName.setText("点击登陆");
        }else {
            mUserName.setText(userModule.getUserNicheng());
        }
    }

    private void initView(){
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigation = (NavigationView) findViewById(R.id.nav_view);
        mNavigation.setNavigationItemSelectedListener(this);
        fm = getSupportFragmentManager();
        mNavigation.getMenu().findItem(R.id.watch_news).setChecked(true);
        headerView = mNavigation.inflateHeaderView(R.layout.nav_header_main);
        mUserIcon = (CircleImageView) headerView.findViewById(R.id.user_icon);
        mUserName = (TextView) headerView.findViewById(R.id.user_name);
        mUserIcon.setOnClickListener(this);
        mUserName.setOnClickListener(this);
        mainFragment = new MainFragment();
        fm.beginTransaction().replace(R.id.content_fragment,mainFragment).commit();
    }



    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (!mainFragment.isVisible()){
                //mainFragment = new MainFragment();
                fm.beginTransaction().replace(R.id.content_fragment,mainFragment).commit();
                mNavigation.getMenu().findItem(R.id.watch_news).setChecked(true);
            }else {
                super.onBackPressed();
            }
        }
    }

    @SuppressLint("CommitTransaction")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        mTransaction = fm.beginTransaction();
       // hideAllFragment(mTransaction);
        switch (id){
            case R.id.watch_news:

                Log.d("currentClick",id+"");
                    mainFragment = new MainFragment();
                    mTransaction.replace(R.id.content_fragment,mainFragment);

                mDrawer.closeDrawer(mNavigation);
                break;
            case R.id.news_collection:
                Log.d("currentClick","收藏");
                    collectionFragment = new NewsCollectionFragment();
                    mTransaction.replace(R.id.content_fragment,collectionFragment);
                    mDrawer.closeDrawer(mNavigation);
                break;
            case R.id.watch_cookbook:
                Intent intent = new Intent(MainActivity.this,CookbookMainActivity.class);
                startActivity(intent);
                mDrawer.closeDrawer(mNavigation);
                break;
            case R.id.query_express:
                Intent expressIntent = new Intent(MainActivity.this, ExpressMainActivity.class);
                startActivity(expressIntent);
                mDrawer.closeDrawer(mNavigation);
                break;
            case R.id.nav_send:
                MyUserModule.logOut();
                setUserData();
                break;
            default:
                mDrawer.closeDrawer(GravityCompat.START);
                break;
        }
        mTransaction.commit();

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUserData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_icon:
                //如果已经登陆，跳转到个人中心；没登陆跳转到登陆界面
                MyUserModule userModule = BmobUser.getCurrentUser(MyUserModule.class);
                if (userModule == null){
                    Intent intent = new Intent(MainActivity.this, LoginAcrivity.class);
                    startActivity(intent);

                }else {
                    //跳转到个人中心
                    Intent userIntent = new Intent(MainActivity.this, UserCenterActivity.class);
                    startActivity(userIntent);
                }
                mDrawer.closeDrawer(mNavigation);
                break;
            case R.id.user_name:
                mUserIcon.performClick();
                break;
        }
    }
}
