package com.example.ckz.demo1.jpush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.news.NewsDetilActivity;
import com.example.ckz.demo1.activity.news.WebActivity;
import com.example.ckz.demo1.adapter.news.NewsContentAdapter;
import com.example.ckz.demo1.bean.db.NewsSaveBean;
import com.example.ckz.demo1.bean.news.NewsBean;
import com.example.ckz.demo1.http.UrlApi;
import com.example.ckz.demo1.util.DataChangeUtil;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class PushMessageActivity extends AppCompatActivity implements View.OnClickListener,NewsContentAdapter.OnItemClickListener{
    private static final int NO_COLLECT = 0;
    private static final int COLLECT = 1;
    private int currentState;

    /**
     * UI
     */
    private ImageView mBackBtn;
    private RecyclerView mList;
    private RelativeLayout mLoading;

    private NewsContentAdapter mAdapter;
    private List<NewsBean.ResultBean.ListBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_message);
        initView();
    }

    private void initView() {
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mBackBtn.setOnClickListener(this);
        mList = (RecyclerView) findViewById(R.id.news_list_view);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mLoading = (RelativeLayout) findViewById(R.id.loading_layout);
        mLoading.setVisibility(View.VISIBLE);
        mData = new ArrayList<>();
        mAdapter = new NewsContentAdapter(this,mData);
        mList.setAdapter(mAdapter);
        setData();
        mAdapter.setOnItemClickListener(this);
    }

    private void setData() {
        getNetNews(0);
    }
    /**
     * 获取网络新闻数据
     */
    private void getNetNews(final int start){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.NewsApi.newsApi("头条",start),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                NewsBean newsBean = JSON.parseObject(responseObj.toString(),NewsBean.class);
               // mData.removeAll(newsBean.getResult().getList());
                mData.addAll(newsBean.getResult().getList());
                Log.d("TAG",mData.size()+"");
                mAdapter.notifyDataSetChanged();
                mLoading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d("TAG",reasonObj.toString());
            }
        }));
//
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
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(PushMessageActivity.this, NewsDetilActivity.class);
        intent.putExtra("NewsData",mData.get(position));
        startActivity(intent);
    }
}
