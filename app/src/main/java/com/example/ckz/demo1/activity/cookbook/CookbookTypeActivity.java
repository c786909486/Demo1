package com.example.ckz.demo1.activity.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.adapter.cookbook.CookbookByTypeAdapter;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;
import com.example.ckz.demo1.http.UrlApi;
import com.example.ckz.demo1.util.StatuBarColorUtil;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class CookbookTypeActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private String classId;
    private String typeName;

    private TextView mTitle;
    private ImageView mBanck;
    private ListView mCookList;
    private ProgressBar mLoading;
    private TwinklingRefreshLayout mRefresh;

    private CookbookByTypeAdapter mAdapter;
    private int start= 0;

    private List<CookbookDataBean.ResultBean.ListBean> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook_type);
        StatuBarColorUtil.setStatuBarColor(this,R.color.darkWhilte);
        getData();
        initView();
        getNetCookbook(false);
        mRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getNetCookbook(true);
            }
        });
    }

    private void initView() {
        mData = new ArrayList<>();
        mBanck = (ImageView) findViewById(R.id.back_btn);
        mTitle = (TextView) findViewById(R.id.cook_type_name);
        mCookList = (ListView) findViewById(R.id.cookbook_list);
        mRefresh = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);
        mLoading = (ProgressBar) findViewById(R.id.loading);
        mBanck.setOnClickListener(this);
        mAdapter = new CookbookByTypeAdapter(this);
        mCookList.setAdapter(mAdapter);
        mRefresh.setEnableRefresh(false);
        mTitle.setText(typeName);
        mLoading.setVisibility(View.VISIBLE);
        mCookList.setOnItemClickListener(this);
    }

    private void getData() {
        Bundle bundle = getIntent().getBundleExtra("cookData");
        classId = bundle.getString("classId");
        typeName = bundle.getString("typeName");
    }

    private void getNetCookbook(final boolean isLoadMore){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.CookbookApi.getItemCookbook(Integer.valueOf(classId),start*20,20),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                CookbookDataBean dataBean = JSON.parseObject(responseObj.toString(),CookbookDataBean.class);
                mAdapter.addAll(dataBean.getResult().getList());
                mData.addAll(dataBean.getResult().getList());
                mLoading.setVisibility(View.GONE);
                start++;
                if (isLoadMore){
                    mRefresh.finishLoadmore();
                }
            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        }));
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,CookbookDetileActivity.class);
        intent.putExtra("cookData",mData.get(position));
        startActivity(intent);
    }
}
