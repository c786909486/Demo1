package com.example.ckz.demo1.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.news.MainActivity;
import com.example.ckz.demo1.activity.news.SearchActivity;
import com.example.ckz.demo1.adapter.pagerAdapter.MyPagerAdapter;
import com.example.ckz.demo1.bean.news.NewsBeanItem;
import com.example.ckz.demo1.cache.ACache;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.http.UrlApi;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by CKZ on 2017/5/5.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener{
    /**
     * UI
     */
    private ImageView mCenterOut;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mSearchIcon;
    /**
     * 缓存
     */
    private ACache mACache;

    /**
     * viewpagerAdapter
     */
    private MyPagerAdapter mPagerAdapter;

    /**
     * Frangment数组
     */
    private List<Fragment> mList;
    private List<String> mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,null);
        mACache = ACache.get(getContext());
        initView(view);
        return view;
    }

    private void initView(View view) {
        mSearchIcon = (ImageView) view.findViewById(R.id.search_icon);
        mViewPager = (ViewPager) view.findViewById(R.id.news_pager);
        mList = new ArrayList<>();
        mTitle = new ArrayList<>();
        //点击弹出左侧菜单
        mCenterOut = (ImageView) view.findViewById(R.id.center_out);
        mCenterOut.setOnClickListener(this);
        mSearchIcon.setOnClickListener(this);

        //设置tabLayout标签
        mTabLayout = (TabLayout) view.findViewById(R.id.main_news_tab_layout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        setNewsItem();
//        //设置viewPager适配器
//        mPagerAdapter = new MyPagerAdapter(getChildFragmentManager(),mList,mTitle);
//        mTabLayout.setupWithViewPager(mViewPager);
//        mViewPager.setAdapter(mPagerAdapter);
        //mPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(0);
    }

    private void setNewsItem(){
        if (mACache.getAsObject("NewsItem") == null){
            getNewsItem();
        }else {
            NewsBeanItem beanItem = (NewsBeanItem) mACache.getAsObject("NewsItem");
            for (String item : beanItem.getResult()){
                mTitle.add(item);
                setFragment(item);
            }
            //设置viewPager适配器
            mPagerAdapter = new MyPagerAdapter(getChildFragmentManager(),mList,mTitle);
            mTabLayout.setupWithViewPager(mViewPager);
            mViewPager.setAdapter(mPagerAdapter);
//
        }
    }

    /**
     * 获取新闻标签
     */
    private void getNewsItem(){
        CommonOkHttpClient.post(CommonRequest.createPostRequest(UrlApi.NewsApi.newsItem(),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                NewsBeanItem beanItem = JSON.parseObject(responseObj.toString(),NewsBeanItem.class);
                mACache.put("NewsItem",beanItem);
                for (String item : beanItem.getResult()){
                    mTitle.add(item);
                    mTabLayout.addTab(mTabLayout.newTab().setText(item));
                    setFragment(item);
                }
                //设置viewPager适配器
                mPagerAdapter = new MyPagerAdapter(getChildFragmentManager(),mList,mTitle);
                mTabLayout.setupWithViewPager(mViewPager);
                mViewPager.setAdapter(mPagerAdapter);

            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        }));
    }

    private void setFragment(String type){
        NewsFragment fragment = new NewsFragment();
        fragment.setType(type);
        mList.add(fragment);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.center_out:
                MainActivity.mDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.search_icon:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
