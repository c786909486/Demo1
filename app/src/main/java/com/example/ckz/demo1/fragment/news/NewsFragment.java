package com.example.ckz.demo1.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.news.NewsDetilActivity;
import com.example.ckz.demo1.adapter.news.NewsContentAdapter;
import com.example.ckz.demo1.bean.news.NewsBean;
import com.example.ckz.demo1.bean.user.news.CommentNews;
import com.example.ckz.demo1.cache.ACache;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.http.UrlApi;
import com.example.ckz.demo1.util.DataChangeUtil;
import com.example.ckz.demo1.util.LogUtils;
import com.example.ckz.demo1.util.SPUtils;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/5/17.
 */

public class NewsFragment extends BaseFragment implements NewsContentAdapter.OnItemClickListener{
   private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * UI
     */
    private SwipeRefreshLayout mSwipe;
    private RecyclerView mRecyclerView;

    /**
     * data
     */
    private List<NewsBean.ResultBean.ListBean> mData;
    private NewsContentAdapter mAdapter;
    private ACache mACache;

    private int page = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,container,false);
        mData = new ArrayList<>();
        mACache = ACache.get(getContext());
        initView(view);
        autoLoadMore();
        return view;
    }

    private void initView(View view) {
        mSwipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        mSwipe.setColorSchemeResources(R.color.toolBarColor);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.news_list_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRecyclerView.setPullRefreshEnabled(false);
        mAdapter = new NewsContentAdapter(getContext(),mData);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
        getNews();
        LogUtils.d("NewsFragment",UrlApi.NewsApi.newsApi(type,0));
        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       getNetNews(true,0);

                   }
               },1500);
            }
        });
    }

    private void getNews(){
        NewsBean newsBean = (NewsBean) mACache.getAsObject(type);
        if (newsBean!=null){
            mData.addAll(newsBean.getResult().getList());
            mAdapter.notifyDataSetChanged();
        }else {
            mSwipe.setRefreshing(true);
            getNetNews(true,0);
        }
    }

    /**
     * 加载更多
     */
    private void autoLoadMore(){
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int endCompletelyPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                if (endCompletelyPosition ==mAdapter.getItemCount()-2){
                    getNetNews(false,page);
                    page = page+10;
                }
            }
        });
    }

    /**
     * 获取网络新闻数据
     */
    private void getNetNews(final boolean isRefresh, final int start){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.NewsApi.newsApi(type,start),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                NewsBean newsBean = JSON.parseObject(responseObj.toString(),NewsBean.class);
                mACache.put(type,newsBean);
                int lastNum = mData.size();
                mData.removeAll(newsBean.getResult().getList());
                if (isRefresh){
                    mData.addAll(0,newsBean.getResult().getList());
                    int nowNun = mData.size();
                    int upSize = nowNun - lastNum;
                    Toast.makeText(getContext(),"又找到了"+upSize+"条新闻",Toast.LENGTH_SHORT).show();
                    SPUtils.putlongSp(getContext(),"refreshTime",System.currentTimeMillis());

                }else {
                    mData.addAll(newsBean.getResult().getList());
                }
                Log.d("TAG",mData.size()+"");
                mAdapter.notifyDataSetChanged();
                mSwipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.d("TAG",reasonObj.toString());
            }
        }));
//
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), NewsDetilActivity.class);
        intent.putExtra("NewsData", DataChangeUtil.commentNews(mData.get(position)));
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (System.currentTimeMillis()-SPUtils.getlongSp(getContext(),"refreshTime")>2*60*60*1000){
           mSwipe.post(new Runnable() {
               @Override
               public void run() {
                   mSwipe.setRefreshing(true);
                   getNetNews(true,0);
               }
           });
        }
    }
}
