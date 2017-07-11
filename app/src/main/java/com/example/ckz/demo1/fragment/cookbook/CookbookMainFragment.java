package com.example.ckz.demo1.fragment.cookbook;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.cookbook.CookbookSearchActivity;
import com.example.ckz.demo1.adapter.cookbook.CookbookMainAdapter;
import com.example.ckz.demo1.adapter.pagerAdapter.GalleryPagerAdapter;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;
import com.example.ckz.demo1.cache.ACache;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.http.UrlApi;
import com.example.ckz.demo1.util.LeftDrawableUtils;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by CKZ on 2017/5/28.
 */

public class CookbookMainFragment extends BaseFragment implements View.OnClickListener{
    private ImageView mBack;
    private TextView mSearcch;
    /**
     *contentUI
     */

    private SwipeRefreshLayout mRefresh;
    private RelativeLayout mRoot;
    private ViewPager mPager;
    private LinearLayout llDot;
    private ListView mList;

    private List<CookbookDataBean> mCookData;
    private CookbookMainAdapter mAdapter;

    private List<CookbookDataBean.ResultBean.ListBean> mCirClerData;
    private GalleryPagerAdapter galleryPagerAdapter;

    private ACache mACache;
    private Timer timer = new Timer();
    private int curIndex = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                   int current = mPager.getCurrentItem();
                    if (current<2){
                        current+=1;
                    }else {
                        current=0;
                    }
                    mPager.setCurrentItem(current,true);
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coolbook_main,container,false);
        mACache = ACache.get(getContext());
        initView(view);
        return view;
    }

    private void initView(View view) {
        //toolBarUI
        mBack = (ImageView) view.findViewById(R.id.back_btn);
        mSearcch = (TextView) view.findViewById(R.id.search_box);
        mSearcch.setOnClickListener(this);
        mBack.setOnClickListener(this);
        LeftDrawableUtils.setDrawbleLeft(getContext(),mSearcch,R.drawable.search);
        //ContentUI
        mList = (ListView) view.findViewById(R.id.main_list);
        mCookData = new ArrayList<>();
        mAdapter = new CookbookMainAdapter(getContext(),mCookData);
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.header_view_cookbook,null);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRoot = (RelativeLayout) headerView.findViewById(R.id.root_layout);
        mPager = (ViewPager) headerView.findViewById(R.id.gallery_view);
        llDot = (LinearLayout) headerView.findViewById(R.id.ll_dot);
        mList.addHeaderView(headerView);
        mList.setAdapter(mAdapter);
        setGalleryData();
        getListData();
    }

    private void getListData() {
        CookbookDataBean chuanCai = (CookbookDataBean) mACache.getAsObject("chuanCai");
        CookbookDataBean xiangCai = (CookbookDataBean) mACache.getAsObject("xiangCai");
        CookbookDataBean yueCai = (CookbookDataBean) mACache.getAsObject("yueCai");
        CookbookDataBean zheCai = (CookbookDataBean) mACache.getAsObject("zheCai");
        if (chuanCai!=null && xiangCai!=null && yueCai!=null && zheCai!=null){
            mCookData.add(chuanCai);
            mCookData.add(xiangCai);
            mCookData.add(yueCai);
            mCookData.add(zheCai);
            mAdapter.notifyDataSetChanged();
        }else {
            getNetData(224);
            getNetData(225);
            getNetData(226);
            getNetData(228);
        }
    }

    private void getNetData(final int classId) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.CookbookApi.getItemCookbook(classId,4,4),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                CookbookDataBean dataBean = JSON.parseObject(responseObj.toString(),CookbookDataBean.class);
                mACache.put(id2Text(classId),dataBean);
                mCookData.add(dataBean);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        }));
    }
    private String id2Text(int id){
        if (id == 224){
            return "chuanCai";
        } else if (id == 225) {
            return "xiangCai";
        } else if (id == 226) {
            return "yueCai";
        }else {
            return "zheCai";
        }
    }

    /**
     * 设置画廊数据
     */
    private void setGalleryData(){
        mCirClerData = new ArrayList<>();
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state==1){
                    mRefresh.setEnabled(false);
                }else {
                    mRefresh.setEnabled(true);
                }
            }
        });
        galleryPagerAdapter = new GalleryPagerAdapter(getContext());
        mPager.setAdapter(galleryPagerAdapter);
       if (mACache.getAsObject("CookBookPic")!=null){
           CookbookDataBean bean = (CookbookDataBean) mACache.getAsObject("CookBookPic");
           galleryPagerAdapter.addAll(bean.getResult().getList());
       }else {
           CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.CookbookApi.getItemCookbook(224,0,3),null),new DisposeDataHandle(new DisposeDataListener() {
               @Override
               public void onSuccess(Object responseObj) {
                   CookbookDataBean bean = JSON.parseObject(responseObj.toString(),CookbookDataBean.class);
                   galleryPagerAdapter.addAll(bean.getResult().getList());
                   mACache.put("CookBookPic",bean);
               }
               @Override
               public void onFailure(Object reasonObj) {
               }
           }));
       }
        sendMessage();
        setOvalLayout();
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < 3; i++) {
            llDot.addView(LayoutInflater.from(getContext()).inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        llDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                llDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                llDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    /**
     * 定时发送
     */
    private void sendMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (timer == null){
                    timer = new Timer();
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(0x123);
                    }
                },5000,5000);
            }
        }).start();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                getActivity().finish();
                break;
            case R.id.search_box:
                //跳转搜索界面
                Intent intent = new Intent(getContext(), CookbookSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        timer.cancel();
    }
}
