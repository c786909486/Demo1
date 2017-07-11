package com.example.ckz.demo1.activity.express;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.bean.db.ExpressSaveBean;
import com.example.ckz.demo1.fragment.express.ExpressHistoryFragment;
import com.example.ckz.demo1.adapter.pagerAdapter.MyPagerAdapter;
import com.example.ckz.demo1.util.StatuBarColorUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ExpressHistoryActivity extends BaseActivity implements View.OnClickListener{
    private TabLayout mTab;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;
    private List<Fragment> mList;
    private List<String> mTitle;

    private ExpressHistoryFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_history);
        StatuBarColorUtil.setStatuBarColor(this,R.color.blue_dark);
        initView();
        setClick();
    }

    private void initView() {
        mTab = (TabLayout) findViewById(R.id.state_tab);
        mPager = (ViewPager) findViewById(R.id.content_pager);
        mTab.setTabMode(TabLayout.MODE_FIXED);
        bindTab();
    }

    /**
     * 设置tab标签 绑定viewpager和tab
     */
    private void bindTab(){
        mList = new ArrayList<>();
        mTitle = new ArrayList<>();
        mTitle.add("全部");
        mTitle.add("运输中");
        mTitle.add("已签收");

        for (String state : mTitle){
            mTab.addTab(mTab.newTab().setText(state));
            fragment = new ExpressHistoryFragment();
            fragment.setState(state);
            mList.add(fragment);
        }
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(),mList,mTitle);
        mPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mPager);
    }

    private void setClick(){
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.delete_all).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.delete_all:
                AlertDialog.Builder builder = new AlertDialog.Builder(ExpressHistoryActivity.this);
                AlertDialog alert = builder.setTitle("注意")
                        .setMessage("确定要删除所有记录吗？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DataSupport.deleteAll(ExpressSaveBean.class);
                                if (fragment!=null){
                                    fragment.mAdapter.clear();
                                }
                            }
                        }).create();
                alert.show();

                break;
        }

    }
}
