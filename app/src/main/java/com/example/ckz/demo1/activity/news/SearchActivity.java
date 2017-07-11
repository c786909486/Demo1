package com.example.ckz.demo1.activity.news;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.adapter.news.NewsContentAdapter;

import com.example.ckz.demo1.bean.db.SearchHistoryBean;
import com.example.ckz.demo1.bean.news.NewsBean;
import com.example.ckz.demo1.http.UrlApi;
import com.example.ckz.demo1.view.ClearEditText;
import com.example.ckz.demo1.view.NestedListView;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity implements TextWatcher,TextView.OnEditorActionListener,View.OnClickListener{
    /**
     * 搜索栏
     */
    private TextView mCancle;
    private ClearEditText mInput;

    /**
     * 搜索界面/历史纪录
     */
    private RelativeLayout mSearchLayout;
    private ScrollView mDataLayout;
    private TextView mClearHistory;
    private NestedListView mHistoryList;
    private TextView mNoHistory;

    /**
     * 搜索结果
     */
    private RelativeLayout mResultLayout;
    private RecyclerView mResultList;
    private ProgressBar mLoading;

    /**
     * data
     */
    private List<NewsBean.ResultBean.ListBean> mData;
    private NewsContentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        setHistoryLayout();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mCancle = (TextView) findViewById(R.id.cancel_btn);
        mInput = (ClearEditText) findViewById(R.id.input_box);
        mCancle.setOnClickListener(this);
        mInput.addTextChangedListener(this);
        mInput.setOnEditorActionListener(this);

        mSearchLayout = (RelativeLayout) findViewById(R.id.search_layout);
        mDataLayout = (ScrollView) findViewById(R.id.data_layout);
        mClearHistory = (TextView) findViewById(R.id.clear_history);
        mHistoryList = (NestedListView) findViewById(R.id.history_list);
        mNoHistory = (TextView) findViewById(R.id.no_history);

        mResultLayout = (RelativeLayout) findViewById(R.id.result_layout);
        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mLoading = (ProgressBar) findViewById(R.id.loading);
        mData = new ArrayList<>();
        mAdapter = new NewsContentAdapter(this,mData);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mResultList.setAdapter(mAdapter);


    }

    private void setHistoryLayout(){
        List<SearchHistoryBean> mList = DataSupport.findAll(SearchHistoryBean.class);
        if (mList.size() == 0){
            mNoHistory.setVisibility(View.VISIBLE);
            mDataLayout.setVisibility(View.GONE);
            Log.d("showWhich","no data");
        }else {
            mNoHistory.setVisibility(View.GONE);
            mDataLayout.setVisibility(View.VISIBLE);
            //NewsHistoryAdapter mAdapter = new NewsHistoryAdapter(this,mList);
            //mHistoryList.setAdapter(mAdapter);
            Log.d("showWhich","show history");
        }
    }



    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length()==0){
            Log.d("Tag", "显示搜索");
            mSearchLayout.setVisibility(View.VISIBLE);
            mResultLayout.setVisibility(View.GONE);
            mData.clear();
            mAdapter.notifyDataSetChanged();
            setHistoryLayout();
        }
    }

    private void getSearchResult(){
        mSearchLayout.setVisibility(View.GONE);
        mResultLayout.setVisibility(View.VISIBLE);
        mLoading.setVisibility(View.VISIBLE);
        mResultList.setVisibility(View.GONE);

        SearchHistoryBean bean = new SearchHistoryBean();
        bean.setHistory(mInput.getText().toString());
        bean.save();
        CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.NewsApi.newsSearch(mInput.getText().toString()),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mLoading.setVisibility(View.GONE);
                NewsBean newsBean = JSON.parseObject(responseObj.toString(),NewsBean.class);
                mData.addAll(newsBean.getResult().getList());
                mAdapter.notifyDataSetChanged();
                mResultList.setVisibility(View.VISIBLE);
                SearchHistoryBean bean = new SearchHistoryBean();
                bean.setHistory(mInput.getText().toString());
                bean.save();
            }

            @Override
            public void onFailure(Object reasonObj) {
                Toast.makeText(SearchActivity.this,"请检查网络连接...",Toast.LENGTH_SHORT).show();
            }
        }));

        Log.d("Url",UrlApi.NewsApi.newsSearch(mInput.getText().toString()));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            if (mInput.getText().toString().length()>0){
                getSearchResult();
            }
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_btn:
                finish();
                break;
        }
    }
}
